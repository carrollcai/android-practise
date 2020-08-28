package com.example.practise.sqlitedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class ShowCameraActivity extends AppCompatActivity {
    private ImageView backImage;
    private EditText titleEdit;
    private EditText contentEdit;
    private ImageView photoImage;
    private TextView tipTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //ȥ��������
        setContentView(R.layout.activity_show_camera);
        backImage = (ImageView) findViewById(R.id.image_back_camera);
        titleEdit = (EditText) findViewById(R.id.edt_title_show_camera);
        contentEdit = (EditText) findViewById(R.id.edt_content_show_camera);
        photoImage = (ImageView) findViewById(R.id.image_show_camera);
        tipTxt = (TextView) findViewById(R.id.txt_photo_tip);

        final History history = (History) getIntent().getExtras().getSerializable("history");
        titleEdit.setText(history.title);
        contentEdit.setText(history.content);
        File file = new File(history.filePath);
        if (file != null && file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
            photoImage.setImageBitmap(bm);
        } else {
            tipTxt.setVisibility(View.VISIBLE);
        }

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCameraActivity.this.finish();
            }
        });
    }
}