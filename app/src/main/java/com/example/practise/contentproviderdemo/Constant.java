package com.example.practise.contentproviderdemo;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/31.
 */


public class Constant {
    public static final String AUTHORITY = "com.example.practise.contentproviderdemo";

    public static final class Book implements BaseColumns {

        public final static String _ID = "_id";
        public final static String NUMBER = "number";
        public final static String NAME = "name";
        public final static String PRESS = "press";

        public final static Uri BOOKS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/books");
        public final static Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");

    }
}
