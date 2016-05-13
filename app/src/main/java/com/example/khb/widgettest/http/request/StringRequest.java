package com.example.khb.widgettest.http.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.khb.widgettest.utils.Constants;
import com.example.khb.widgettest.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khb on 2016/5/13.
 */
public class StringRequest extends Request<String> {

    private Map<String, Object> params;
    private Response.Listener<String> listener;

    //get
    public StringRequest(int method, String url,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    //post
    public StringRequest(int method, String url, Map<String, Object> params,
                         Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.params = params;
        this.listener = listener;
    }

    @Override
    public Map<String,String> getParams() {
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
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }
}
