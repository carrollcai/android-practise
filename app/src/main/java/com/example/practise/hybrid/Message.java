package com.example.practise.hybrid;

import java.util.HashMap;

public class Message {
    public HashMap<String, String> mMessageObject = new HashMap<>();

    public void add(String localFuncName, String callbackName) {
        mMessageObject.put(localFuncName, callbackName);
    }

    public String get(String localFuncName) {
        return mMessageObject.get(localFuncName);
    }

    public void remove(String localFuncName) {
        mMessageObject.remove(localFuncName);
    }
}
