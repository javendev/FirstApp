package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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


public class FragmentFour extends Fragment {
    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CommonAdapter commonAdapter;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            loadMore();
        }
    };

    public FragmentFour() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity().getApplicationContext();
        View view=inflater.inflate(R.layout.fragment_four, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview);
//        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list = new ArrayList<>();
        describes = new ArrayList<>();
        //本地图片集合
        for (int position = 0; position < 10; position++) {
            list.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            describes.add("原始数据"+position);
        }
        commonAdapter = new CommonAdapter<Integer>(mContext, R.layout.collects, list){

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.id_collect_pic,list.get(position));
                holder.setImageResource(R.id.id_collect_hight, R.mipmap.hight_img);
                holder.setImageResource(R.id.id_collect_vp,R.mipmap.vp);
                holder.setImageResource(R.id.id_collect_people,R.mipmap.people);
                holder.setText(R.id.id_collect_desc,describes.get(position));
                holder.setText(R.id.id_collect_count,"200");
            }
        };


        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
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
        recyclerview.setAdapter(commonAdapter);
    }
}
