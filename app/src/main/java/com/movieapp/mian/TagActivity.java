package com.movieapp.mian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.utils.Res;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    private final static int LOADMORE_WHAT=0x458;
    private final static int LOADINGNOM_WHAT=0x459;
    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CommonAdapter commonAdapter;


    private LoadMoreWrapper mLoadMoreWrapper;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private ImageView loadingNom;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==LOADINGNOM_WHAT){
                loadingNom.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            }else if (msg.what==LOADMORE_WHAT){
                loadMore();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        loadingNom = (ImageView) findViewById(R.id.id_tags_lsit_lading_nom);
        recyclerview = (RecyclerView)findViewById(R.id.id_tags_list_recyclerview);
        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linear);
        //添加动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        commonAdapter = new CommonAdapter<Integer>(mContext, R.layout.item_tagslist_contnet, list){

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
                holder.setImageResource(R.id.id_tags_list_pic,list.get(position));
                holder.setText(R.id.id_tags_list_desc,describes.get(position));
                holder.setText(R.id.id_tags_list_desc2,"200");
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(mContext,PlayActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                Toast.makeText(mContext, "onItemLongClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        initHeaderAndFooter();
        initEmptyView();

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener(){
            @Override
            public void onLoadMoreRequested(){
                handler.sendEmptyMessageDelayed(LOADMORE_WHAT,3000);
            }
        });
//        recyclerview.setAdapter(commonAdapter);
        //加载更多
        recyclerview.setAdapter(mLoadMoreWrapper);
        //无数据时
//        recyclerview.setAdapter(mEmptyWrapper);
    }

    private void initEmptyView(){
        mEmptyWrapper = new EmptyWrapper(commonAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, recyclerview, false));
    }
    private void initHeaderAndFooter(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
    }
    private void initData() {
        list=new ArrayList<>();
        describes=new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(Res.getResId("ic_test_" + i, R.drawable.class));
            describes.add("默认的描述:"+i);
        }
        handler.sendEmptyMessageDelayed(LOADINGNOM_WHAT,1000);
    }

    private void loadMore() {
        for (int i = 0; i < 10; i++){
            list.add(R.drawable.ic_test_2);
            describes.add("add:"+i);
        }
        recyclerview.getAdapter().notifyDataSetChanged();
    }
}
