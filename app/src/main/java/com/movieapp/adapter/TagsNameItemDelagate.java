package com.movieapp.adapter;

import com.movieapp.R;
import com.movieapp.bean.HomeTags;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Javen on 2016/7/25.
 */
public class TagsNameItemDelagate implements ItemViewDelegate<HomeTags> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_tagname;
    }

    @Override
    public boolean isForViewType(HomeTags item, int position) {
        return item.isTag();
    }

    @Override
    public void convert(ViewHolder holder, HomeTags homeTags, int position) {
        holder.setText(R.id.id_ch_name,homeTags.getTagName());
    }
}
