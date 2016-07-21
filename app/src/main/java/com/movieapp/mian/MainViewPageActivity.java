package com.movieapp.mian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.movieapp.R;
import com.movieapp.fragment.FragmentFive;
import com.movieapp.fragment.FragmentFour;
import com.movieapp.fragment.FragmentOne;
import com.movieapp.fragment.FragmentThree;
import com.movieapp.fragment.FragmentTwo;

import java.util.ArrayList;

public class MainViewPageActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    //ViewPager控件
    private ViewPager main_viewPager ;
    //RadioGroup控件
    private RadioGroup main_tab_RadioGroup ;
    //RadioButton控件
    private RadioButton radio_home, radio_tags , radio_vip , radio_collect, radio_my;
    //类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList ;
    Fragment oneFragment,twoFragment,threeFragment,fourFragment,fiveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage_main);
        //界面初始函数，用来获取定义的各控件对应的ID
        initView();
        //ViewPager初始化函数
        initViewPager();
    }
    private void initView() {
        main_tab_RadioGroup = (RadioGroup) findViewById(R.id.main_tab_RadioGroup) ;

        radio_home = (RadioButton) findViewById(R.id.radio_home) ;
        radio_tags = (RadioButton) findViewById(R.id.radio_tags) ;
        radio_vip = (RadioButton) findViewById(R.id.radio_vip) ;
        radio_collect = (RadioButton) findViewById(R.id.radio_collect) ;
        radio_my = (RadioButton) findViewById(R.id.radio_my) ;

        main_tab_RadioGroup.setOnCheckedChangeListener(this);
    }
    private void initViewPager() {
        main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);

        fragmentList = new ArrayList<Fragment>() ;

        oneFragment = new FragmentOne();
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
        main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager() , fragmentList));
        //当前为第一个页面
        main_viewPager.setCurrentItem(0);
        //ViewPager的页面改变监听器
        main_viewPager.addOnPageChangeListener(new MyViewPageListner());
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(oneFragment != null && !oneFragment.isHidden())fragmentTransaction.hide(oneFragment);
        if(twoFragment != null && !twoFragment.isHidden())fragmentTransaction.hide(twoFragment);
        if(threeFragment != null && !threeFragment.isHidden())fragmentTransaction.hide(threeFragment);
        if(fourFragment != null  && !fourFragment.isHidden())fragmentTransaction.hide(fourFragment);
        if(fiveFragment != null  && !fiveFragment.isHidden())fragmentTransaction.hide(fiveFragment);
    }

    public class MyAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment> list ;
        public MyAdapter(FragmentManager fm , ArrayList<Fragment> list)
        {
            super(fm);
            this.list = list ;
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

    public class MyViewPageListner implements ViewPager.OnPageChangeListener{
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
            int current = main_viewPager.getCurrentItem() ;
            switch(current){
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
        int current=0;
        switch(checkedId){
            case R.id.radio_home:
                current = 0 ;
                break ;
            case R.id.radio_tags:
                current = 1 ;
                break;
            case R.id.radio_vip:
                current = 2 ;
                break;
            case R.id.radio_collect:
                current = 3 ;
                break ;
            case R.id.radio_my:
                current = 4 ;
                break ;
        }
        if(main_viewPager.getCurrentItem() != current){
            main_viewPager.setCurrentItem(current);
        }
    }
}
