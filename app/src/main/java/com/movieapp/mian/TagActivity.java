package com.movieapp.mian;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    private final static int LOAD_FIRST=0x456;
    private final static int LOAD_MORE=0x457;
    private final static  int pageSize=4;
    private Context mContext;
    private RecyclerView recyclerview;
    private List<MovieModel> movies;
    CommonAdapter commonAdapter;

    private LoadMoreWrapper mLoadMoreWrapper;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private ImageView loadingNom;

    int categoryid;
    Page page;

    IMoviceService moviceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        mContext=this;
        if(moviceService == null){
            moviceService =new MoviceServiceImpl(mContext);
        }
        EventBus.getDefault().register(this);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            categoryid= extras.getInt("categoryid");
        }
        initData();
        initView();
    }
    private void initData() {
        page=new Page();
        page.setPageNumber(1);
        page.setPageSize(pageSize);
        movies =new ArrayList<MovieModel>();
        loadData(page.getPageNumber(),LOAD_FIRST);
    }

    private void initView() {
        loadingNom = (ImageView) findViewById(R.id.id_tags_lsit_lading_nom);
        recyclerview = (RecyclerView)findViewById(R.id.id_tags_list_recyclerview);
        LinearLayoutManager linear = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(linear);
        //添加动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        commonAdapter = new CommonAdapter<MovieModel>(mContext, R.layout.item_tagslist_contnet, movies){

            @Override
            protected void convert(ViewHolder holder, MovieModel movieModel, int position) {
                ImageView view=holder.getView(R.id.id_tags_list_pic);
                Imageloader.getInstance(mContext).setImage(movieModel.getFenleiLink(),view,R.drawable.default_item_picture);
                holder.setText(R.id.id_tags_list_desc,movieModel.getTitle());
                holder.setText(R.id.id_tags_list_desc2,movieModel.getDescription());
            }
        };
        commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener<MovieModel>() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                LogicService.getInstance(mContext).toPlayActivity(o);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, MovieModel o, int position) {
                return false;
            }
        });

        initHeaderAndFooter();
        initEmptyView();

        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener(){
            @Override
            public void onLoadMoreRequested(){
                int size = movies.size();
                if (size>0){
                    loadData(page.getPageNumber(),LOAD_MORE);
                }

            }
        });
        //recyclerview.setAdapter(commonAdapter);
        if (movies ==null ||  movies.size()<=0){
            //无数据时
           recyclerview.setAdapter(mEmptyWrapper);
        }else {
            //加载更多
            recyclerview.setAdapter(mLoadMoreWrapper);
        }

    }

    private void initEmptyView(){
        mEmptyWrapper = new EmptyWrapper(commonAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, recyclerview, false));
    }
    private void initHeaderAndFooter(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(commonAdapter);
    }


    //加载数据
    public  void loadData(int pageNumber,int type){
        if (type==LOAD_MORE){
            pageNumber=pageNumber+1;
        }
        moviceService.getMoviesByCategoryId(mContext,CommonUtils.APPID,categoryid,pageNumber,page.getPageSize(),type);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMoviceByCategoryId(Event.getMoviceByCategoryId event) {
        int type=event.getType();
        page=event.getPage();
        List<MovieModel> movieModels = page.getList();
        if (movieModels!=null && movieModels.size()>0){
            movies.addAll(movieModels);
            if (type==LOAD_FIRST){
                loadingNom.setVisibility(View.GONE);
                recyclerview.setVisibility(View.VISIBLE);
            }
            //如果是最后一页就去掉加载更多
            if (page.isLastPage()){
                recyclerview.setAdapter(commonAdapter);
            }else {
                recyclerview.setAdapter(mLoadMoreWrapper);
            }
            recyclerview.getAdapter().notifyDataSetChanged();
        }else {
            Toast.makeText(mContext, "出现错误了", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
