package com.example.khb.widgettest.presenter.impl;

import android.support.v4.app.Fragment;

import com.example.khb.widgettest.factory.FragmentFactory;
import com.example.khb.widgettest.interactor.interf.INewsInteractor;
import com.example.khb.widgettest.presenter.IMainPresenter;
import com.example.khb.widgettest.view.ui.activity.IMainActivity;

/**
 * Created by khb on 2016/6/6.
 */
public class MainPresenter implements IMainPresenter{
    private IMainActivity iMainActivity;
    private INewsInteractor iNewsInteractor;
    public MainPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
//        iNewsInteractor = new NewsInteractor(this);
    }

    @Override
    public void initialize() {
        iMainActivity.getView();
//        iMainActivity.initLists();
    }

    @Override
    public Fragment getFragments(int page) {
//        iNewsInteractor.getNews(page);
        return FragmentFactory.getFragment(page);
    }

//    @Override
//    public void onPreLoad(String msg) {
//        iMainActivity.showLoading(msg);
//    }
//
//    @Override
//    public void onLoadSuccess(NewsInteractor.NewsBean data) {
//        iMainActivity.hideLoading();
//        iMainActivity.refresh(data);
//    }
//
//    @Override
//    public void onLoadFailed(String msg) {
//        iMainActivity.hideLoading();
//    }
}
