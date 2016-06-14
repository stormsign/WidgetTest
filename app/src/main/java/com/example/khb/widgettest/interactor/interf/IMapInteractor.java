package com.example.khb.widgettest.interactor.interf;

import android.content.Context;

import com.amap.api.maps2d.model.LatLng;

import java.util.List;

/**
 * Created by khb on 2016/6/14.
 */
public interface IMapInteractor {

    void receiveLocation(Context context);
    List<LatLng> receiveCoordinates(Context context);

}
