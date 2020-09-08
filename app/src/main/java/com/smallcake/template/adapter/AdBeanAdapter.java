package com.smallcake.template.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.smallcake.template.R;
import com.smallcake.template.bean.AdBean;
import com.smallcake.template.databinding.ItemAdBinding;

/**
 * Date: 2020/1/13
 * author: SmallCake
 */
public class AdBeanAdapter extends BaseQuickAdapter<AdBean, BaseDataBindingHolder<ItemAdBinding>> {
    public AdBeanAdapter() {
        super(R.layout.item_ad);
    }
    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemAdBinding> helper, AdBean item) {
        ItemAdBinding binding =  helper.getDataBinding();
        binding.setItem(item);
    }
}
