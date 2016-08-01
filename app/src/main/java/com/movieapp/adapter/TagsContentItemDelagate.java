package com.movieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.movieapp.R;
import com.movieapp.bean.HomeTags;
import com.movieapp.bean.MovieModel;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Javen on 2016/7/25.
 */
public class TagsContentItemDelagate implements ItemViewDelegate<HomeTags> {
    RecyclerView recyclerView;
    private CommonAdapter<MovieModel> mAdapter;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_tagcontent;
    }

    @Override
    public boolean isForViewType(HomeTags item, int position) {
        return !item.isTag();
    }

    @Override
    public void convert(ViewHolder holder, HomeTags homeTags, int position) {
        List<MovieModel> tagList = homeTags.getTagList();
        recyclerView = holder.getView(R.id.id_main_tags_recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter=new CommonAdapter<MovieModel>(recyclerView.getContext(),R.layout.item_main_tagcontent_item,tagList) {

            @Override
            protected void convert(ViewHolder holder, MovieModel movie, int position) {
//                ImageView view = holder.getView(R.id.id_tagcontent_pic);
//                Imageloader.getInstance(view.getContext()).setImage(movie.getUrl(),view,R.drawable.default_item_picture);
                if (position % 2 == 0)
                holder.setImageResource(R.id.id_tagcontent_pic,R.drawable.ic_test_1);
                else
                    holder.setImageResource(R.id.id_tagcontent_pic,R.drawable.ic_test_2);
                holder.setText(R.id.id_tagcontent_name,movie.getName());
                holder.setText(R.id.id_tagcontent_desc,movie.getDesc());
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MovieModel>() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                Logger.i(o.getImageUrl());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
