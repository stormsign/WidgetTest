package com.example.khb.widgettest.model.interf;

import android.content.Context;

import com.example.khb.widgettest.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/5/10.
 */
public interface IUserEntity {
    void sendData(Context context, OnLoadCallBack onLoadCallBack);
    void getData(Context context, OnLoadCallBack onLoadCallBack);
}
