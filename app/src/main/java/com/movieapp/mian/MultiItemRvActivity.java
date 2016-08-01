package com.movieapp.mian;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.adapter.HomeTagsAdaper;
import com.movieapp.bean.HomeTags;
import com.movieapp.bean.MovieModel;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultiItemRvActivity extends Activity {
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    HomeTagsAdaper adapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

    private List<HomeTags> mDates = new ArrayList<HomeTags>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initDates();
        adapter=new HomeTagsAdaper(this,mDates);
        initHeaderAndFooter();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<HomeTags>(){

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                Toast.makeText(MultiItemRvActivity.this, "Click:" + position + " => " + o.isTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                Toast.makeText(MultiItemRvActivity.this, "LongClick:" + position + " => " + o.isTag(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        mRecyclerView.setAdapter(mLoadMoreWrapper);

    }

    private void initHeaderAndFooter(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        TextView textView=new TextView(this);
        textView.setText("Head");
        mHeaderAndFooterWrapper.addHeaderView(textView);
    }

    private void initDates() {
        HomeTags tags=new HomeTags();
        tags.setTag(true);
        tags.setTagName("高清");
        mDates.add(tags);

        tags= new HomeTags();
        tags.setTag(false);
        List<MovieModel> movies = new ArrayList<MovieModel>();
        movies.add(new MovieModel("高清名称1","http://www.baidu.com","http://imgstore.cdn.sogou.com/app/a/100540002/714860.jpg","高清这是是否打算对方"));
        movies.add(new MovieModel("高清名称2","http://www.baidu.com","http://img5.imgtn.bdimg.com/it/u=1390800033,3298177266&fm=206&gp=0.jpg","高清这是是否打算对方2"));
        tags.setTagList(movies);
        mDates.add(tags);

        tags=new HomeTags();
        tags.setTag(true);
        tags.setTagName("VIP");
        mDates.add(tags);

        tags= new HomeTags();
        tags.setTag(false);
        movies = new ArrayList<MovieModel>();
        movies.add(new MovieModel("VIP名称1","http://www.baidu.com","http://www.sucaitianxia.com/Photo/pic/201001/gefnegs37.jpg","VIP这是是否打算对方"));
        movies.add(new MovieModel("VIP名称2","http://www.baidu.com","http://img.tuku.cn/file_big/201503/99ac63ce52d144218db0d18c5faf44ba.jpg","VIP这是是否打算对方2"));
        movies.add(new MovieModel("VIP名称3","http://www.baidu.com","http://image.tianjimedia.com/uploadImages/2012/236/2H2TR02NKWAA.jpg","VIP这是是否打算对方3"));
        movies.add(new MovieModel("VIP名称4","http://www.baidu.com","http://ww2.sinaimg.cn/large/4a8cd6e5jw9ekardhvkavj21e011iaod.jpg","VIP这是是否打算对方4"));
        tags.setTagList(movies);
        mDates.add(tags);

    }
}
