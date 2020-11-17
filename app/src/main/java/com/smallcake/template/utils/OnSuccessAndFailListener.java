package com.smallcake.template.utils;


import android.app.Dialog;
import android.text.TextUtils;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.HttpException;
import com.smallcake.template.bean.BaseResponse;
import com.smallcake.utils.L;
import com.smallcake.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;


/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public abstract class OnSuccessAndFailListener<T> extends DisposableObserver<T> {
    private static final String TAG = "OnSuccessAndFailListene";
    private Dialog loadDialog;

    private SwipeRefreshLayout refreshLayout;

    private void showLoading() {
        if (loadDialog != null) loadDialog.show();
        if (refreshLayout != null) refreshLayout.setRefreshing(true);
    }

    private void dismissLoading() {
        if (loadDialog != null) loadDialog.dismiss();
        if (refreshLayout != null) refreshLayout.setRefreshing(false);
    }

    public OnSuccessAndFailListener() {}
    public OnSuccessAndFailListener(Dialog loadDialog) {
        this.loadDialog = loadDialog;
    }
    public OnSuccessAndFailListener(SwipeRefreshLayout refresh) {
        this.refreshLayout = refresh;
    }


    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
    }

    @Override
    public void onNext(T t) {
        dismissLoading();
        try {
            BaseResponse baseResponse = (BaseResponse) t;
            if (baseResponse.getCode()==0) {
                onSuccess(t);
            } else {
                onErr(baseResponse);
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissLoading();
        String message;
        if (e instanceof SocketTimeoutException) {
            message = "网络连接超时！";
        } else if (e instanceof ConnectException) {
            message = "网络无法连接！";
        } else if (e instanceof HttpException) {
            message = "网络中断，请检查您的网络状态！";
        } else if (e instanceof UnknownHostException || e instanceof NullPointerException) {
            message = "网络错误，请检查您的网络状态！";
        } else {
            message = e.getMessage();
        }
        if (!TextUtils.isEmpty(message)) ToastUtil.showLong(message);
    }

    @Override
    public void onComplete() {}

    protected abstract void onSuccess(T t);

    protected void onErr(BaseResponse errResponse) {
        String errMsg = errResponse.getMessage();
        L.e(TAG, "网络数据异常：" + errMsg);
        switch (errResponse.getCode()) {
            case 401://跳登录
                break;
            default:
                ToastUtil.showLong(errMsg);
                break;
        }

    }
}

