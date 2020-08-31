package com.example.practise.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/31.
 */


public class BookProvider extends ContentProvider {
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int BOOKS = 1;
    private static final int BOOK = 2;
    private DBManager dbManger;

    static {
        matcher.addURI(Constant.AUTHORITY, "books", BOOKS);
        matcher.addURI(Constant.AUTHORITY, "book/#", BOOK);
    }

    @Override
    public boolean onCreate() {
        dbManger = new DBManager(this.getContext());

        // 查看所有数据
        List<BookBean> lists = dbManger.search();

        for (BookBean list: lists) {
            Log.d("查看所有数据", list.number +  ", " + list.name);
        }

        ContentValues values = new ContentValues();
        values.put(Constant.Book.NUMBER, "009");
        values.put(Constant.Book.NAME, "初始化添加");
        values.put(Constant.Book.PRESS, "测试");

        insert(Constant.Book.BOOK_CONTENT_URI, values);

        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (matcher.match(uri)) {
        case BOOKS:
                return "vnd.android.cursor.dir/com.example.practise.contentproviderdemo";
            case BOOK:
                return "vnd.android.cursor.item/com.example.practise.contentproviderdemo";
            default:
                throw new IllegalArgumentException("未知Uri");
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
            @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor c = null;

        switch (matcher.match(uri)) {
            case BOOKS:
                c = dbManger.query(projection, selection, selectionArgs, sortOrder);
                break;
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = Constant.Book._ID + "=" + id;

                if (selection != null && !selection.equals("")) {
                    where = where + "and" + selection;
                }

                c = dbManger.query(projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("未知Uri");
        }
        return c;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long rowId = dbManger.insert(Constant.Book._ID, values);

        if (rowId > 0) {
            Uri wordUri = ContentUris.withAppendedId(uri, rowId);

            getContext().getContentResolver().notifyChange(wordUri, null);
            return wordUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
            @Nullable String[] selectionArgs) {

        int num = 0;

        switch (matcher.match(uri)) {
            case BOOKS:
                num = dbManger.delete(selection, selectionArgs);
                break;
            case BOOK:
                long id = ContentUris.parseId(uri);
                String where = Constant.Book._ID + "=" + id;

                if (selection != null && !selection.equals("")) {
                    where = where + "and" + selection;
                }
                num = dbManger.delete(where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
            @Nullable String[] selectionArgs) {
        int num = 0;

        switch (matcher.match(uri)) {
            case BOOKS:
                num = dbManger.update(values, selection, selectionArgs);
                break;
            case BOOK:
                // 这边后面拼接 ? 符号
                String where = Constant.Book._ID + "=" + "?";

                if (selection != null && !selection.equals("")) {
                    where = where + "and" + selection;
                }
                num = dbManger.update(values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知Uri");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return num;
    }
}
