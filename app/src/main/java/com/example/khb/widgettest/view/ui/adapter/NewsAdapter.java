package com.example.khb.widgettest.view.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khb.widgettest.R;
import com.example.khb.widgettest.model.NewsEntity;
import com.example.khb.widgettest.view.ui.activity.impl.PayDemoActivity;

import java.util.List;

/**
 * Created by khb on 2016/6/6.
 */
public class NewsAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<NewsEntity> list;

    public NewsAdapter(Context context, List<NewsEntity> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, PayDemoActivity.class));
    }

    public class NewsHolder extends RecyclerView.ViewHolder{
        TextView news;
        public NewsHolder(View itemView) {
            super(itemView);
            news = (TextView) itemView.findViewById(R.id.news);
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

            newsholder.news.setOnClickListener(this);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
