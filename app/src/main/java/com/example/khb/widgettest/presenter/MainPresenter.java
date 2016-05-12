package com.example.khb.widgettest.presenter;

import android.content.Context;

import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.model.IUserEntity;
import com.example.khb.widgettest.model.UserEntity;
import com.example.khb.widgettest.view.ui.IMainActivity;

public  class MainPresenter implements IMainPresenter{

    private IMainActivity iMainActivity;
    private IUserEntity iUser;

    public MainPresenter (IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
        iUser = new UserEntity();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void performOnClick(Context context) {
        iUser.getData(context, new OnLoadCallBack() {

            @Override
            public void onPreLoad(String msg) {
                iMainActivity.onLoading(msg);
            }

            @Override
            public void onLoadSuccess(Object data) {
                String strData = (String) data;
                strData = processData(strData);
                iMainActivity.setData(strData);
            }

            @Override
            public void onLoadFailed(String msg) {
                iMainActivity.onLoadingFailed(msg);
            }

        });
    }

    private String processData(String strData) {
        return "New Data :" + strData;
    }
}
