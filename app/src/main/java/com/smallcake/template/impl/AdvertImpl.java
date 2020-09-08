package com.smallcake.template.impl;


import com.smallcake.template.api.AdvertApi;
import com.smallcake.template.bean.AdBean;
import com.smallcake.template.bean.BaseResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.smallcake.okhttp.retrofit2.RetrofitComposeUtils.bindIoUI;

/**
 * Date: 2019/11/25
 * author: SmallCake
 */
public class AdvertImpl implements AdvertApi {
    @Inject
    AdvertApi advertApi;
    @Inject
    public AdvertImpl() {}

    @Override
    public Observable<BaseResponse<AdBean>> startAd() {
        return bindIoUI(advertApi.startAd());
    }

}
