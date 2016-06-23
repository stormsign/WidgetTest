package com.example.khb.widgettest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.khb.widgettest.utils.L;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by khb on 2016/6/15.
 */
public class LongConnectionService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        L.i("==== SERVICE ON CREATE");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i(" ==== SERVICE ON START");
        Thread thread = getThread();
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    private Thread getThread(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                L.i("==== getThread");
                getCoordinates();
            }
        });

        return thread;
    }

    private void getCoordinates() {
        Socket socket = null;
        try {
            socket = new Socket("120.24.223.94", 8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        while (socket.isConnected()){
//            L.i("==== socket is connected");
//            try {
//                InputStream is = socket.getInputStream();
//                byte[] bytes = new byte[4*1024];
//                is.read(bytes);
//                String str = new String(bytes);
//                L.i("===== data from socket : "+str);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }


}
