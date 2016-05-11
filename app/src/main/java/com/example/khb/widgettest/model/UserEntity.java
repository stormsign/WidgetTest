package com.example.khb.widgettest.model;

import android.util.Log;

import com.example.khb.widgettest.listener.OnLoadCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public void getData(final OnLoadCallBack onLoadCallBack) {

        onLoadCallBack.onPreLoad("loading.....");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = null;
                try {
                    Thread.sleep(1000);
//                    requestPost();
                    retrofitPostRequest();
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
    private void requestPost() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        String md5 = md5String("3"+"866328028175394"+"1"+"hothz");
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

        Request request = new Request.Builder().url("http://app.miuhouse.com/app/"+"newHuxingInfo").post(body).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String strResponse = response.body().string();
                    Log.i("Log", "=====OKHTTP Exception=====\n"+strResponse);
                }else {
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

    private void retrofitPostRequest() throws JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IHuxingBiz huxingBiz = retrofit.create(IHuxingBiz.class);
        Map<String, String> map = new HashMap<>();
        String md5 = md5String("3"+"866328028175394"+"1"+"hothz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceType", "3")
                .put("imei", "866328028175394")
                .put("version_code", "1")
                .put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
        String json = jsonObject.toString();
        map.put("md5", md5);
        map.put("transData", json);
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




    /**
     * 对字符串进行MD5加密 输出：MD5加密后的大写16进制密文
     *
     * @param text
     * @return
     */
    public static String md5String(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 获取 摘要器
        byte[] result = digest.digest(text.getBytes()); // 通过 摘要器对指定的数据进行加密，并返回到byte[]。
        StringBuffer sb = new StringBuffer(); // 保存十六进制的密文

        // 将加密后的数据 由byte(二进制)转化成Integer(十六进制)。
        for (byte b : result) {
            int code = b & 0xff;
            String s = Integer.toHexString(code);
            if (s.length() == 1) {
                sb.append("0");
                sb.append(s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString().toUpperCase(); // 返回数据加密后的十六进制密文
    }


}












