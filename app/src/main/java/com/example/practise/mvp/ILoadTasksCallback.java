package com.example.practise.mvp;

public interface ILoadTasksCallback<T> {
    void onSuccess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}