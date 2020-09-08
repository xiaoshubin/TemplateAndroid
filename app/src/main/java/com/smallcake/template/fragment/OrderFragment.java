package com.smallcake.template.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.smallcake.template.R;
import com.smallcake.template.base.BaseBindFragment;
import com.smallcake.template.databinding.FragmentOrderBinding;
import com.smallcake.utils.L;

/**
 * Date: 2020/1/3
 * author: SmallCake
 */
public class OrderFragment extends BaseBindFragment<FragmentOrderBinding> {
    private static final String TAG = "ShopFragment";
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {
        L.e(TAG,"onBindView");
    }

    @Override
    protected void onLazyLoad() {
        L.e(TAG,"onLazyLoad");

    }
}
