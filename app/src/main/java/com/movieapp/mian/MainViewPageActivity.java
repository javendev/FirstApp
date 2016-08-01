package com.movieapp.mian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.movieapp.R;
import com.movieapp.eventbus.Event;
import com.movieapp.fragment.FragmentFive;
import com.movieapp.fragment.FragmentFour;
import com.movieapp.fragment.FragmentOne3;
import com.movieapp.fragment.FragmentThree;
import com.movieapp.fragment.FragmentTwo;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewPageActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_ViewPager)
    ViewPager mainViewPager;
    @BindView(R.id.radio_home)
    RadioButton radioHome;
    @BindView(R.id.radio_tags)
    RadioButton radioTags;
    @BindView(R.id.radio_vip)
    RadioButton radioVip;
    @BindView(R.id.radio_collect)
    RadioButton radioCollect;
    @BindView(R.id.radio_my)
    RadioButton radioMy;
    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup main_tab_RadioGroup;

    //类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList;
    Fragment oneFragment, twoFragment, threeFragment, fourFragment, fiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage_main);
        ButterKnife.bind(this);
        //界面初始函数，用来获取定义的各控件对应的ID
        initView();
        //ViewPager初始化函数
        initViewPager();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setLogo(R.drawable.head_logo);
        mToolbar.setTitle(null);
        mToolbar.setSubtitle(null);
        setSupportActionBar(mToolbar);

        main_tab_RadioGroup.setOnCheckedChangeListener(this);
    }

    private void initViewPager() {
        fragmentList = new ArrayList<Fragment>();

        oneFragment = new FragmentOne3();
        twoFragment = new FragmentTwo();
        threeFragment = new FragmentThree();
        fourFragment = new FragmentFour();
        fiveFragment = new FragmentFive();

        //将各Fragment加入数组中
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);
        fragmentList.add(fiveFragment);

        //设置ViewPager的设配器
        mainViewPager.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList));
        //当前为第一个页面
        mainViewPager.setCurrentItem(0);
        //ViewPager的页面改变监听器
        mainViewPager.addOnPageChangeListener(new MyViewPageListner());
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (oneFragment != null && !oneFragment.isHidden()) fragmentTransaction.hide(oneFragment);
        if (twoFragment != null && !twoFragment.isHidden()) fragmentTransaction.hide(twoFragment);
        if (threeFragment != null && !threeFragment.isHidden())
            fragmentTransaction.hide(threeFragment);
        if (fourFragment != null && !fourFragment.isHidden())
            fragmentTransaction.hide(fourFragment);
        if (fiveFragment != null && !fiveFragment.isHidden())
            fragmentTransaction.hide(fiveFragment);
    }

    public class MyAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    public class MyViewPageListner implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
            hideAllFragment(fTransaction);
            //获取当前页面用于改变对应RadioButton的状态
            int current = mainViewPager.getCurrentItem();
            switch (current) {
                case 0:
                    main_tab_RadioGroup.check(R.id.radio_home);
                    if (oneFragment.isHidden())
                        fTransaction.show(oneFragment);
                    break;
                case 1:
                    main_tab_RadioGroup.check(R.id.radio_tags);
                    if (twoFragment.isHidden())
                        fTransaction.show(twoFragment);
                    break;
                case 2:
                    main_tab_RadioGroup.check(R.id.radio_vip);
                    if (threeFragment.isHidden())
                        fTransaction.show(threeFragment);
                    break;
                case 3:
                    main_tab_RadioGroup.check(R.id.radio_collect);
                    if (fourFragment.isHidden())
                        fTransaction.show(fourFragment);
                    break;
                case 4:
                    main_tab_RadioGroup.check(R.id.radio_my);
                    if (fiveFragment.isHidden())
                        fTransaction.show(fiveFragment);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current = 0;
        switch (checkedId) {
            case R.id.radio_home:
                current = 0;
                break;
            case R.id.radio_tags:
                current = 1;
                break;
            case R.id.radio_vip:
                current = 2;
                break;
            case R.id.radio_collect:
                current = 3;
                break;
            case R.id.radio_my:
                current = 4;
                break;
        }
        if (mainViewPager.getCurrentItem() != current) {
            mainViewPager.setCurrentItem(current);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void buildUserEvent(Event.buildUserEvent event) {
        Logger.i("Activity接收的值："+event.userModel.getUserid());
        Toast.makeText(this, "获取到信息了", Toast.LENGTH_SHORT).show();
    }


}
