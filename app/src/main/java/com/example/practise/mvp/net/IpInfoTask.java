package com.example.practise.mvp.net;

import com.example.practise.mvp.ILoadTasksCallback;
import com.example.practise.mvp.model.IpData;
import com.example.practise.mvp.model.IpInfo;

import java.util.HashMap;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;

public class IpInfoTask implements INetTask<String> {
    private static IpInfoTask INSTANCE = null;
    private static final String HOST = "http://ip.taobao.com/service/getIpInfo.php";

    private IpInfoTask() {
    }

    public static IpInfoTask getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IpInfoTask();
        }
        return INSTANCE;
    }

    @Override
    public void execute(String data, final ILoadTasksCallback loadTasksCallback) {
        RequestParams requestParams = new RequestParams();
        requestParams.addFormDataPart("ip", data);
        HttpRequest.post(HOST, requestParams, new BaseHttpRequestCallback<IpInfo>() {

            @Override
            public void onStart() {
                super.onStart();
                loadTasksCallback.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loadTasksCallback.onFinish();
            }

            @Override
            protected void onSuccess(IpInfo ipInfo) {
                super.onSuccess(ipInfo);
                loadTasksCallback.onSuccess(ipInfo);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                loadTasksCallback.onFailed();
            }
        });
    }
}
