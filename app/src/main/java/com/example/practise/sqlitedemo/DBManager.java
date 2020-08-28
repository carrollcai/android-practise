package com.example.practise.sqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);

        db = helper.getWritableDatabase();
    }

    public void add(History history) {
        db.beginTransaction();

        try {
            db.execSQL("insert into history values(null, ?, ?, ?, ?, ?)", new Object[] {history.title, history.content, history.filePath, history.time, history.type});
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }

    }

    public void add(List<History> histories) {
        db.beginTransaction();

        try {
            for (History history: histories) {
                db.execSQL("insert into history values(null, ?, ?, ?, ?, ?)", new Object[] {history.title, history.content, history.filePath, history.time, history.type});
                db.setTransactionSuccessful();
            }
        }  catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public void delete(int theId) {


        db.beginTransaction();

        try {
            db.execSQL("delete from history where _id" + theId);
            db.setTransactionSuccessful();
        } catch (Exception e) {

        } finally {
            db.endTransaction();
        }
    }

    public List<History> query(String[] type) {
        ArrayList<History> histories = new ArrayList<History>();
        Cursor cursor = queryTheCursor(type);
        while (cursor.moveToNext()) {
            History history = new History();
            history._id = cursor.getInt(cursor.getColumnIndex("_id"));
            history.title = cursor.getString(cursor.getColumnIndex("title"));
            history.content = cursor.getString(cursor.getColumnIndex("content"));
            history.filePath = cursor.getString(cursor.getColumnIndex("filePath"));
            history.time = cursor.getString(cursor.getColumnIndex("time"));
            history.type = cursor.getString(cursor.getColumnIndex("type"));
            histories.add(history);
        }
        cursor.close();
        return histories;
    }

    public Cursor queryTheCursor(String[] type) {
        Cursor c = db.rawQuery("select * from history where type = ? order by _id desc", type);
        return c;
    }

    public void closeDB() {
        db.close();
    }

    public Cursor queryTheNew(String[] time) {
        Cursor c = db.rawQuery("select * from history where time = ?", time);
        return c;
    }
}
