package com.example.khb.widgettest.presenter.impl;

import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.listener.OnLoadCallBack;
import com.example.khb.widgettest.presenter.INewsPresenter;
import com.example.khb.widgettest.view.ui.fragment.interf.INewsFragment;

/**
 * Created by khb on 2016/6/13.
 */
public class NewsPresenter implements INewsPresenter, OnLoadCallBack<NewsInteractor.NewsBean> {
    private INewsFragment iNewsFragment;
    private final NewsInteractor newsInteractor;

    public NewsPresenter(INewsFragment iNewsFragment){
        this.iNewsFragment = iNewsFragment;
        newsInteractor = new NewsInteractor(this);
    }

    @Override
    public void initViews() {
        iNewsFragment.initViews();
    }

    @Override
    public void getLists(int page) {
        newsInteractor.getNews(page);
    }

    @Override
    public void onPreLoad(String msg) {
        iNewsFragment.showLoading(msg);
    }

    @Override
    public void onLoadSuccess(NewsInteractor.NewsBean data) {
        iNewsFragment.hideLoading();
        iNewsFragment.refresh(data);
    }

    @Override
    public void onLoadFailed(String msg) {

    }
}
