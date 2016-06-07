package com.example.khb.widgettest.view.widget;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by khb on 2016/6/7.
 */
public class ViewOverrideManager {
    private Context context;
    public ViewOverrideManager(Context context){this.context = context;}

    private void showLayout(View view){
    }

    public void showLoading(String msg){
//        View view = LayoutInflater.from(context).inflate(R.layout.overrideview, null);
//        TextView tvMsg = (TextView) view.findViewById(R.id.msg);
//        tvMsg.setText(msg);
//        showLayout();
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
