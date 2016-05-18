package com.example.khb.widgettest.http.listener;

/**
 * Created by khb on 2016/5/18.
 */
public interface ProgressListener {
    void onProgress(long bytesRead, long totalBytes, boolean done);
}
