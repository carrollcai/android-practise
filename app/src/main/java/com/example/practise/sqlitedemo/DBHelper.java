package com.example.practise.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public  class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sqlitedemo.db";
    private static final int DATABASE_VER = 1;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists history" + "(_id integer primary key autoincrement, title varchar, content varchar, filePath varchar, time varchar, type varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table history add column other string");
    }
}
