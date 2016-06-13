package com.example.khb.widgettest.view.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.presenter.IMapPresenter;
import com.example.khb.widgettest.presenter.impl.MapPresenter;
import com.example.khb.widgettest.view.ui.base.BaseFragment;
import com.example.khb.widgettest.view.ui.fragment.interf.IMapFragment;

/**
 * Created by khb on 2016/6/13.
 */
public class MapFragment extends BaseFragment implements IMapFragment, GeocodeSearch.OnGeocodeSearchListener {


    private IMapPresenter mapPresenter;
    private MapView mapView;
    private Bundle bundle;
    private AMap aMap;
    private MarkerOptions markerOptions;
    private LatLng latLng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bundle = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void getViews(View view) {
        mapView = (MapView) view.findViewById(R.id.map);
        mapPresenter = new MapPresenter(this);
    }

    @Override
    protected void setUpView() {
        mapView.onCreate(bundle);
        aMap = mapView.getMap();

//        aMap.setLocationSource(this);
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);
//        aMap.setMyLocationEnabled(true);

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.mipmap.ic_launcher));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        不让父控件拦截地图的触摸事件，避免滑动地图时整个页面也跟着滑动
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mapView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });
        showEstateLocation();

        CameraPosition cameraPosition = new CameraPosition(latLng, 16, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.moveCamera(cameraUpdate);
        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
        geocodeSearch.setOnGeocodeSearchListener(this);
        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(query);


//        mRadioGroup = (RadioGroup) findViewById(R.id.map_radioGroup);
//        mRadioGroup.setOnCheckedChangeListener(this);

//        设置地图缩放级别
//        cameraUpdate = CameraUpdateFactory.zoomTo(16);
//        aMap.moveCamera(cameraUpdate);
    }

    private void showEstateLocation() {
        latLng = new LatLng(22.5416746181, 114.0851537873);
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        aMap.addMarker(markerOptions);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
