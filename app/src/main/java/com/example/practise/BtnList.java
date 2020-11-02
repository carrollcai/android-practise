package com.example.practise;

public class BtnList {
    private final Class<?> cls;
    public String text;

    public BtnList(String text, Class<?> cls) {
        this.text = text;
        this.cls = cls;
    }

    public String getText() {
        return text;
    }

    public Class<?> getCls() {
        return cls;
    }
}
