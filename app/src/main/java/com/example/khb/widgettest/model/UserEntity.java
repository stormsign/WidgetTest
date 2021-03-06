package com.example.khb.widgettest.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khb.widgettest.http.OkHttpStack;
import com.example.khb.widgettest.http.VolleyManager;
import com.example.khb.widgettest.http.request.UploadRequest;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.model.interf.IUserEntity;
import com.example.khb.widgettest.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
    public void sendData(final Context context, final OnLoadCallBack onLoadCallBack) {
        new Thread(new Runnable() {
            String data = null;
            @Override
            public void run() {
                String base64String = null;
                try {
                    base64String = com.example.khb.widgettest.utils.Base64.encode(
                            Util.Bitmap2Bytes(Util.createImageThumbnail(
                                    context,
                                    "/storage/emulated/0/DCIM/IMG_345197777.jpg",
                                    800)));

                    String fileName = "/storage/emulated/0/DCIM/IMG_345197777.jpg";
                    String folder = "bbs";
                    UploadRequest.getInstance(context).getParams(base64String, fileName, folder).execute(onLoadCallBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void getData(final Context context, final OnLoadCallBack onLoadCallBack) {

        onLoadCallBack.onPreLoad("loading.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = null;
                try {
                    Thread.sleep(1000);
//                    okHttpPostRequest();
//                    retrofitPostRequest();
//                    volleyPostRequest(context);
                    Map<String, Object> params = new HashMap<>();
                    params.put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
                    VolleyManager.getInstance().sendGsonRequest("tag", BASE_URL + "newHuxingInfo", params, HuxingBean.class,
                            new com.android.volley.Response.Listener<HuxingBean>() {
                                @Override
                                public void onResponse(HuxingBean response) {
                                    Log.i("Log", "======Volley WRAPPED RESPONSE======\n" + response);
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });
//                    VolleyManager.getInstance().cancel("tag");
                    data = "user shadow";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (onLoadCallBack!=null){
                    onLoadCallBack.onLoadSuccess(data);
                }
            }
        }).start();


    }

    private void requestGet() throws IOException {

        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("http://app.miuhouse.com/app/"+"demand").build();
        Request request = new Request.Builder().url("http://app.miuhouse.com/app/"+"cityList").build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            String strResponse = response.body().string();
            Log.e("Log", "=====OKHTTP Exception=====\n"+strResponse);
        }else {
            throw new IOException("===Unexpected code===" + response);
        }

    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String BASE_URL = "http://app.miuhouse.com/app/";

//    okhttp3网络请求
    private void okHttpPostRequest() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        String md5 = Util.md5String("3" + "866328028175394" + "1" + "hothz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceType", "3")
                .put("imei", "866328028175394")
                .put("version_code", "1")
                .put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
        String json = jsonObject.toString();
        FormBody body = new FormBody.Builder()
                .add("md5", md5)
                .add("transData", json)
                .build();

        Request request = new Request.Builder()
                .url("http://app.miuhouse.com/app/"+"newHuxingInfo")
                .post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String strResponse = response.body().string();
                    Log.i("Log", "=====OKHTTP Exception=====\n" + strResponse);
                } else {
                    throw new IOException("===Unexpected code===" + response);
                }
            }
        });
    }

    public interface IHuxingBiz{
        @POST("newHuxingInfo")
        @FormUrlEncoded
//        强制设置封装实体类型？？还是因为使用了GsonConverter？
        retrofit2.Call<HuxingBean> getHuxings(@Field("md5") String md5, @Field("transData") String json);
    }

//    retrofit网络请求
    private void retrofitPostRequest() throws JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IHuxingBiz huxingBiz = retrofit.create(IHuxingBiz.class);
        String md5 = Util.md5String("3" + "866328028175394" + "1" + "hothz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceType", "3")
                .put("imei", "866328028175394")
                .put("version_code", "1")
                .put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
        String json = jsonObject.toString();
        retrofit2.Call<HuxingBean> call = huxingBiz.getHuxings(md5, json);
        call.enqueue(new retrofit2.Callback<HuxingBean>() {
            @Override
            public void onResponse(retrofit2.Call<HuxingBean> call, retrofit2.Response<HuxingBean> response) {
                String strResponse = response.body().toString();
                Log.i("Log", "=====Retrofit Response=====\n" + strResponse);
            }

            @Override
            public void onFailure(retrofit2.Call<HuxingBean> call, Throwable t) {
                Log.e("Log", "=====Retrofit ERROR=====\n");
                t.printStackTrace();
            }
        });
    }

    private void volleyPostRequest(Context context){
        String url = BASE_URL + "newHuxingInfo";

        StringRequest request = new StringRequest(com.android.volley.Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Log", "======Volley RESPONSE======\n" + response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String md5 = Util.md5String("3" + "866328028175394" + "1" + "hothz");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("deviceType", "3")
                            .put("imei", "866328028175394")
                            .put("version_code", "1")
                            .put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = jsonObject.toString();
                map.put("md5", md5);
                map.put("transData", json);
                return map;
            }
        };
        //使用了以OkHttp作为实现的HttpStack
        RequestQueue requestQueue = Volley.newRequestQueue(context, new OkHttpStack(new OkHttpClient()));
        requestQueue.add(request);
    }
















    private class HuxingBean {
        int code;
        String msg;
        HuxingEntity newHuxing;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public HuxingEntity getNewHuxing() {
            return newHuxing;
        }

        public void setNewHuxing(HuxingEntity newHuxing) {
            this.newHuxing = newHuxing;
        }
    }









}












