package com.example.doublesucktopdemo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.doublesucktopdemo.ProductBean;
import com.example.doublesucktopdemo.R;

import java.util.List;

/**
 * Created by weioule
 * on 2019/6/26.
 */
public class RecommendAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {


    public RecommendAdapter(@Nullable List<ProductBean> data) {
        super(R.layout.recommend_item_layout, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductBean item) {
        helper.setImageResource(R.id.img, item.getImageResource());
        helper.setText(R.id.name, item.getName());
        helper.addOnClickListener(R.id.content);
    }
}
