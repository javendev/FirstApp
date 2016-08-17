package com.movieapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.movieapp.R;
import com.movieapp.adapter.DividerItemDecoration;
import com.movieapp.adapter.HomeTagsAdaper;
import com.movieapp.bean.HomePage;
import com.movieapp.bean.HomeTags;
import com.movieapp.bean.MovieModel;
import com.movieapp.eventbus.Event;
import com.movieapp.mian.TagActivity;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.LogicService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.widget.imageloader.Imageloader;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
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

public class FragmentOne extends BaseFragment implements ViewPager.OnPageChangeListener, OnItemClickListener {
    private final static int LOADINGNOM_WHAT = 0x123;
    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;

    private ConvenientBanner convenientBanner;
    private List<String> transformerList = new ArrayList<String>();

    private List<MovieModel> topList;

    private HomeTagsAdaper mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<HomeTags> mDates;

    private EmptyWrapper mEmptyWrapper;
    private View ad;

    private IMoviceService moviceService;


    public FragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //确保只加载一次
        initTransformerList();
        EventBus.getDefault().register(this);

    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        if (moviceService == null) {
            moviceService = new MoviceServiceImpl(mContext);
        }
        if (topList == null){
            topList = new ArrayList<MovieModel>();
        }
        if (mDates == null){
            mDates = new ArrayList<HomeTags>();
        }

    }

    @Override
    public void loadData() {
        moviceService.getMain(mContext);
    }


    @Override
    protected void initEvent() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));
        ad = LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.ad_content, null);
        convenientBanner = (ConvenientBanner) ad.findViewById(R.id.convenientBanner);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, topList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setManualPageable(true);//设置不能手动影响
        //点击监听
        convenientBanner.setOnItemClickListener(this);
        //设置滑动监听
        convenientBanner.setOnPageChangeListener(this);

//        initDates();
        mAdapter = new HomeTagsAdaper(getActivity().getApplicationContext(), mDates);

        initHeaderAndFooter();
        initEmptyView();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);

        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<HomeTags>() {

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                int categoryId = o.getCategoryId();
                Intent intent = new Intent(mContext, TagActivity.class);
                intent.putExtra("categoryid",categoryId);
                startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        //广告栏点击
        LogicService.getInstance(mContext).toPlayActivity(topList.get(position));
    }

    public class LocalImageHolderView implements Holder<MovieModel> {
        private ImageView imageView;
        private TextView textView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.ad_item, null);
            imageView = (ImageView) view.findViewById(R.id.id_img);
            textView = (TextView) view.findViewById(R.id.id_tv);
            return view;
        }

        @Override
        public void UpdateUI(Context context, int position, MovieModel data) {
            textView.setText(data.getDescription());
            Imageloader.getInstance(mContext).setImage(data.getPiclink(), imageView, R.drawable.ic_test_2);
        }


    }

    private void initEmptyView() {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.empty_view, mRecyclerView, false));
    }

    private void initHeaderAndFooter() {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(ad);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMainPage(Event.getMainPage event) {
        HomePage homePage = event.getHomeTags();
        List<MovieModel> top = homePage.getTop();
        if (top!=null && top.size()>0){
            topList.clear();
            topList.addAll(top);
            convenientBanner.getViewPager().getAdapter().notifyDataSetChanged();
        }

        List<HomePage.Title> title = homePage.getTitle();
        if (title!=null && title.size()>0){
            for (int i=0;i<title.size();i++) {
                HomeTags tags=new HomeTags();
                tags.setTag(true);
                tags.setCategoryId(title.get(i).getCategory().getCategoryid());
                tags.setTagName(title.get(i).getCategory().getCategoryname());
                mDates.add(tags);

                tags=new HomeTags();
                tags.setTag(false);
                tags.setCategoryId(title.get(i).getCategory().getCategoryid());
                tags.setTagName(title.get(i).getCategory().getCategoryname());
                tags.setTagList(title.get(i).getContent());
                mDates.add(tags);
            }
        }

        mRecyclerView.getAdapter().notifyDataSetChanged();
        Logger.e("Top size:" + homePage.getTop().size() + " Title size:" + homePage.getTitle().size() +" mDates size:"+mDates.size());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        Logger.v("onResume");
        super.onResume();
//        //开始自动翻页
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
     * 各种翻页效果
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

    private void changeTransforms(int position) {
        String transforemerName = transformerList.get(position);
        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);
            //部分3D特效需要调整滑动速度
            if (transforemerName.equals("StackTransformer")) {
                convenientBanner.setScrollDuration(1200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
