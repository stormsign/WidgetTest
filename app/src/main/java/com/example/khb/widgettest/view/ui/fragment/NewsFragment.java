package com.example.khb.widgettest.view.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.khb.widgettest.R;
import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.model.NewsEntity;
import com.example.khb.widgettest.presenter.INewsPresenter;
import com.example.khb.widgettest.presenter.impl.NewsPresenter;
import com.example.khb.widgettest.view.ui.adapter.NewsAdapter;
import com.example.khb.widgettest.view.ui.base.BaseFragment;
import com.example.khb.widgettest.view.ui.fragment.interf.INewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by khb on 2016/6/13.
 */
public class NewsFragment extends BaseFragment implements INewsFragment, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refresh;
    private RecyclerView list;
    private NewsAdapter adapter;
    private List<NewsEntity> dataList;
    private int page = 1;
    private INewsPresenter newsPresenter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void getViews(View view) {
        refresh = (SwipeRefreshLayout)view.findViewById(R.id.refresh);
        list = (RecyclerView)view.findViewById(R.id.list);
        newsPresenter = new NewsPresenter(this);
        newsPresenter.initViews();
    }

    @Override
    protected void setUpView() {

    }

    @Override
    public void initViews() {

        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);

        list.setLayoutManager(new LinearLayoutManager(mContext));
        dataList = new ArrayList<>();
        adapter = new NewsAdapter(mContext, dataList);
        this.list.setAdapter(adapter);

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        newsPresenter.getLists(page);
    }

    @Override
    public void refresh(NewsInteractor.NewsBean data) {
        if (null != data && data.getNewsInfos().size()>0){
            dataList.clear();
            dataList.addAll(data.getNewsInfos());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        newsPresenter.getLists(page);
    }

    @Override
    public void showLoading(String msg) {
//        super.showLoading(msg);
        if (!refresh.isRefreshing()) {
            refresh.post(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        refresh.setRefreshing(false);
    }
}
