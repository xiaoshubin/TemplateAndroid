package com.smallcake.template;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.gyf.immersionbar.ImmersionBar;
import com.smallcake.template.base.BaseBindActivity;
import com.smallcake.template.databinding.ActivityMainBinding;
import com.smallcake.template.fragment.JobFragment;
import com.smallcake.template.fragment.MineFragment;
import com.smallcake.template.fragment.OrderFragment;
import com.smallcake.template.listener.OnBottomNavClickListener;
import com.smallcake.template.utils.BottomNavUtils;

/**
 * 主要包含：
 * 1.找活 {@link JobFragment}
 * 2.订单 {@link OrderFragment}
 * 3.我的 {@link MineFragment}
 */
//        AndPermission.with(this)
//                .runtime()
//                .permission(Permission.WRITE_EXTERNAL_STORAGE,Permission.READ_EXTERNAL_STORAGE)
//                .onGranted(permissions -> {
//                    ToastUtil.showShort("已经获取了权限");
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                        ToastUtil.showShort("你拒绝了获取此权限！");
//                        // 这些权限被用户总是拒绝。
//                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
//                            new AlertDialog.Builder(MainActivity.this)
//                                    .setTitle("权限申请")
//                                    .setMessage("需要此权限才能使用此功能，去设置？")
//                                    .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            AppUtils.goIntentSetting(MainActivity.this);
//                                        }
//                                    })
//                                    .setNegativeButton("取消",null)
//                                    .show();
//                        }
//                    }
//                })
//                .start();
public class MainActivity extends BaseBindActivity<ActivityMainBinding> {
    private String[] tabNames = new String[]{"找活", "订单", "我的"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarDarkFont(true).autoDarkModeEnable(true).init();
        initViewPager();
    }

    private void initViewPager() {
        final Fragment[] fragments = new Fragment[]{new JobFragment(), new OrderFragment(), new MineFragment()};
        final FragmentStatePagerAdapter fragmentPagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager(),0){

            @Override
            public int getCount() {
                return fragments.length;
            }
            @NonNull
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabNames[position];
            }
        };
        db.viewPager.setAdapter(fragmentPagerAdapter);
        db.viewPager.setOffscreenPageLimit(tabNames.length);
        BottomNavUtils.initTabBindViewPager(db.tabLayout, db.viewPager, new OnBottomNavClickListener() {
            @Override
            public void onNavClick(int index) {

            }
        });




    }
}
