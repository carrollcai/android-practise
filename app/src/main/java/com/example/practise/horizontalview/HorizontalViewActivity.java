package com.example.practise.horizontalview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.practise.R;

public class HorizontalViewActivity extends AppCompatActivity {
    private ListView lv_one;
    private ListView lv_two;
    private ListView lv_three;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_view);

        lv_one = findViewById(R.id.lv_one);
        lv_two = findViewById(R.id.lv_two);
        lv_three = findViewById(R.id.lv_three);

        String[] strs1 = {"1", "2", "3", "4", "5", "1", "2", "3", "4", "5", "1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs1);
        lv_one.setAdapter(adapter1);

        String[] strs2 = {"A", "B", "C", "D", "E", "A", "B", "C", "D", "E", "A", "B", "C", "D", "E"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs2);
        lv_two.setAdapter(adapter2);

        String[] strs3 = {"11", "12", "13", "14", "15", "11", "12", "13", "14", "15", "11", "12", "13", "14", "15"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, strs3);
        lv_three.setAdapter(adapter3);
    }
}