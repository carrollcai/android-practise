package com.example.practise.sqlitedemo;

import java.io.Serializable;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class History implements Serializable {
    private static final long serialVersionUID = 1L;
    public int _id;
    public String title;
    public String content;
    public String filePath;
    public String time;
    public String type;

    public History() {

    }

    public History(String title, String content, String filePath, String time, String type) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.time = time;
        this.type = type;
    }

    public History(int _id, String title, String content, String filePath, String time, String type) {
        this._id = _id;
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.time = time;
        this.type = type;
    }
}
