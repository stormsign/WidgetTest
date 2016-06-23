package com.example.khb.widgettest.interactor;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.khb.widgettest.http.VolleyManager;
import com.example.khb.widgettest.interactor.interf.IMapInteractor;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khb on 2016/6/14.
 */
public class MapInteractor implements IMapInteractor {

//    private final Context context;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener listener;
    private OnLoadCallBack onLoadListener;

    public MapInteractor(AMapLocationListener listener, OnLoadCallBack onLoadListener){
        this.listener = listener;
        this.onLoadListener = onLoadListener;
    }


    @Override
    public void receiveLocation(Context context){
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClient = new AMapLocationClient(context);
//        定位监听
        aMapLocationClient.setLocationListener(listener);
//        设置定位模式为高精度模式，battery_saving为低功耗模式，device_sensors是仅设备模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setOnceLocation(true);
//        aMapLocationClientOption.setInterval(2000); //定位间隔2秒
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
// 启动定位
        aMapLocationClient.startLocation();
    }

    @Override
    public void receiveCoordinates(Context context, LatLng latLng) {
//        Intent startService = new Intent(context, LongConnectionService.class);
//        context.startService(startService);



        String url = "http://192.168.1.117:8080/app/positionList";
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", "89eee5c4-4ea9-42b1-906b-841d7eb42fa8");
        params.put("lon", latLng.longitude);
        params.put("lat", latLng.latitude);
        params.put("propertyId", 4);
        VolleyManager.getInstance().sendGsonRequest("POSITION",
                url,
                params,
                PositionBean.class,
                new Response.Listener<PositionBean>() {
                    @Override
                    public void onResponse(PositionBean response) {
                        if (onLoadListener != null) {
                            onLoadListener.onLoadSuccess(response.getPositions());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (onLoadListener != null) {
                            onLoadListener.onLoadFailed(error.toString());
                        }
                    }
                });
    }

    public class PositionBean {
        int code;
        String msg;
        List<Position> positions;

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

        public List<Position> getPositions() {
            return positions;
        }

        public void setPositions(List<Position> positions) {
            this.positions = positions;
        }
    }

}





















