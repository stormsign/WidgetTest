package com.example.khb.widgettest.view.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.khb.widgettest.view.widget.ViewOverrideManager;

/**
 * Created by khb on 2016/6/7.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    private Context context = null;
    private ViewOverrideManager overrideManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayoutId());
        context = this;
        init();
        overrideManager = new ViewOverrideManager(getLoadingParentView());
    }

    public abstract void init();

    public abstract int getContentLayoutId();

    public abstract View getLoadingParentView();


    @Override
    public void showLoading(String msg) {
        overrideManager.showLoading(msg);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {
        overrideManager.restoreView();
    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {

    }
}
