package com.example.practise.recyclerview;

import android.os.Bundle;
import android.widget.Toast;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearActivity extends AppCompatActivity {

    private RecyclerView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        RecyclerView mView = findViewById(R.id.rv_linear);

        mView.setLayoutManager(new LinearLayoutManager(LinearActivity.this));

        mView.setAdapter(new LinearAdapter(LinearActivity.this,
                new LinearAdapter.OnItemClickListener() {

                    @Override
                    public void onClick(int pos) {
                        Toast.makeText(LinearActivity.this, "click...." + pos, Toast.LENGTH_LONG)
                                .show();
                    }
                }, new LinearAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                Toast.makeText(LinearActivity.this, "long click...." + pos, Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public boolean isStop(int pos) {
                if (pos % 2 == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }));

    }
}