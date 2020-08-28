package com.example.practise.sqlitedemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.practise.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Copyright (C) 2020. All rights reserved.
 * <p>
 * Created by Peter on 2020/8/27.
 */


public class CameraHistoryFragment extends Fragment {

    private ListViewAdapter cameraAdapter;
    private ListView cameraListView;
    private RelativeLayout takePhotoLayout;
    private List<History> historyList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_camera, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            boolean flag = true;
            String sdStatus = Environment.getExternalStorageState();

            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(getActivity(), "存储设备未插入", Toast.LENGTH_LONG).show();
                return;
            }

            if (data != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");

                FileOutputStream b = null;

                String sdkDir = FileUtil.getSDCardPath() + File.separator + "sqlitedemo";

                File dir1 = FileUtil.createDIR(sdkDir);
                File dir2 = FileUtil.createDIR(dir1.getAbsolutePath() + File.separator + "images");

                final String fileName = dir2.getAbsolutePath() + File.separator + DateUtil.getCurrentDate() + ".jpg";

                try {
                    b = new FileOutputStream(fileName);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
                    Toast.makeText(getActivity(), "照片保存成功", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    flag = false;
                    Toast.makeText(getActivity(), "照片保存失败", Toast.LENGTH_LONG).show();
                } finally {
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (flag == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.listview_dialog, null);

                    builder.setView(layout);
                    builder.setTitle("完善此刻想说的话");

                    final AlertDialog dialog = builder.show();
                    Button button = layout.findViewById(R.id.btn_over);
                    final EditText titleEdt = layout.findViewById(R.id.edt_title);
                    final EditText contentEdt = layout.findViewById(R.id.edt_content);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog != null) {
                                String title = titleEdt.getText().toString();
                                String content = contentEdt.getText().toString();
                                String time = DateUtil.getCurrentDate();
                                String type = "camera";
                                SqliteDemoActivity.dbManger.add(new History(title, content, fileName, time ,type));

                                dialog.dismiss();
                                String timeRaw[] = {time};
                                History history = queryNew(timeRaw);
                                historyList.add(0, history);
                                cameraAdapter.notifyDataSetChanged();
                            }
                        }
                    });

                }
            }
        }
    }

    private void initListView() {
        takePhotoLayout = getActivity().findViewById(R.id.layout_takephoto);

        takePhotoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });

        String[] typeRaw = {"camera"};
        historyList = handleDB(typeRaw);
        cameraAdapter = new ListViewAdapter(CameraHistoryFragment.this.getActivity(), historyList);
        cameraListView = CameraHistoryFragment.this.getActivity().findViewById(R.id.listview_camera);
        cameraListView.setAdapter(cameraAdapter);
        cameraListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("", "点击item");
                History history = historyList.get(position);
                Intent intent = new Intent(CameraHistoryFragment.this.getActivity(), ShowCameraActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("history", history);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private List<History> handleDB(String[] typeRaw) {
        List<History> historyList = new ArrayList<History>();
        Cursor cursor = null;
        cursor = SqliteDemoActivity.dbManger.queryTheCursor(typeRaw);
        CameraHistoryFragment.this.getActivity().startManagingCursor(cursor);

        while(cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String filePath = cursor.getString(3);
            String time = cursor.getString(4);
            String type = cursor.getString(5);
            History history = new History(_id, title, content, filePath, time, type);
            historyList.add(history);
        }
        return historyList;
    }

    private History queryNew(String[] timeRaw) {
        Cursor cursor = null;
        cursor = SqliteDemoActivity.dbManger.queryTheNew(timeRaw);
        CameraHistoryFragment.this.getActivity().startManagingCursor(cursor);
        while(cursor.moveToNext()) {
            int _id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String filePath = cursor.getString(3);
            String time = cursor.getString(4);
            String type = cursor.getString(5);
            History history = new History(_id, title, content, filePath, time, type);
            return history;
        }
        return null;
    }
}
