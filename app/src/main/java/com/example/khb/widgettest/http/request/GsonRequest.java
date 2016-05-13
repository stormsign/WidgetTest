package com.example.khb.widgettest.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.khb.widgettest.utils.Constants;
import com.example.khb.widgettest.utils.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/5/13.
 */
public class GsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;
    private TypeToken<T> typeToken;
    private Map<String, Object> params;
    private Class<T> clazz;
    private Gson mGson;

    //get
    public GsonRequest(int method, String url, Class<T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        this.clazz = clazz;
        this.mListener = listener;
    }

    //post
    public GsonRequest(int method, String url, Map<String, Object> params, Class<T> clazz,
                       Response.Listener<T> listener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mGson = new Gson();
        this.params = params;
        this.clazz = clazz;
        this.mListener = listener;
    }

    //post
    public GsonRequest(int method, String url, Map<String, Object> params, TypeToken<T> typeToken,
                       Response.Listener<T> listener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mGson = new Gson();
        this.params = params;
        this.typeToken = typeToken;
        this.mListener = listener;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.DEVICETYPE, Constants.DEVICETYPE_VALUE)
                    .put(Constants.VERSIONCODE, Constants.VERSIONCODE_VALUE)
                    .put(Constants.IMEI, Constants.IMEI_VALUE);
            if (params != null){
                for (String key : params.keySet()){
                    jsonObject.put(key, params.get(key));
                }
            }
            String json = jsonObject.toString();
            String md5 = Util.md5String(Constants.DEVICETYPE_VALUE
                    + Constants.IMEI_VALUE
                    + Constants.VERSIONCODE_VALUE
                    + Constants.APPKEY);
            map.put("md5", md5);
            map.put("transData", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String charset = HttpHeaderParser.parseCharset(response.headers);
            Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
            String jsonString = new String(response.data, charset);
            if (typeToken == null) {
                T fromJson = mGson.fromJson(jsonString, clazz);
                return Response.success(fromJson, entry);
            }else{
                Object fromJson = mGson.fromJson(jsonString, typeToken.getType());
                return (Response<T>) Response.success(mGson.fromJson(jsonString, typeToken.getType())
                ,entry);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
