package com.example.khb.widgettest.model;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.POST;

/**
 * Created by khb on 2016/5/11.
 */
public interface IHuxingEntity  {
    @POST("newHuxingInfo")
    retrofit2.Call<String> getHuxings(@FieldMap Map<String, String> map);
}
