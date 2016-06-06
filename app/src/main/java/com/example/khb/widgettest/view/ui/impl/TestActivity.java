package com.example.khb.widgettest.view.ui.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khb.widgettest.R;
import com.example.khb.widgettest.presenter.impl.TestPresenter;
import com.example.khb.widgettest.view.ui.ITestActivity;

public class TestActivity extends AppCompatActivity implements ITestActivity {

    private TextView tv;
    private TestPresenter mainPresenter;
//    private DrawerLayout drawer;
//    private Toolbar title;
//    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mainPresenter = new TestPresenter(this);
        mainPresenter.onCreate();
        initTitle();
        initView();
    }

    private void initTitle() {
//        drawer = (DrawerLayout) findViewById(R.id.drawer_main);
//        mNavigationView = (NavigationView) findViewById(R.id.navigation);
//        title = (Toolbar) findViewById(R.id.title);
//        setSupportActionBar(title);
//        title.setNavigationIcon(R.mipmap.ic_person_outline_white_48dp);
//        StatusCompat.compat(this, getResources().getColor(android.R.color.holo_orange_dark));
    }

    private void initView() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.performOnClick(TestActivity.this);
            }
        });
    }

    @Override
    public void onLoading(String msg) {
        Toast.makeText(TestActivity.this, msg, Toast.LENGTH_SHORT).show();
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
