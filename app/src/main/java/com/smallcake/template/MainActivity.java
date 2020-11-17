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
