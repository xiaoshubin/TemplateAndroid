package com.smallcake.template.inject;


import com.smallcake.template.base.BaseActivity;
import com.smallcake.template.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Date: 2019/11/25
 * author: SmallCake
 * 注入器
 */
@Singleton
@Component(modules = NetWorkMoudle.class)
public interface CommonComponent {
    void inject(BaseFragment baseFragment);
    void inject(BaseActivity baseActivity);
}
