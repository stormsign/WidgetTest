package com.example.khb.widgettest.model;

import android.content.Context;

import com.example.khb.widgettest.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/5/10.
 */
public interface IUserEntity {
    void getData(Context context, OnLoadCallBack onLoadCallBack);
}
