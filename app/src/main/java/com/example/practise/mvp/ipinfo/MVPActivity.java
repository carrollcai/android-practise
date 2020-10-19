package com.example.practise.mvp.ipinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.practise.R;
import com.example.practise.mvp.ipinfo.IpInfoFragment;
import com.example.practise.mvp.ipinfo.IpInfoPresenter;
import com.example.practise.mvp.net.IpInfoTask;
import com.example.practise.mvp.util.ActivityUtils;

public class MVPActivity extends AppCompatActivity {
    private IpInfoPresenter ipInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_v_p);

        IpInfoFragment ipInfoFragment = (IpInfoFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (ipInfoFragment == null) {
            ipInfoFragment = IpInfoFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ipInfoFragment, R.id.contentFrame);
        }

        IpInfoTask ipInfoTask = IpInfoTask.getInstance();
        ipInfoPresenter = new IpInfoPresenter(ipInfoFragment, ipInfoTask);
        ipInfoFragment.setPresenter(ipInfoPresenter);
    }
}