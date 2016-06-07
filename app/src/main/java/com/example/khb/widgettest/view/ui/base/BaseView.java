package com.example.khb.widgettest.view.ui.base;

/**
 * Created by khb on 2016/6/7.
 */
public interface BaseView {

    void showLoading(String msg);
    void showError(String msg);
    void hideLoading();
    void hideError();
    void netError();

}
