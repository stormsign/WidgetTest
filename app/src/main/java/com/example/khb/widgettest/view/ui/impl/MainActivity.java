package com.example.khb.widgettest.view.ui.impl;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.interactor.NewsInteractor;
import com.example.khb.widgettest.model.NewsEntity;
import com.example.khb.widgettest.presenter.IMainPresenter;
import com.example.khb.widgettest.presenter.impl.MainPresenter;
import com.example.khb.widgettest.view.ui.IMainActivity;
import com.example.khb.widgettest.view.ui.adapter.NewsAdapter;
import com.example.khb.widgettest.view.ui.base.BaseActivity;
import com.example.khb.widgettest.view.widget.StatusCompat;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IMainActivity, SwipeRefreshLayout.OnRefreshListener {

    private DrawerLayout drawer;
    private NavigationView mNavigationView;
    private Toolbar title;
    private ImageView mIvUserHead;

    private TextView mTvUserName;
    private RecyclerView list;

    private IMainPresenter iMainPresenter;
    private SwipeRefreshLayout refresh;
    private NewsAdapter adapter;
    private List<NewsEntity> dataList;

    private int page;

    //    urlPath = "http://cloud.miuhouse.com/app/" + "crawNews";
//    map.put("cityId", 59);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iMainPresenter = new MainPresenter(this);
        iMainPresenter.initialize();
        onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTitle(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        title = (Toolbar) findViewById(R.id.title);
        setSupportActionBar(title);
        title.setNavigationIcon(R.mipmap.ic_person_outline_white_48dp);
        StatusCompat.compat(this, getResources().getColor(android.R.color.holo_orange_dark));

    }

    private void initNavigation() {
        mIvUserHead = (ImageView) findViewById(R.id.iv_user_head);
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
        Glide.with(this).load(R.mipmap.ic_person_outline_white_48dp).placeholder(R.mipmap.ic_person_outline_white_48dp).into(mIvUserHead);
        mTvUserName.setText("--");
    }

    @Override
    public void getView() {
        initTitle();
        initNavigation();
        CircleProgressBar circle = new CircleProgressBar(this);
        circle.setColorSchemeResources(android.R.color.holo_orange_light);
        circle.setCircleBackgroundEnabled(true);
        RelativeLayout mainContainer = (RelativeLayout) findViewById(R.id.mainContainer);
        RelativeLayout.LayoutParams params =  new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        circle.setLayoutParams(params);

        TextView tv = new TextView(this);
        tv.setText("ttttttttttt");
        tv.setTextColor(getResources().getColor(R.color.primary_material_dark));
        tv.setLayoutParams(params);
        mainContainer.addView(circle);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(android.R.color.holo_orange_light);
        refresh.setOnRefreshListener(this);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.progress_scale);
//        animation.setDuration(500);
//        animation.setFillAfter(true);
        ObjectAnimator oa = ObjectAnimator.ofInt(new WrapCircle(circle), "width", 100);
        oa.setDuration(5000);
        oa.start();
    }

    public class WrapCircle{
        private View view;
        private int width;
        public WrapCircle(View view){
            this.view = view;
        }

        public int getWidth(){
            return view.getLayoutParams().width;
        }

        public void setWidth(int width){
            this.width = width;
            view.getLayoutParams().width = this.width;
            view.requestLayout();
        }
    }

    @Override
    public void initLists() {
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new NewsAdapter(this, dataList);
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
        iMainPresenter.getList(page);
    }

    @Override
    public void showLoading(String msg) {
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
        refresh.setRefreshing(false);
    }

//    @Override
//    public void showLoginDialog() {
//        Toast.makeText(this, "23333333", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void showUserInfo(User user) {
//        Glide.with(this).load("http://pic4.nipic.com/20090821/2267807_180803075_2.jpg")
//                .placeholder(R.mipmap.ic_person_white_48dp)
//                .error(R.mipmap.ic_person_white_48dp)
//                .into(mIvUserHead);
//        mTvUserName.setText(user.getUsername());
//    }
}
