package com.hxuanyu.mytools;


import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hxuanyu.mytools.utils.ActivityCollector;
import com.hxuanyu.mytools.utils.StatusBarUtil;
import com.hxuanyu.mytools.utils.slideback.DefaultSlideView;
import com.hxuanyu.mytools.utils.slideback.SlideBack;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary),0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            //getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
            //getWindow().setNavigationBarColor(Color.BLUE);
        }
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
        setSlideBack();
    }

    protected void setSlideBack(){
        //开启滑动关闭
        SlideBack.create()
                .slideView(new DefaultSlideView(this))
                .attachToActivity(this);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}