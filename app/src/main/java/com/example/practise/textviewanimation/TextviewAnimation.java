package com.example.practise.textviewanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;

import com.example.practise.R;

public class TextviewAnimation extends AppCompatActivity {
    private EditTextPlus mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textview_animation);

        mTextView = findViewById(R.id.tv);

        findViewById(R.id.add_number).setOnClickListener(v -> {
            String random = String.valueOf(((int)(Math.random() * 100) % 10));
            mTextView.appendContent(random);
        });

        findViewById(R.id.add_symbol).setOnClickListener(v -> {
            String random = String.valueOf(((int)(Math.random() * 100) % 10));
            String str = "-";
            if (Integer.parseInt(random) > 5) {
                str = "-";
            } else {
                str = "+";
            }
            mTextView.appendContent(str);
        });

        findViewById(R.id.delete).setOnClickListener(v -> {
            mTextView.deleteContent();
        });

        findViewById(R.id.cancel_focus).setOnClickListener(v -> {
            mTextView.blur();
        });

        findViewById(R.id.clear_one).setOnClickListener(v -> {
            mTextView.deleteInEditMode();
        });
    }
}