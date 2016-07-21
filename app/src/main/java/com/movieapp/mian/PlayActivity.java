package com.movieapp.mian;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.utils.Res;
import com.movieapp.view.UI;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private RecyclerView recyclerview;
    private List<Integer> list;
    private List<String> describes;
    CommonAdapter commonAdapter;

    private RelativeLayout topRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mContext=this;
        initData();
        initView();
    }
    private void initView() {
        topRelativeLayout = (RelativeLayout) findViewById(R.id.id_paly_rlTop);
        topRelativeLayout.setOnClickListener(this);
        recyclerview = (RecyclerView)findViewById(R.id.id_play_list_recyclerview);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //添加动画
        recyclerview.setItemAnimator(new DefaultItemAnimator());

        commonAdapter = new CommonAdapter<Integer>(mContext, R.layout.item_playlist_contnet, list){

            @Override
            protected void convert(ViewHolder holder, Integer integer, int position) {
//                holder.setImageResource(R.id.id_tags_list_pic,list.get(position));
//                holder.setText(R.id.id_tags_list_desc,describes.get(position));
//                holder.setText(R.id.id_tags_list_desc2,"200");
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
        list=new ArrayList<>();
        describes=new ArrayList<>();
        for (int i = 0; i < 10; i++){
            list.add(Res.getResId("ic_test_" + i, R.mipmap.class));
            describes.add("默认的描述:"+i);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.id_paly_rlTop:
                UI.showDialog(mContext,new UI.ItemOnListener(){

                    @Override
                    public void itemOnListener(View view) {
                        Logger.i("点击了item");
                    }

                    @Override
                    public void closeOnListener(View view) {
                        Logger.i("点击了关闭");
                        Toast.makeText(mContext,"点击了关闭",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void btnOnListener(View view) {
                        switch (view.getId()){
                            case R.id.btn_m:
                                Logger.i("包月");
                                Toast.makeText(mContext,"点击了包月",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.btn_y:
                                Logger.i("包年");
                                Toast.makeText(mContext,"点击了包年",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.btn_n:
                                Logger.i("终身");
                                Toast.makeText(mContext,"点击了终身免费",Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                break;
        }
    }
}
