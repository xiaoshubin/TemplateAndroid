package com.smallcake.template;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.lsxiao.apollo.core.Apollo;
import com.smallcake.okhttp.retrofit2.RetrofitHttp;
import com.smallcake.template.utils.LogHeaderInterceptor;
import com.smallcake.utils.SmallUtils;
import com.smallcake.template.base.Contants;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmallUtils.init(this);//工具类
        RetrofitHttp.init(this, Contants.BASE_URL);//网络类
//        RetrofitHttp.init(this, Contants.BASE_URL, new LogHeaderInterceptor());//自定义拦截器，方便添加公共Header
        Apollo.init(AndroidSchedulers.mainThread(), this);//事件通知
    }
    //方法数量过多，合并
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
