package com.smallcake.utils;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 接受CharSequence类型的msg
 * T改为ToastUtil避免和泛型T冲突
 * Toast声明为静态，避免多次点击疯狂弹出
 */
public class ToastUtil {
    private static android.widget.Toast toast;
    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void showLong(CharSequence message) {
        show(message, android.widget.Toast.LENGTH_LONG);
    }
    public static void showShort(CharSequence message) {
        show(message, android.widget.Toast.LENGTH_SHORT);
    }

    public static void showGravityLong( CharSequence message, int gravity) {
        showGravity(SmallUtils.getApp(),message,gravity, android.widget.Toast.LENGTH_LONG);
    }
    public static void showGravityShort( CharSequence message, int gravity) {
        showGravity(SmallUtils.getApp(),message,gravity, android.widget.Toast.LENGTH_SHORT);
    }

    public static void showMyToast(String message, final int cnt) {
        final android.widget.Toast toast = android.widget.Toast.makeText(SmallUtils.getApp(), message, android.widget.Toast.LENGTH_LONG);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }

    private static void show(CharSequence message,int duration){
        if(toast==null){
            toast= android.widget.Toast.makeText(SmallUtils.getApp(),message, duration);
        }else{
            toast.setText(message);
        }
        toast.show();

    }
    private static void showGravity(Context context,CharSequence message, int gravity,int duration){
        if(toast==null){
            toast= android.widget.Toast.makeText(SmallUtils.getApp(),message, duration);
        }else{
            toast.setText(message);
        }
        toast.setGravity(gravity,0, (int) (64 * context.getResources().getDisplayMetrics().density + 0.5));
        toast.show();
    }

}
