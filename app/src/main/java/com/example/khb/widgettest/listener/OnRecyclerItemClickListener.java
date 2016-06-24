package com.example.khb.widgettest.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by khb on 2016/6/23.
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private GestureDetectorCompat gestureDetectorCompat;

    public OnRecyclerItemClickListener(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), new MyGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (null != childView){
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childView);
                childViewHolder.itemView.onTouchEvent(e);
                int position = recyclerView.getChildAdapterPosition(childView);
                onItemClick(childViewHolder, position);
            }
            return true;
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder childViewHolder, int position);
}
