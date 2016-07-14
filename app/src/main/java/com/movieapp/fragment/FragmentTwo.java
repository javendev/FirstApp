package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.adapter.CustomCommonAdapter;
import com.movieapp.utils.Res;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;


public class FragmentTwo extends Fragment {

    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CustomCommonAdapter commonAdapter;
    LoadMoreWrapper loadMoreWrapper;

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            loadMore();
//        }
//    };

    public FragmentTwo() {
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
        View view=inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.id_recyclerview);
//        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);

        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        list = new ArrayList<>();
        describes = new ArrayList<>();
        //本地图片集合
        for (int position = 0; position < 9; position++) {
            list.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            describes.add("原始数据"+position);
        }

        commonAdapter = new CustomCommonAdapter<Integer>(mContext, R.layout.tags, list) {
            @Override
            protected void convertView(ViewHolder holder, Integer s, int position) {
                holder.setImageResource(R.id.id_tags_pic, list.get(position));
                holder.setText(R.id.id_tag_tv,describes.get(position));
            }
        };
//        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
//        headerAndFooterWrapper.addHeaderView(LayoutInflater.from(mContext).inflate(R.layout.item_list, recyclerview, false));

//        loadMoreWrapper = new LoadMoreWrapper(headerAndFooterWrapper);
//        loadMoreWrapper.setLoadMoreView(R.layout.default_loading);
//        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                handler.sendEmptyMessageDelayed(0, 3000);
//            }
//        });

        commonAdapter.setCustomOnItemClickListener(new CustomCommonAdapter.OnItemClickListener() {
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
    /**
     * 加载更多
     */
    private void loadMore() {
        for (int i=1;i<=10;i++) {
            list.add(R.mipmap.ic_test_6);
            describes.add("Load More "+i);
        }
        recyclerview.getAdapter().notifyDataSetChanged();

    }

}
