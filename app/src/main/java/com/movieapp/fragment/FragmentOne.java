package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.movieapp.R;
import com.movieapp.adapter.HomeTagsAdaper;
import com.movieapp.bean.HomeTags;
import com.movieapp.bean.MovieModel;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.widget.imageloader.Imageloader;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FragmentOne extends Fragment implements ViewPager.OnPageChangeListener, OnItemClickListener {
    private final static int LOADINGNOM_WHAT=0x123;
    private Context mContext;
    private ConvenientBanner convenientBanner;
    private List<String> adLocalImages = new ArrayList<String>();
    private List<String> adLocalDescribes = new ArrayList<String>();
    private List<String> transformerList = new ArrayList<String>();

    private RecyclerView mRecyclerView;
    private HomeTagsAdaper mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<HomeTags> mDates;

    private EmptyWrapper mEmptyWrapper;
    private View ad;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.v("onCreateView");
        mContext=getActivity().getApplicationContext();
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_one, container, false);
        initView(view);
        init();
        return view;
    }

    private void initView(View view){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_recyclerview);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
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
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setManualPageable(true);//设置不能手动影响
                 //点击监听
                 convenientBanner .setOnItemClickListener(this);
                 //设置滑动监听
                 convenientBanner.setOnPageChangeListener(this);
        initDates();
        mAdapter=new HomeTagsAdaper(getActivity().getApplicationContext(),mDates);

        initHeaderAndFooter();
        initEmptyView();
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);

        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener<HomeTags>(){

            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "Click:" + position + " => " + o.isTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, HomeTags o, int position) {
                Toast.makeText(getActivity().getApplicationContext(), "LongClick:" + position + " => " + o.isTag(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Logger.e("广告点击了："+position);

        UserModel userModel = new UserModel();
        userModel.setUserid("123466879");
        EventBus.getDefault().post(new Event.buildUserEvent(userModel));

    }

    public class LocalImageHolderView implements Holder<String> {
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
        public void UpdateUI(Context context, final int position, String data) {
            textView.setText(adLocalDescribes.get(position));
            Imageloader.getInstance(mContext).setImage(data,imageView,R.drawable.ic_test_2);
        }
    }
    private void initEmptyView(){
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(getActivity().getApplication()).inflate(R.layout.empty_view, mRecyclerView, false));
    }
    private void initHeaderAndFooter(){
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mHeaderAndFooterWrapper.addHeaderView(ad);
    }


    private void initDates() {
        mDates = new ArrayList<HomeTags>();
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
    /*
   加入测试Views
   * */
    private void loadTestDatas() {
        adLocalImages.add("http://www.6188.com/upload_6188s/flashAll/s800/20120517/1337218925uatdTW.jpg");
        adLocalImages.add("http://p2.pccoo.cn/bbs/20140411/201404111721506076.png");
        adLocalImages.add("http://img5.imgtn.bdimg.com/it/u=646049329,3325478164&fm=21&gp=0.jpg");
        adLocalImages.add("http://www.6188.com/upload_6188s/flashAll/s800/20120517/1337218925uatdTW.jpg");
        adLocalImages.add("http://www.6188.com/upload_6188s/flashAll/s800/20120517/1337218925uatdTW.jpg");
        adLocalImages.add("http://www.6188.com/upload_6188s/flashAll/s800/20120517/1337218925uatdTW.jpg");
        adLocalImages.add("http://www.6188.com/upload_6188s/flashAll/s800/20120517/1337218925uatdTW.jpg");
        //本地图片集合
        for (int position = 0; position < 7; position++) {
//            adLocalImages.add(Res.getResId("ic_test_" + position, R.drawable.class));

            adLocalDescribes.add("描述"+position);
        }
//        handler.sendEmptyMessageDelayed(LOADINGNOM_WHAT,3000);
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
