package com.movieapp.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
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

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTwo extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.id_swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Integer> list;
    private List<String> describes;
    CommonAdapter commonAdapter;

    public FragmentTwo() {
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        describes = new ArrayList<>();
        //本地图片集合
        for (int position = 0; position < 9; position++) {
            list.add(Res.getResId("ic_test_" + position, R.drawable.class));
            describes.add("原始数据" + position);
        }
    }

    @Override
    protected void initEvent() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow
        );
        swipeRefreshLayout.setOnRefreshListener(this);
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        commonAdapter = new CommonAdapter<Integer>(mContext, R.layout.item_tags_content, list) {

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.id_tags_pic, list.get(position));
                holder.setText(R.id.id_tag_tv, describes.get(position));
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



    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(0, 3000);

        System.out.println("onRefresh()。。。。。");
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            swipeRefreshLayout.setRefreshing(false);
            list.add(0,R.drawable.ic_test_0);
            describes.add(0,"刷新添加的数据");
            recyclerview.getAdapter().notifyDataSetChanged();
        }
    };


}
