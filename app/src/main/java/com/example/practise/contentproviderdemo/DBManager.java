package com.example.practise.contentproviderdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/31.
 */


public class DBManager {
    public DBHelper mDBHelper;
    public SQLiteDatabase mSQLiteDatabase;

    public DBManager(Context context) {

        mDBHelper = new DBHelper(context);

        // 必须需要初始化，获取可读写的数据库实例
        mSQLiteDatabase = mDBHelper.getWritableDatabase();

    }

    public long insert(String nullColumnHack, ContentValues values) {
        Long rowId = null;
        mSQLiteDatabase.beginTransaction();

        try {
            rowId = mSQLiteDatabase.insert("book", nullColumnHack, values);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return rowId;
    }

    public int delete(String whereClause, String[] whereArgs) {
        int num = 0;
        mSQLiteDatabase.beginTransaction();
        try {
            num = mSQLiteDatabase.delete("book", whereClause, whereArgs);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return num;
    }

    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        int num = 0;
        mSQLiteDatabase.beginTransaction();
        try {
            num = mSQLiteDatabase.update("book", values, whereClause, whereArgs);
            mSQLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSQLiteDatabase.endTransaction();
        }
        return num;
    }

    public List<BookBean> search() {
        ArrayList<BookBean> list = new ArrayList<>();

        Cursor c = queryTheCursor();

        while (c.moveToNext()) {
            BookBean bean = new BookBean();

            bean._id = c.getInt(c.getColumnIndex("_id"));
            bean.number = c.getString(c.getColumnIndex("number"));
            bean.name = c.getString(c.getColumnIndex("name"));
            bean.press = c.getString(c.getColumnIndex("press"));

//            Log.d("_id", bean.number + "");

            list.add(bean);
        }
        return list;
    }

    public Cursor queryTheCursor() {
        Cursor c = mSQLiteDatabase.rawQuery("select * from book order by _id desc", null);
        return c;
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String sortOrder) {

        Cursor c = mSQLiteDatabase.query("book", columns, selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }
}
