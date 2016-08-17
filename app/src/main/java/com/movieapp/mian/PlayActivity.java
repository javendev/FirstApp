package com.movieapp.mian;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.bean.MovieModel;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.LogicService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.view.UI;
import com.movieapp.widget.imageloader.Imageloader;
import com.orhanobut.logger.Logger;
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
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity {
    @BindView(R.id.id_play_pic)
    ImageView playPic;
    @BindView(R.id.id_paly_rlTop)
    RelativeLayout topRelativeLayout;
    @BindView(R.id.id_play_list_recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.id_play_count)
    TextView playCount;

    private Context mContext;
//    private List<Integer> list;
//    private List<String> describes;
    CommonAdapter commonAdapter;

    private List<MovieModel> fours;
    IMoviceService moviceService;

    private String playUrl;
    private int videoid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        mContext = this;
        EventBus.getDefault().register(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            MovieModel movice = extras.getParcelable("movice");
            Logger.e(movice.toString());
            playUrl = movice.getVideolink();
            videoid = movice.getVideoid();
            int viewCount = movice.getViewcount();
            if (viewCount == 0){
                viewCount = 1;
            }
            Imageloader.getInstance(mContext).setImage(movice.getPlayerpiclink(), playPic, R.drawable.default_item_picture);
            String play_count=getResources().getString(R.string.play_count);
            playCount.setText(String.format(play_count,viewCount));
        }

        initData();
        initView();
    }

    private void initView() {
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        commonAdapter = new CommonAdapter<MovieModel>(mContext, R.layout.item_playlist_contnet, fours) {

            @Override
            protected void convert(ViewHolder holder, MovieModel movieModel, int position) {
                ImageView view = holder.getView(R.id.id_paly_list_pic);
                Imageloader.getInstance(mContext).setImage(movieModel.getPiclink(),view,R.drawable.default_item_picture);
                holder.setText(R.id.id_paly_list_desc,movieModel.getDescription());
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


    private void initData() {
        if (moviceService == null){
            moviceService = new MoviceServiceImpl(mContext);
        }
        if (fours == null){
            fours = new ArrayList<MovieModel>();
        }
        moviceService.getFour(mContext,videoid);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getFour(Event.getFour event) {

        List<MovieModel> movieModels = event.getFours();
        if (movieModels!=null && movieModels.size()>0){
            fours.clear();
            fours.addAll(movieModels);
            recyclerview.getAdapter().notifyDataSetChanged();
        }else {
            Toast.makeText(mContext, "出现错误了", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.id_paly_rlTop)
    public void onClick() {
        UI.showPayDialog(mContext);
        if (playUrl!=null){
//            LogicService.getInstance(mContext).viewPlay(playUrl);
            LogicService.getInstance(mContext).vitamioPlay(playUrl);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
