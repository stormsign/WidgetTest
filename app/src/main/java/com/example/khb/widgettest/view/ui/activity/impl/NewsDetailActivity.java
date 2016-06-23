package com.example.khb.widgettest.view.ui.activity.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.model.NewsEntity;
import com.example.khb.widgettest.view.ui.activity.INewsDetailActivity;
import com.example.khb.widgettest.view.ui.base.BaseActivity;

/**
 * Created by khb on 2016/6/23.
 */
public class NewsDetailActivity extends BaseActivity implements INewsDetailActivity {


    private TextView title;
    private ImageView titleImage;
    private TextView time;
    private TextView content;

    @Override
    public void init() {
        title = (TextView) findViewById(R.id.newsTitle);
        titleImage = (ImageView) findViewById(R.id.titleImage);
        time = (TextView) findViewById(R.id.time);
        content = (TextView) findViewById(R.id.content);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public View getLoadingParentView() {
        return null;
    }

    @Override
    public void initViewsAndEvents() {
        NewsEntity news = (NewsEntity) getIntent().getSerializableExtra("news");
        title.setText(news.getTitle());

        Glide.with(this).load(news.getImage()).error(R.mipmap.ic_launcher).into(titleImage);
        time.setText(news.getCreateTime()+"");
        content.setText(news.getDescription());
    }
}
