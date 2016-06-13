package com.example.khb.widgettest.view.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by khb on 2016/6/13.
 */
public abstract class BaseFragment extends Fragment implements BaseView{

    public Context mContext;
    public View mView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getContentViewId()!=0){
            mView = inflater.inflate(getContentViewId(), null);
            getViews(mView);
            return mView;
        }else{
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
    }

    protected abstract void getViews(View view);

    protected abstract void setUpView();

    public abstract int getContentViewId();


    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void hideError() {

    }

    @Override
    public void netError() {

    }
}
