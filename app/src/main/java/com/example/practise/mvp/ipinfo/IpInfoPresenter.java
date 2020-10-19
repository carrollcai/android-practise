package com.example.practise.mvp.ipinfo;

import com.example.practise.mvp.ILoadTasksCallback;
import com.example.practise.mvp.ipinfo.IpInfoContract;
import com.example.practise.mvp.model.IpInfo;
import com.example.practise.mvp.net.INetTask;

public class IpInfoPresenter implements IpInfoContract.Presenter, ILoadTasksCallback<IpInfo> {
    private INetTask netTask;
    private IpInfoContract.View addTaskView;
    public IpInfoPresenter(IpInfoContract.View addTaskView, INetTask netTask) {
        this.netTask = netTask;
        this.addTaskView = addTaskView;
    }

    @Override
    public void onSuccess(IpInfo ipInfo) {
        if (addTaskView.isActive()) {
            addTaskView.setIpInfo(ipInfo);
        }
    }

    @Override
    public void onStart() {
        if (addTaskView.isActive()) {
            addTaskView.showLoading();
        }
    }

    @Override
    public void onFailed() {
        if (addTaskView.isActive()) {
            addTaskView.hideLoading();
            addTaskView.showError();
        }
    }

    @Override
    public void onFinish() {
        if (addTaskView.isActive()) {
            addTaskView.hideLoading();
        }
    }

    @Override
    public void getIpInfo(String ip) {
        netTask.execute(ip, this);
    }
}
