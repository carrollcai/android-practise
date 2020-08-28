package com.example.practise.sqlitedemo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class DateUtil {
    public static String getCurrentDate() {
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(nowDate);
    }

    // 这里需要时间转换，暂时不处理
    public static String getReadableDate(String date) {
//        Date curDate = new Date(date);
        Date nowDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(nowDate);
    }
}
