package com.example.khb.widgettest.factory;

import android.support.v4.app.Fragment;

import com.example.khb.widgettest.utils.Constants;
import com.example.khb.widgettest.view.ui.base.BaseFragment;
import com.example.khb.widgettest.view.ui.fragment.MapFragment;
import com.example.khb.widgettest.view.ui.fragment.NewsFragment;

import java.util.HashMap;

/**
 * Created by khb on 2016/6/6.
 */
public class FragmentFactory {



    private static HashMap<Integer, Fragment> hashMap = new HashMap<Integer, Fragment>();

    public static BaseFragment getFragment(int position) {
        BaseFragment baseFragment = null;
        if (hashMap.containsKey(position)) {
            if (hashMap.get(position) != null) {
                baseFragment = (BaseFragment) hashMap.get(position);
            }
        } else {
            switch (position) {
                case Constants.FRAGMENT_NEWS:
                    baseFragment = new NewsFragment();
                    break;
                case Constants.FRAGMENT_MAP:
                    baseFragment = new MapFragment();
                    break;
            }
            hashMap.put(position, baseFragment);
        }
        return baseFragment;
    }

}
