package com.movieapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.bean.MovieModel;
import com.movieapp.bean.Page;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.LogicService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.utils.CommonUtils;
import com.movieapp.widget.imageloader.Imageloader;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentThree extends BaseFragment {
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;

    private List<MovieModel> vipList;
    CommonAdapter mAdapter;
    Page page;
    private final static  int pageSize=50;
    IMoviceService moviceService;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        if (vipList == null){
            vipList = new ArrayList<>();
        }
        if (page == null){
            page=new Page();
            page.setPageNumber(1);
            page.setPageSize(pageSize);
        }
        if (moviceService == null){
            moviceService=new MoviceServiceImpl(mContext);
        }
    }

    @Override
    public void loadData() {
        loadData(page.getPageNumber());
    }


    @Override
    protected void initEvent() {
        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(linear);

        mAdapter = new CommonAdapter<MovieModel>(mContext, R.layout.item_vip_content, vipList) {
            @Override
            protected void convert(ViewHolder holder, MovieModel movieModel, int position) {
                holder.setImageResource(R.id.id_vip_hight, R.drawable.hight_img);
                holder.setImageResource(R.id.id_vip_vp, R.drawable.vp);
                ImageView view = holder.getView(R.id.id_vip_pic);
                Imageloader.getInstance(mContext).setImage(movieModel.getPiclink(),view,R.drawable.default_item_picture);
                holder.setText(R.id.id_vip_describes, movieModel.getDescription());
            }
        };

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MovieModel>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MovieModel movie, int position) {
                LogicService.getInstance(mContext).toPlayActivity(movie);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MovieModel movie, int position) {
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

    //加载数据
    public  void loadData(int pageNumber){
        moviceService.getRankMovices(mContext, CommonUtils.APPID,CommonUtils.TANK_TYPE_VIP,pageNumber,page.getPageSize(),0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getRankMovicesChoice(Event.getRankMovicesChoice event) {
        page=event.getPage();
        List<MovieModel> movieModels = page.getList();
        if (movieModels!=null && movieModels.size()>0){
            vipList.addAll(movieModels);
            recyclerview.getAdapter().notifyDataSetChanged();
        }else {
            Toast.makeText(mContext, "出现错误了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
