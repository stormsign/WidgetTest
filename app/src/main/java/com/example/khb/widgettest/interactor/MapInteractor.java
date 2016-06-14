package com.example.khb.widgettest.interactor;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.example.khb.widgettest.interactor.interf.IMapInteractor;

import java.util.List;

/**
 * Created by khb on 2016/6/14.
 */
public class MapInteractor implements IMapInteractor {

//    private final Context context;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationListener listener;

    public MapInteractor(AMapLocationListener listener){
        this.listener = listener;
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
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
// 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
// 在定位结束后，在合适的生命周期调用onDestroy()方法
// 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
// 启动定位
        aMapLocationClient.startLocation();
    }

    @Override
    public List<LatLng> receiveCoordinates(Context context) {
        return null;
    }

}





















