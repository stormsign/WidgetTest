package com.example.khb.widgettest.application;

import android.app.Application;
import android.content.Context;

import com.example.khb.widgettest.utils.Constants;
import com.example.khb.widgettest.utils.Util;

/**
 * Created by khb on 2016/5/13.
 */
public class App extends Application {

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.applicationContext = getApplicationContext();
        Constants.IMEI_VALUE = Util.getIMEI(this);

    }

    public static Context getContext(){
        return applicationContext;
    }
}
