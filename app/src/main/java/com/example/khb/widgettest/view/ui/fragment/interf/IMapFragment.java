package com.example.khb.widgettest.view.ui.fragment.interf;

import com.amap.api.maps2d.model.LatLng;
import com.example.khb.widgettest.view.ui.base.BaseView;

import java.util.List;

/**
 * Created by khb on 2016/6/13.
 */
public interface IMapFragment extends BaseView {
    void findLocation(LatLng latLng);
    void showLocation(LatLng latlng);
    void showCoordinates(List<LatLng> latLngList);
    void setIsFixZoom(boolean isFixZoom);
    boolean isFixZoom();
    void drawMovement(LatLng currentLocation);
}
