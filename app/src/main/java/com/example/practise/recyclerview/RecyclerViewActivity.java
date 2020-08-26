package com.example.practise.recyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class RecyclerViewActivity extends AppCompatActivity {

    private Button mBtnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Button mBtnList = findViewById(R.id.btn_rv_list);

        mBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, LinearActivity.class);
                startActivity(intent);
            }
        });
    }
}