package com.example.khb.widgettest.presenter.impl;

import com.example.khb.widgettest.presenter.IMapPresenter;
import com.example.khb.widgettest.view.ui.fragment.interf.IMapFragment;

/**
 * Created by khb on 2016/6/13.
 */
public class MapPresenter implements IMapPresenter {

    private IMapFragment mapFragment;
    public MapPresenter(IMapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }


}
