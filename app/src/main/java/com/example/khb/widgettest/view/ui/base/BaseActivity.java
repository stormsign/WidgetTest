package com.example.khb.widgettest.view.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.khb.widgettest.view.widget.ViewOverrideManager;

/**
 * Created by khb on 2016/6/7.
 */
public class BaseActivity extends AppCompatActivity implements BaseView{

    private Context context = null;
    private ViewOverrideManager overrideManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        overrideManager = new ViewOverrideManager(this);
    }

    @Override
    public void showLoading(String msg) {
        overrideManager.showLoading(msg);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {

    }
}
