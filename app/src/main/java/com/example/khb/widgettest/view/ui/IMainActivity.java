package com.example.khb.widgettest.view.ui;

import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.view.ui.base.BaseView;

/**
 * Created by khb on 2016/6/3.
 */
public interface IMainActivity extends BaseView{
    void getView();
    void initLists();
    void refresh(NewsInteractor.NewsBean data);
}
