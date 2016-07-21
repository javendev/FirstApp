package com.movieapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.mian.TagActivity;
import com.movieapp.utils.Res;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class FragmentTwo extends Fragment {

    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CommonAdapter commonAdapter;

    public FragmentTwo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();
        View view=inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list = new ArrayList<>();
        describes = new ArrayList<>();
        //本地图片集合
        for (int position = 0; position < 9; position++) {
            list.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            describes.add("原始数据"+position);
        }

        commonAdapter = new CommonAdapter<Integer>(mContext, R.layout.item_tags_content, list) {

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.id_tags_pic, list.get(position));
                holder.setText(R.id.id_tag_tv,describes.get(position));
            }
        };

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext, TagActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemLongClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        recyclerview.setAdapter(commonAdapter);

    }
}
