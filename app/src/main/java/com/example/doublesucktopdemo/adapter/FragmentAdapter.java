package com.example.doublesucktopdemo.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.doublesucktopdemo.ProductBean;
import com.example.doublesucktopdemo.R;
import com.example.doublesucktopdemo.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by weioule
 * on 2019/6/26.
 */
public class FragmentAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    public FragmentAdapter(@Nullable List<ProductBean> data) {
        super(R.layout.frmagent_adapter_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductBean item) {
        helper.setImageResource(R.id.img, item.getImageResource());
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_price, item.getPrice());
        helper.setText(R.id.tv_content, item.getContent());
        helper.addOnClickListener(R.id.content_view);

        //30是spacing: 3x10
        int actual_width = (Utils.getScreenWidth(mContext) - Utils.dp2px(30)) / 2;
        Drawable drawable = mContext.getResources().getDrawable(item.getImageResource());
        int image_width = drawable.getIntrinsicWidth();
        int image_height = drawable.getIntrinsicHeight();

        int iamgHeight = image_height * actual_width / image_width;

        RoundedImageView img = helper.getView(R.id.img);
        ViewGroup.LayoutParams params = img.getLayoutParams();
        params.height = iamgHeight;
        img.setImageDrawable(drawable);

        helper.setVisible(R.id.tv_look_similar, helper.getAdapterPosition() % 3 == 0);
        helper.setVisible(R.id.iv_shop_cart, helper.getAdapterPosition() % 3 != 0);
    }
}
