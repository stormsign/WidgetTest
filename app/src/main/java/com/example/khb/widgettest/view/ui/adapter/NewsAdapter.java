package com.example.khb.widgettest.view.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.khb.widgettest.R;
import com.example.khb.widgettest.model.NewsEntity;

import java.util.List;

/**
 * Created by khb on 2016/6/6.
 */
public class NewsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<NewsEntity> list;

    public NewsAdapter(Context context, List<NewsEntity> list){
        this.context = context;
        this.list = list;
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        public TextView news;
        public ImageView newsImage;
        public NewsHolder(View itemView) {
            super(itemView);
            news = (TextView) itemView.findViewById(R.id.news);
            newsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        }
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsHolder){
            String str = list.get(position).getTitle();
            NewsHolder newsholder = (NewsHolder)holder;
            newsholder.news.setText(str);
            Glide.with(context).load(list.get(position).getImage()).error(R.mipmap.ic_launcher).into(newsholder.newsImage);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
