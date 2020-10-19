package com.example.practise.mvp.net;

import com.example.practise.mvp.ILoadTasksCallback;

public interface INetTask<T> {
    void execute(T data, ILoadTasksCallback callback);
}
