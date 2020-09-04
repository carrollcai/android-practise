package com.example.practise.HttpURLConnectionDemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.practise.R;

import androidx.appcompat.app.AppCompatActivity;

public class HttpURLConnectionActivity extends AppCompatActivity {

    private Button downloadBtn;
    private Button visitBtn;
    private ImageView mImageView;
    private TextView mTextView;
    private ProgressBar progressBar;

    private String resultString = "";

    private ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_u_r_l_connection);

        initView();
    }

    private void initView() {

        downloadBtn = findViewById(R.id.btn_download_img);
        visitBtn = findViewById(R.id.btn_visit_web);
        mImageView = findViewById(R.id.imageview_show);
        mTextView = findViewById(R.id.textview_show);

        downloadBtn.setOnClickListener(new DownLoadClick());
        visitBtn.setOnClickListener(new VisitClick());

    }

    private class VisitClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mImageView.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);

            Thread visitBaiduThread = new Thread(new VisitWebRunnable());

            visitBaiduThread.start();

            try {
              visitBaiduThread.join();
              if (!resultString.equals("")) {
                  mTextView.setText(resultString);
              }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownLoadClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mImageView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);

//            String imgUrl = "http://img.shixiu.net/file/news/fjxw/9909e2eb3cc173f107afe2b53a5a2c95.jpg";
            String imgUrl = "https://pic4.zhimg.com/80/0fdb8ca1202817f414ccf1aa09e492c8_1440w.jpg?source=1940ef5c";
            new DownImgAsyncTask().execute(imgUrl);
        }
    }



    private void showProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyle);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setVisibility(View.VISIBLE);

        viewGroup = (ViewGroup) findViewById(R.id.parent_view);
        viewGroup.addView(progressBar, params);
    }

    private void dismissProgressBar() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            viewGroup.removeView(progressBar);
            progressBar = null;
        }
    }



    private class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap b = getImageBitmap(strings[0]);
            return b;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mImageView.setImageBitmap(null);
            showProgressBar();
        }

        // 图片加载结束 finish 会执行这个方法
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
                dismissProgressBar();
            }
        }
    }

    private Bitmap getImageBitmap(String s) {
        URL imgUrl = null;
        Bitmap bitmap = null;
        try {
            imgUrl = new URL(s);
            HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    class VisitWebRunnable implements Runnable {

        @Override
        public void run() {
            String data = getURLResponse("http://www.baidu.com");
            resultString = data;
        }
    }

    private String getURLResponse(String s) {
        HttpURLConnection conn = null;
        InputStream is = null;
        String resultData = "";

        try {
            URL url = new URL(s);
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String inputLine = "";
            while((inputLine = bufferedReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return resultData;
    }
}