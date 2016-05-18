package com.example.khb.widgettest.http.request;

import android.content.Context;
import android.util.Log;

import com.example.khb.widgettest.application.App;
import com.example.khb.widgettest.http.listener.ProgressListener;
import com.example.khb.widgettest.http.requestbody.ProgressRequestBody;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** 上传文件的请求
 * Created by khb on 2016/5/17.
 */
public class UploadRequest {

    private Context context;
    private final OkHttpClient client;
    private final JSONObject jsonObject;
    private final String md5;
    private RequestBody body;
    private ProgressRequestBody progressBody;

    private UploadRequest(Context context) {
        this.context = context;
        client = new OkHttpClient();
        md5 = Util.md5String("3" + Util.getIMEI(context) + "1" + "hothz");
        jsonObject = new JSONObject();
        try {
            jsonObject.put("deviceType", "3")
                    .put("imei", "866328028175394")
                    .put("version_code", "1");

//            String json = jsonObject.toString();
//            FormBody body = new FormBody.Builder()
//                    .add("md5", md5)
//                    .add("transData", json)
//                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class Holder{
        private final static UploadRequest INSTANCE = new UploadRequest(App.getContext());
    }

    public static UploadRequest getInstance(Context context){
        return Holder.INSTANCE;
    }

    private String fileName;

    public UploadRequest getParams(String base64String, String fileName, String folder) throws JSONException {
        jsonObject.put("base64String", base64String);
        jsonObject.put("fileName", fileName);
        jsonObject.put("folder", folder);
        this.fileName = fileName;
        return this;
    }

    private void getBody(){
        String json = jsonObject.toString();
//        body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("md5", md5)
//                .addFormDataPart("transData", json)
//                .build();
        File file = new File(fileName);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("name", "img")
                .addFormDataPart("filename", file.getName(), fileBody)
                .build();
        progressBody = new ProgressRequestBody(body, new ProgressListener() {
            @Override
            public void onProgress(long bytesRead, long totalBytes, boolean done) {
                Log.i("TAG", "========== " + bytesRead + " / " + totalBytes + " =========");
            }
        });
    }

    private String imgUrl = "http://img.miuhouse.com/upload/images";

    public void execute(final OnLoadCallBack onLoadCallBack) throws IOException {
        String url = "http://upload.miuhouse.com/app/uploadImg";
        getBody();
        Request request = new Request.Builder()
//                .url(url)
                .url(imgUrl)
                .post(progressBody)
                .build();


        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onLoadCallBack.onLoadFailed(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("TAG", "==== uploading stop " + System.currentTimeMillis() + " ====");
                Log.i("TAG", "--------RESPONSE 1 ----------\n" + response.body().toString());
                onLoadCallBack.onLoadSuccess(response);
            }

        });

    }

}
