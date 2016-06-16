package com.example.khb.widgettest.presenter.impl;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.example.khb.widgettest.interactor.MapInteractor;
import com.example.khb.widgettest.interactor.interf.IMapInteractor;
import com.example.khb.widgettest.presenter.IMapPresenter;
import com.example.khb.widgettest.utils.L;
import com.example.khb.widgettest.view.ui.fragment.interf.IMapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/6/13.
 */
public class MapPresenter implements IMapPresenter, AMapLocationListener {

    private IMapFragment mapFragment;
    private IMapInteractor mapInteractor;
    public MapPresenter(IMapFragment mapFragment) {
        this.mapFragment = mapFragment;
        mapInteractor = new MapInteractor(this);
    }


    @Override
    public void getLocation(Context context) {
        mapInteractor.receiveLocation(context);
    }

    @Override
    public void getCoordinates(Context context) {
        List<LatLng> list =  mapInteractor.receiveCoordinates(context);
        list = new ArrayList<>();
        mapFragment.showCoordinates(list);
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
            }
        }
    }

}
