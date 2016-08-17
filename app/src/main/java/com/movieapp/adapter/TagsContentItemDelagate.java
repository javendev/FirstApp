package com.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.movieapp.R;
import com.movieapp.bean.HomeTags;
import com.movieapp.bean.MovieModel;
import com.movieapp.service.LogicService;
import com.movieapp.widget.imageloader.Imageloader;
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
    private Context mContext;

    public  TagsContentItemDelagate(Context context){
        mContext = context;
    }

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
                ImageView view = holder.getView(R.id.id_tagcontent_pic);
                holder.setText(R.id.id_tagcontent_desc,movie.getDescription());
                holder.setText(R.id.id_tagcontent_name,movie.getTitle());
                Imageloader.getInstance(view.getContext()).setImage(movie.getPiclink(),view,R.drawable.default_item_picture);
            }
        };
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MovieModel>() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                LogicService.getInstance(mContext).toPlayActivity(o);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(mAdapter);
    }
}
