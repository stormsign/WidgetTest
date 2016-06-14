package com.example.khb.widgettest.view.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.presenter.IMapPresenter;
import com.example.khb.widgettest.presenter.impl.MapPresenter;
import com.example.khb.widgettest.utils.L;
import com.example.khb.widgettest.view.ui.base.BaseFragment;
import com.example.khb.widgettest.view.ui.fragment.interf.IMapFragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by khb on 2016/6/13.
 */
public class MapFragment extends BaseFragment implements IMapFragment, GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener {


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
        Button locate = (Button) view.findViewById(R.id.locate);
        locate.setOnClickListener(this);
        mapPresenter = new MapPresenter(this);


    }

    @Override
    protected void setUpView() {
        mapView.onCreate(bundle);
        aMap = mapView.getMap();

//        aMap.setLocationSource(this);
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);
//        aMap.setMyLocationEnabled(true);

        L.i("==== mapPresenter.getLocation ====");
        mapPresenter.getLocation(mContext);

        // 自定义系统定位小蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.mipmap.fktx_but_red));// 设置小蓝点的图标
//        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
//        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
//        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
//        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
//        不让父控件拦截地图的触摸事件，避免滑动地图时整个页面也跟着滑动
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                mapView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        });
//        CameraPosition cameraPosition = new CameraPosition(null, 20, 0, 30);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        L.i("==== setUpView done ====");

        mapPresenter.getCoordinates(mContext);

//        CameraPosition cameraPosition = new CameraPosition(latLng, 16, 0, 30);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
//        aMap.moveCamera(cameraUpdate);
//        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
//        geocodeSearch.setOnGeocodeSearchListener(this);
//        LatLonPoint latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
//        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
//        geocodeSearch.getFromLocationAsyn(query);


//        mRadioGroup = (RadioGroup) findViewById(R.id.map_radioGroup);
//        mRadioGroup.setOnCheckedChangeListener(this);

//        设置地图缩放级别
//        cameraUpdate = CameraUpdateFactory.zoomTo(16);
//        aMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void findLocation(LatLng latLng) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        aMap.moveCamera(cameraUpdate);
    }

    @Override
    public void showLocation(LatLng latLng) {
        L.i("===== showLocation =====");
//        CameraPosition cameraPosition = new CameraPosition(latLng, 20, 0, 30);
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        markerOptions = new MarkerOptions();
        markerOptions.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.fktx_but_red));
        aMap.clear();
        aMap.addMarker(markerOptions);
        aMap.moveCamera(cameraUpdate);
    }

    @Override
    public void showCoordinates(final List<LatLng> latLngList) {
        latLng = new LatLng(22.5416746181, 114.0851537873);
//        markerOptions = new MarkerOptions();
//        markerOptions.position(latLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
//        aMap.addMarker(markerOptions);

        TimerTask task = new TimerTask() {
            double lat = 22.5416746181;
             double lon = 114.0851537873;
             double offLat = 0.00002;
             double offLon = 0.00001;
            @Override
            public void run() {
                latLngList.clear();
//                LatLng l1 = new LatLng(lat, lon);
                lat+=offLat;
                lon+=offLon;
                LatLng l2 = new LatLng(lat, lon);
                LatLng l3 = new LatLng(lat, lon);
//                latLngList.add(l1);
                latLngList.add(l2);
                latLngList.add(l3);
                Message msg = new Message();
                msg.obj = latLngList;
                handler.sendMessage(msg);

            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);

//        if (null==latLngList){ return ;}
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            L.i("====== handle =======");
            List<LatLng> list = (List<LatLng>) msg.obj;
            aMap.clear();
            for (int i=0; i<list.size(); i++){
                markerOptions = new MarkerOptions();
                markerOptions.position(list.get(i)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                aMap.addMarker(markerOptions);
            }
        }
    };

    @Override
    public void onClick(View v) {
//        final double lat = 22.5416746181;
//        final double lon = 114.0851537873;
//        final double offLat = 0.0002;
//        final double offLon = 0.0001;



    }

//    public void locate(View view){
//        mapPresenter.getLocation(mContext);
//    }

}
