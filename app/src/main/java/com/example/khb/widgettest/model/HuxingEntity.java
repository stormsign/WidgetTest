package com.example.khb.widgettest.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by khb on 2016/5/11.
 */
public class HuxingEntity  {
    String id;
    ArrayList<String> images;   //户型图片
    String title;   //户型标题
    String apartment;   //户型
    double area;    //面积
    String zxqk;    //装修情况
    String dfl; //得房率
    int price;  //价格 元/平米
    String cx;  //朝向
    long createTime;    //创建时间
    String newPropertyId;   //关联小区的id
    String comment; //户型点评

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getZxqk() {
        return zxqk;
    }

    public void setZxqk(String zxqk) {
        this.zxqk = zxqk;
    }

    public String getDfl() {
        return dfl;
    }

    public void setDfl(String dfl) {
        this.dfl = dfl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getNewPropertyId() {
        return newPropertyId;
    }

    public void setNewPropertyId(String newPropertyId) {
        this.newPropertyId = newPropertyId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Huxing{" +
                "id='" + id + '\'' +
                ", images=" + images +
                ", title='" + title + '\'' +
                ", apartment='" + apartment + '\'' +
                ", area=" + area +
                ", zxqk='" + zxqk + '\'' +
                ", dfl='" + dfl + '\'' +
                ", price=" + price +
                ", cx='" + cx + '\'' +
                ", createTime=" + createTime +
                ", newPropertyId='" + newPropertyId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public static final String BASE_URL = "http://app.miuhouse.com/app/";

    private void retrofitPostRequest() throws JSONException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IHuxingEntity huxingBiz = retrofit.create(IHuxingEntity.class);
        Map<String, String> map = new HashMap<>();
        String md5 = md5String("3" + "866328028175394" + "1" + "hothz");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deviceType", "3")
                .put("imei", "866328028175394")
                .put("version_code", "1")
                .put("id", "198317f9-7ade-4e2c-a7ec-d09cb3adaae8");
        String json = jsonObject.toString();
        map.put("md5", md5);
        map.put("transData", json);
        retrofit2.Call<String> call = huxingBiz.getHuxings(map);
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {

            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {

            }
        });
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
