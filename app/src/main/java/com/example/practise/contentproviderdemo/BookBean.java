package com.example.practise.contentproviderdemo;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/31.
 */


public class BookBean {

    public int _id;
    public String number;
    public String name;
    public String press;

    public BookBean() {

    }

    public BookBean(String number, String name, String press) {
        this.number = number;
        this.name = name;
        this.press = press;
    }

    public BookBean(int _id, String number, String name, String press) {
        this._id = _id;
        this.number = number;
        this.name = name;
        this.press = press;
    }
}
