package com.movieapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.bean.CategoryModel;
import com.movieapp.eventbus.Event;
import com.movieapp.mian.TagActivity;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.utils.CommonUtils;
import com.movieapp.widget.imageloader.Imageloader;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragmentTwo extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.id_swipe_refresh_widget)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<CategoryModel> categoryModels;
    CommonAdapter commonAdapter;

    IMoviceService moviceService;

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
        if (moviceService == null){
            moviceService=new MoviceServiceImpl(mContext);
        }
        categoryModels = new ArrayList<CategoryModel>();
        loadData(false);
    }

    @Override
    public void loadData() {
        loadData(false);
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

        commonAdapter = new CommonAdapter<CategoryModel>(mContext, R.layout.item_tags_content, categoryModels) {

            @Override
            protected void convert(ViewHolder holder, CategoryModel categoryModel, int position) {
                //                holder.setImageResource(R.id.id_tags_pic, list.get(position));
                ImageView view = holder.getView(R.id.id_tags_pic);
                Imageloader.getInstance(mContext).setImage(categoryModel.getIccon(),view,R.drawable.default_item_picture);
                holder.setText(R.id.id_tag_tv, categoryModel.getCategoryname());
            }
        };

        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                CategoryModel category= (CategoryModel) o;
                Logger.i("对象："+category.toString());
                Intent intent = new Intent(mContext, TagActivity.class);
                intent.putExtra("categoryid",category.getCategoryid());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
//                Toast.makeText(mContext, "onItemLongClick position:" + position + "内容:" + o.toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        recyclerview.setAdapter(commonAdapter);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    //加载数据
    public  void loadData(boolean isRefresh){
        moviceService.getAllCategory(getActivity().getApplication(), CommonUtils.APPID,isRefresh);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCategoryEvent(Event.getCategoryEvent event) {
        boolean isRefresh=event.isRefresh();
        List<CategoryModel> categorys = event.getCategoryModels();
        if (categorys!=null && categorys.size()>0){
            categoryModels.clear();
            categoryModels.addAll(categorys);
            if (isRefresh){
                swipeRefreshLayout.setRefreshing(false);
            }
            if (recyclerview.getAdapter() !=null){
                recyclerview.getAdapter().notifyDataSetChanged();
            }
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
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
