package com.smallcake.template.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.smallcake.template.R;
import com.smallcake.template.base.BaseBindFragment;
import com.smallcake.template.databinding.FragmentJobBinding;
import com.smallcake.template.listener.OnTabClickListener;
import com.smallcake.template.utils.BannerUtils;
import com.smallcake.template.utils.TabUtils;
import com.smallcake.utils.L;
import com.smallcake.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 找活
 */
public class JobFragment extends BaseBindFragment<FragmentJobBinding> {
    private static final String TAG = "JobFragment";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_job;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {
        L.e(TAG,"onBindView");

    }

    @Override
    protected void onLazyLoad() {
        L.e(TAG,"onLazyLoad");
        initView();
        onEvent();
        loadData();
    }

    private void initView() {
        //轮播图片
        List<Object> imgs = new ArrayList<>();
        imgs.add("https://cdn.dribbble.com/users/63407/screenshots/9323216/media/84481f7fff0aa3029aaf1f325923a71c.png");
        imgs.add("https://cdn.dribbble.com/users/1027121/screenshots/9326881/media/a518baf13a1bae89880832c0085ac7e3.gif");
        imgs.add("https://cdn.dribbble.com/users/59947/screenshots/9329817/media/4431b9a8cd9861c339e24fd7842d97b0.jpg");
        imgs.add("https://cdn.dribbble.com/users/78464/screenshots/9323218/media/85bb8e343ae3556f3ab95bb85220357e.jpg");
        BannerUtils.initBanner(db.banner,imgs);

        //TabLayout
        List<String> tabNames = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tabNames.add("Tab"+i);
        }
        TabUtils.setDefaultTab(this.getContext(), db.tabLayout, tabNames, new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                ToastUtil.showShort("点击了Tab"+position);
            }
        });
    }
    //网络请求
    private void loadData() {
    }

    private void onEvent() {
        //下拉刷新
        db.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

}
