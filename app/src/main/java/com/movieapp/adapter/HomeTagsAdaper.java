package com.movieapp.adapter;

import android.content.Context;

import com.movieapp.bean.HomeTags;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Javen on 2016/7/25.
 */
public class HomeTagsAdaper extends MultiItemTypeAdapter<HomeTags> {
    public HomeTagsAdaper(Context context, List<HomeTags> datas) {
        super(context, datas);
        addItemViewDelegate(new TagsNameItemDelagate());
        addItemViewDelegate(new TagsContentItemDelagate(context));
    }
}
