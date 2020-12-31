package com.smallcake.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * MyApplication --  com.smallcake.okhttp
 * Created by Small Cake on  2018/7/25 8:58.
 * 下载需要网络权限和读写权限
 * <uses-permission android:name="android.permission.INTERNET"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * 使用'me.jessyan:progressmanager:1.5.0'监听真实的下载进度
 * implementation 'me.jessyan:progressmanager:1.5.0'
 */

public class DownloadUtils {
    private static DownloadUtils instance;
    private OkHttpClient okHttpClient;
    private Handler mHandler; //所有监听器在 Handler 中被执行,所以可以保证所有监听器在主线程中被执行

    public static DownloadUtils getInstance() {
        if (instance == null) instance = new DownloadUtils();
        return instance;
    }

    private DownloadUtils() {
        this.mHandler = new Handler(Looper.getMainLooper());
        okHttpClient = ProgressManager.getInstance().with(new OkHttpClient.Builder()).build();
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(ProgressInfo progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

    /**
     * @param url      下载连接
     * @param saveDir  储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final String saveName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("下载失败", e.getMessage());
                // 下载失败
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Okhttp/Retofit 下载监听
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    File file = new File(saveDir, saveName);
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 下载完成
                            listener.onDownloadSuccess();
                        }
                    });

                } catch (Exception e) {
                    Log.e("下载异常", e.getMessage());
                    // 下载失败
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed();
                        }
                    });
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        });
        ProgressManager.getInstance().addResponseListener(url, new ProgressListener() {
            @Override
            public void onProgress(ProgressInfo progressInfo) {
                listener.onDownloading(progressInfo);
            }

            @Override
            public void onError(long l, Exception e) {
                listener.onDownloadFailed();
            }
        });
    }

//    /**
//     *
//     * @param activity 上下文
//     * @param down_url 下载链接
//     * @param content 更新描述
//     * @param isMust 是否强制更新
//     * R.layout.dialog_update 确定框的布局，具体看XPopup
//     */
//    public static void showAppUpdate(Activity activity, String down_url, String content,boolean isMust) {
//        new XPopup.Builder(activity)
//                .dismissOnBackPressed(false)
//                .dismissOnTouchOutside(false)
//                .asConfirm("发现新版本",
//                        content,
//                        "稍后更新",
//                        "立即更新",
//                        () -> {
//                            downloadApk(activity, down_url);
//                        },
//                        null,
//                        isMust, R.layout.dialog_update)
//                .show();
//    }

//    private static void downloadApk(Activity context, String down_url) {
//        ProgressDialog progressDialog = new ProgressDialog(context);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setMessage("更新进度");
//        progressDialog.show();
//        String path = context.getExternalCacheDir().getAbsolutePath() + File.separator;
//        String name = "jiangxin.apk";
//        DownloadUtils.getInstance().download(down_url, path, name, new DownloadUtils.OnDownloadListener() {
//            @Override
//            public void onDownloadSuccess() {
//                ToastUtil.showShort("恭喜你下载成功，开始安装！");
//                String successDownloadApkPath = path + name;
//                AppUtils.installApk(context, successDownloadApkPath);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ActivityCollector.finishAll();
//                    }
//                }, 3000);
//            }
//
//            @Override
//            public void onDownloading(ProgressInfo progressInfo) {
//                progressDialog.setProgress(progressInfo.getPercent());
//                boolean finish = progressInfo.isFinish();
//                if (!finish) {
//                    long speed = progressInfo.getSpeed();
//                    progressDialog.setMessage("下载速度(" + (speed > 0 ? FormatUtils.formatSize(speed) : speed) + "/s)");
//                } else {
//                    progressDialog.setMessage("下载完成！");
//                }
//            }
//
//            @Override
//            public void onDownloadFailed() {
//                progressDialog.dismiss();
//                context.runOnUiThread(() -> new XPopup.Builder(context)
//                        .isDestroyOnDismiss(false)
//                        .dismissOnTouchOutside(false)
//                        .asConfirm("下载出错", "请重启应用!",
//                                () -> ActivityCollector.finishAll(),
//                                () -> ActivityCollector.finishAll()
//                        ).show());
//
//
//            }
//        });
//
//    }

}
