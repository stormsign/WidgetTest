package com.example.khb.widgettest.model;

import com.example.khb.widgettest.listener.OnLoadCallBack;

/**
 * Created by khb on 2016/5/10.
 */
public class UserEntity implements IUserEntity {
    private String id;
    private String name;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void getData(final OnLoadCallBack onLoadCallBack) {

        onLoadCallBack.onPreLoad("loading.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = null;
                try {
                    Thread.sleep(1000);
                    data = "user shadow";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (onLoadCallBack!=null){
                    onLoadCallBack.onLoadSuccess(data);
                }
            }
        }).start();


    }

}
