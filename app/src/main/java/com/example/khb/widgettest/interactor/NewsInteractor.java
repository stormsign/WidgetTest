package com.example.khb.widgettest.interactor;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.khb.widgettest.http.VolleyManager;
import com.example.khb.widgettest.interactor.interf.INewsInteractor;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.model.NewsEntity;
import com.example.khb.widgettest.utils.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/6/6.
 */
public class NewsInteractor implements INewsInteractor {

    private OnLoadCallBack mOnLoadCallBack;

    public NewsInteractor(OnLoadCallBack mOnLoadCallBack){
        this.mOnLoadCallBack = mOnLoadCallBack;
    }
    String url = "http://cloud.miuhouse.com/app/crawNews";
    @Override
    public void getNews(int page) {
        if (null != mOnLoadCallBack){
            mOnLoadCallBack.onPreLoad(null);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("propertyId",4);
        params.put("page", 1);
        params.put("pageSize", 10);
        VolleyManager.getInstance().sendGsonRequest("NEWS",
//                BASE_URL + "news",
                url,
                params,
                NewsBean.class,
                new Response.Listener<NewsBean>() {
                    @Override
                    public void onResponse(NewsBean response) {
                        L.i(response.toString());
                        mOnLoadCallBack.onLoadSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        L.i("===errror===\n"+error.toString());
                        mOnLoadCallBack.onLoadFailed(error.toString());
                    }
                });
    }

    public static final String BASE_URL = "http://app.miuhouse.com/app/";



    public class NewsBean{
        int code;
        String msg;
        List<NewsEntity> newsInfos;

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

        public List<NewsEntity> getNewsInfos() {
            return newsInfos;
        }

        public void setNewsInfos(List<NewsEntity> newsInfos) {
            this.newsInfos = newsInfos;
        }

        @Override
        public String toString() {
            return "NewsBean{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", newsInfos=" + newsInfos +
                    '}';
        }
    }
}
