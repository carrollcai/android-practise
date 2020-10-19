package com.example.practise.mvp.ipinfo;

import com.example.practise.mvp.BaseView;
import com.example.practise.mvp.model.IpInfo;

public interface IpInfoContract {
    interface Presenter {
        void getIpInfo(String ip);
    }

    interface View extends BaseView<Presenter> {
        void setIpInfo(IpInfo ipInfo);
        void showLoading();
        void hideLoading();
        void showError();
        boolean isActive();
    }
}
