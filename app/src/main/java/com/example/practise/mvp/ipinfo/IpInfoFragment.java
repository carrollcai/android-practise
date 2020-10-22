package com.example.practise.mvp.ipinfo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practise.R;
import com.example.practise.mvp.model.IpData;
import com.example.practise.mvp.model.IpInfo;

public class IpInfoFragment extends Fragment implements IpInfoContract.View {
    private TextView tv_country;
    private TextView tv_area;
    private TextView tv_city;
    private Button bt_ipInfo;
    private Dialog mDialog;
    private IpInfoContract.Presenter mPresenter;

    public static IpInfoFragment newInstance() {
        return new IpInfoFragment();
    }

    @Override
    public void setIpInfo(IpInfo ipInfo) {
        if (ipInfo != null && ipInfo.getData() != null) {
            IpData ipData = new IpData("河南", "郑州", "中国");
            tv_country.setText(ipData.getCountry());
            tv_area.setText(ipData.getArea());
            tv_city.setText(ipData.getCity());
        }
    }

    @Override
    public void showLoading() {
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity().getApplicationContext(), "网络错误", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(IpInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ip_info, container, false);
        tv_country = root.findViewById(R.id.tv_country);
        tv_area = root.findViewById(R.id.tv_area);
        tv_city = root.findViewById(R.id.tv_city);
        bt_ipInfo = root.findViewById(R.id.bt_ipinfo);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setTitle("获取数据中");

        bt_ipInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getIpInfo("39.155.184.147");
            }
        });
    }
}
