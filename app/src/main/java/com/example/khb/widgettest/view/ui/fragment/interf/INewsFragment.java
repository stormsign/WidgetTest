package com.example.khb.widgettest.view.ui.fragment.interf;

import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.view.ui.base.BaseView;

/**
 * Created by khb on 2016/6/13.
 */
public interface INewsFragment extends BaseView {
    void initViews();
    void refresh(NewsInteractor.NewsBean data);
}
