package com.example.khb.widgettest.view.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khb.widgettest.R;

/**
 * Created by khb on 2016/6/7.
 */
public class ViewOverrideManager {
    private View parentView;
    private ViewGroup.LayoutParams layoutParams;
    private ViewGroup container;
    private int viewIndex;
    public ViewOverrideManager(View view){
        this.parentView = view;
//        init();
    }

    private void init() {
        layoutParams = parentView.getLayoutParams();
        if (null != parentView.getParent()){
            container = (ViewGroup) parentView.getParent();
        }else {
            container = (ViewGroup) parentView.getRootView().findViewById(android.R.id.content);
        }
        for (int i=0; i<container.getChildCount(); i++){
            if (parentView == container.getChildAt(i)){
                viewIndex = i;
                break;
            }
        }
    }

    private void showLayout(View view){
        if (null == container){
            init();
        }
        if (container.getChildAt(viewIndex) != view){
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
            container.removeViewAt(viewIndex);
            container.addView(view, viewIndex, layoutParams);
        }

    }

    public void showLoading(String msg){
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.overrideview, null);
        TextView tvMsg = (TextView) view.findViewById(R.id.msg);
        tvMsg.setText(msg);
        tvMsg.setTextColor(parentView.getContext().getResources().getColor(R.color.primary_material_dark));
        showLayout(view);
//        Toast.makeText(parentView.getContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void restoreView(){
        showLayout(parentView);
    }

}
