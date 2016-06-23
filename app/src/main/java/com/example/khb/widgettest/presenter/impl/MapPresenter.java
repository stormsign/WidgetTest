package com.example.khb.widgettest.presenter.impl;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.example.khb.widgettest.interactor.MapInteractor;
import com.example.khb.widgettest.interactor.interf.IMapInteractor;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.model.Position;
import com.example.khb.widgettest.presenter.IMapPresenter;
import com.example.khb.widgettest.utils.L;
import com.example.khb.widgettest.view.ui.fragment.interf.IMapFragment;

import java.util.List;

/**
 * Created by khb on 2016/6/13.
 */
public class MapPresenter implements IMapPresenter, AMapLocationListener, OnLoadCallBack {

    private IMapFragment mapFragment;
    private IMapInteractor mapInteractor;
    public MapPresenter(IMapFragment mapFragment) {
        this.mapFragment = mapFragment;
        mapInteractor = new MapInteractor(this, this);
    }


    @Override
    public void getLocation(Context context) {
        mapInteractor.receiveLocation(context);
    }

    @Override
    public void getCoordinates(Context context) {
//        mapInteractor.receiveCoordinates(context);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation){
            if (aMapLocation.getErrorCode() == 0){
                aMapLocation.getLocationType();     //获取定位结果来源
                aMapLocation.getLatitude(); //纬度
                aMapLocation.getLongitude();//经度
                L.i("==== "+aMapLocation.getLatitude()+" : "+aMapLocation.getLongitude()+" ====");
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
//                mapFragment.showLocation(latLng);
                mapFragment.findLocation(latLng);
//                if (mapFragment.isFixZoom() != false){
//                    mapFragment.setIsFixZoom(false);
//                }
                mapInteractor.receiveCoordinates(null, latLng);
            }
        }
    }

    @Override
    public void onPreLoad(String msg) {

    }


    @Override
    public void onLoadSuccess(Object data) {
        L.i("ok");
        List<Position> list = ((List<Position>)data);
        mapFragment.showCoordinates(list);
    }

    @Override
    public void onLoadFailed(String msg) {
        L.i("failed:\n"+msg);
    }
}
