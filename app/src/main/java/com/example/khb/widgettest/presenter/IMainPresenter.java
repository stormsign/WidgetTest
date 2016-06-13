package com.example.khb.widgettest.presenter;

import android.support.v4.app.Fragment;

/**
 * Created by khb on 2016/6/6.
 */
public interface IMainPresenter {
    void initialize();
    Fragment getFragments(int page);
}
