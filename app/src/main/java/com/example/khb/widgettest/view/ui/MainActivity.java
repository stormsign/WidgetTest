package com.example.khb.widgettest.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khb.widgettest.R;
import com.example.khb.widgettest.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private TextView tv;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        mainPresenter.onCreate();
        initView();
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.performOnClick(MainActivity.this);
            }
        });
    }

    @Override
    public void onLoading(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(data);
            }
        });
    }

    @Override
    public void onLoadingFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
