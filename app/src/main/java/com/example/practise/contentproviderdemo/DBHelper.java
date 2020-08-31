package com.example.practise.contentproviderdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/31.
 */


public class DBHelper extends SQLiteOpenHelper {

    private final static int VER = 1;
    private final static String NAME = "book.db";

    public DBHelper(@Nullable Context context) {
        super(context, NAME, null, VER);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists book" + "(_id integer primary key autoincrement, number varchar, name varchar, press varchar)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table book add column other string");
    }
}
