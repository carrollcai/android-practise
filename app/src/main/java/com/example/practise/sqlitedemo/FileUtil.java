package com.example.practise.sqlitedemo;

import android.os.Environment;

import java.io.File;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class FileUtil {
    public static File createDIR(String filePath) {
        File dir = new File(filePath);

        if (!dir.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            dir.mkdir();
        }
        return dir;
    }

    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }
}
