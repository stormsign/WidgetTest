package com.example.khb.widgettest.view.ui.impl;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khb.widgettest.R;
import com.example.khb.widgettest.view.widget.StatusCompat;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView mNavigationView;
    private Toolbar title;
    private ImageView mIvUserHead;

    private TextView mTvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitle();
        initView();
    }

    private void initTitle(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        mNavigationView = (NavigationView) findViewById(R.id.navigation);
        title = (Toolbar) findViewById(R.id.title);
        setSupportActionBar(title);
        title.setNavigationIcon(R.mipmap.ic_person_outline_white_48dp);
        StatusCompat.compat(this, getResources().getColor(android.R.color.holo_orange_dark));

    }

    private void initView() {
        mIvUserHead = (ImageView) findViewById(R.id.iv_user_head);
        mTvUserName = (TextView) findViewById(R.id.tv_user_name);
//        Glide.with(this).load(R.mipmap.ic_person_outline_white_48dp).placeholder(R.mipmap.ic_person_outline_white_48dp).into(mIvUserHead);
        mTvUserName.setText("--");
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
