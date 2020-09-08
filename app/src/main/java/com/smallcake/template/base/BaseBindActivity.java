package com.smallcake.template.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Date: 2020/1/4
 * author: SmallCake
 * 1.配置了泛型，DataBind的方式
 */
public abstract class BaseBindActivity<DB extends ViewDataBinding> extends BaseActivity {
    protected DB db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         db = DataBindingUtil.setContentView(this, getLayoutId());
    }
}
