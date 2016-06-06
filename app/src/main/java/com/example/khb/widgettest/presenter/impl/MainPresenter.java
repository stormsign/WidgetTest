package com.example.khb.widgettest.presenter.impl;

import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.interactor.interf.INewsInteractor;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.presenter.IMainPresenter;
import com.example.khb.widgettest.view.ui.IMainActivity;

/**
 * Created by khb on 2016/6/6.
 */
public class MainPresenter implements IMainPresenter, OnLoadCallBack<NewsInteractor.NewsBean> {
    private IMainActivity iMainActivity;
    private INewsInteractor iNewsInteractor;
    public MainPresenter(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
        iNewsInteractor = new NewsInteractor(this);
    }

    @Override
    public void initialize() {
        iMainActivity.getView();
        iMainActivity.initLists();
    }

    @Override
    public void getList(int page) {
        iNewsInteractor.getNews(page);
    }

    @Override
    public void onPreLoad(String msg) {

    }

    @Override
    public void onLoadSuccess(NewsInteractor.NewsBean data) {

    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
