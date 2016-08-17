package com.movieapp.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentFour extends BaseFragment {
    private final static int LOAD_FIRST=0x456;
    private final static int LOAD_MORE=0x457;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;

    private List<MovieModel> choiceList;
    CommonAdapter commonAdapter;

    private LoadMoreWrapper mLoadMoreWrapper;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;

    Page page;
    private final static  int pageSize=2;
    IMoviceService moviceService;

    public FragmentFour() {
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_four, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        if(choiceList == null){
            choiceList = new ArrayList<MovieModel>();
        }
        if (moviceService == null){
            moviceService = new MoviceServiceImpl(mContext);
        }
        if (page == null){
            page=new Page();
            page.setPageNumber(1);
            page.setPageSize(pageSize);
        }
    }

    @Override
    public void loadData() {
        loadData(page.getPageNumber(),LOAD_FIRST);
    }

    @Override
    protected void initEvent() {
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        commonAdapter = new CommonAdapter<MovieModel>(mContext, R.layout.collects, choiceList) {

            @Override
            protected void convert(ViewHolder holder, MovieModel movieModel, int position) {
                ImageView view = holder.getView(R.id.id_collect_pic);
                Imageloader.getInstance(mContext).setImage(movieModel.getPiclink(),view,R.drawable.default_item_picture);
                holder.setImageResource(R.id.id_collect_hight, R.drawable.hight_img);
                holder.setImageResource(R.id.id_collect_vp, R.drawable.vp);
                holder.setImageResource(R.id.id_collect_people, R.drawable.people);
                holder.setText(R.id.id_collect_desc, movieModel.getDescription());
                holder.setText(R.id.id_collect_count, movieModel.getViewcount()+"");
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MovieModel>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MovieModel movie, int position) {
                LogicService.getInstance(mContext).toPlayActivity(movie);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MovieModel movie, int position) {
                return true;
            }
        });
        initHeaderAndFooter();
        initEmptyView();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(page.getPageNumber(),LOAD_MORE);
            }
        });
//        recyclerview.setAdapter(commonAdapter);
        //加载更多
        recyclerview.setAdapter(mLoadMoreWrapper);
        //无数据时
//        recyclerview.setAdapter(mEmptyWrapper);
    }

    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(commonAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.empty_view, recyclerview, false));
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
    }

    //加载数据
    public  void loadData(int pageNumber,int type){
        if (type==LOAD_MORE){
            pageNumber=pageNumber+1;
        }
        moviceService.getRankMovices(mContext,CommonUtils.APPID,CommonUtils.TANK_TYPE_CHOICE,pageNumber,page.getPageSize(),type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getRankMovicesChoice(Event.getRankMovicesChoice event) {
        page=event.getPage();
        int type = event.getType();
        List<MovieModel> movieModels = page.getList();
        if (movieModels!=null && movieModels.size()>0){
//            if (type == LOAD_FIRST){
//                choiceList.clear();
//            }
            choiceList.addAll(movieModels);
            //如果是最后一页就去掉加载更多
            Logger.e("是否是最后一页："+page.isLastPage());
            if (page.isLastPage()){
                recyclerview.setAdapter(commonAdapter);
            }
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
