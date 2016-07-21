package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.utils.Res;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FragmentThree extends Fragment {
    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CommonAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();
        View view=inflater.inflate(R.layout.fragment_third, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview);
        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(linear);
        list = new ArrayList<>();
        describes = new ArrayList<>();
        //本地图片集合
        for (int position = 0; position < 10; position++) {
            list.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            describes.add("原始数据"+position);
        }

        mAdapter= new CommonAdapter<Integer>(mContext, R.layout.item_vip_content, list){

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.id_vip_hight, R.mipmap.hight_img);
                holder.setImageResource(R.id.id_vip_pic,list.get(position));
                holder.setImageResource(R.id.id_vip_vp,R.mipmap.vp);
                holder.setText(R.id.id_vip_describes,describes.get(position));
            }
        };

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemLongClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        recyclerview.setAdapter(mAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
}
