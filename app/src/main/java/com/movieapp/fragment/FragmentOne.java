package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.movieapp.R;
import com.movieapp.utils.Res;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;


public class FragmentOne extends Fragment implements ViewPager.OnPageChangeListener{

    private ConvenientBanner convenientBanner;
    private List<Integer> adLocalImages = new ArrayList<Integer>();
    private List<String> adLocalDescribes = new ArrayList<String>();
    private List<Integer> images = new ArrayList<Integer>();
    private List<String> describes = new ArrayList<String>();



    private List<String> transformerList = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private CommonAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<String> mDatas = new ArrayList<>();
    private View ad;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadMore();
        }
    };
    public FragmentOne() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //确保只加载一次
        initTransformerList();

        loadTestDatas();
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.v("onCreateView");
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_one, container, false);
        initView(view);
        init();
        return view;
    }

    private void initView(View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initEmptyView()
    {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.empty_view, mRecyclerView, false));
    }


    private void init(){
        ad=LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.ad_content,null);
        convenientBanner= (ConvenientBanner) ad.findViewById(R.id.convenientBanner);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, adLocalImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setManualPageable(true);//设置不能手动影响
                 //设置滑动监听
                 convenientBanner.setOnPageChangeListener(this);
        mAdapter=new CommonAdapter(getActivity().getApplication(),R.layout.item_list, images) {

            @Override
            protected void convert(ViewHolder holder, Object o, int position) {
                holder.setImageResource(R.id.iv_pic, images.get(position-1));
                holder.setText(R.id.tv_text, describes.get(position-1));
            }
        };

        initHeaderAndFooter();
        initEmptyView();


        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener(){
            @Override
            public void onLoadMoreRequested(){
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        });

        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<Integer>(){
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Integer o, int position){
                Toast.makeText(getActivity().getApplicationContext(), "pos = " + position, Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Integer o, int position){
                return false;
            }
        });
    }

    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;
        private TextView textView;
        @Override
        public View createView(Context context) {
            View view=LayoutInflater.from(context).inflate(R.layout.ad_item,null);
            imageView= (ImageView) view.findViewById(R.id.id_img);
            textView= (TextView) view.findViewById(R.id.id_tv);
            return view;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
            textView.setText(adLocalDescribes.get(position));
        }
    }

    private void initHeaderAndFooter(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(ad);
    }


    private  void  loadMore(){
        for (int i = 0; i < 10; i++)
        {
//            mDatas.add("Add:" + i);
            images.add(R.mipmap.ic_test_4);
            describes.add("Add:" + i);
        }
//        mLoadMoreWrapper.notifyDataSetChanged();
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }
    /*
   加入测试Views
   * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++) {
            adLocalImages.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            adLocalDescribes.add("描述"+position);

            images.add(Res.getResId("ic_test_" + position, R.mipmap.class));
            describes.add("原始数据:"+position);
        }
    }

    private void initDatas() {
        for (int i = 'A'; i <= 'z'; i++) {
            mDatas.add((char) i + "");
        }
    }



    // 开始自动翻页
    @Override
    public void onResume() {
        Logger.v("onResume");
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        Logger.v("onPause");
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onStop() {
        Logger.v("onStop");
        super.onStop();
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
//        Logger.e("监听到翻到第"+position+"了");
        changeTransforms(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     *  各种翻页效果
     */
    private void initTransformerList() {
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());
    }

    private  void changeTransforms(int position) {
        String transforemerName = transformerList.get(position);
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer= (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true,transforemer);
            //部分3D特效需要调整滑动速度
            if(transforemerName.equals("StackTransformer")){
                convenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
