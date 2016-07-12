package com.movieapp.mian;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.movieapp.R;
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
    private RadioButton radio_channel, radio_message , radio_better , radio_setting ;
    //类型为Fragment的动态数组
    private ArrayList<Fragment> fragmentList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpage_main);
        //界面初始函数，用来获取定义的各控件对应的ID
        InitView();
        //ViewPager初始化函数
        InitViewPager();
    }
    private void InitView() {
        main_tab_RadioGroup = (RadioGroup) findViewById(R.id.main_tab_RadioGroup) ;

        radio_channel = (RadioButton) findViewById(R.id.radio_channel) ;
        radio_message = (RadioButton) findViewById(R.id.radio_message) ;
        radio_better = (RadioButton) findViewById(R.id.radio_better) ;
        radio_setting = (RadioButton) findViewById(R.id.radio_setting) ;

        main_tab_RadioGroup.setOnCheckedChangeListener(this);
    }
    private void InitViewPager() {
        main_viewPager = (ViewPager) findViewById(R.id.main_ViewPager);

        fragmentList = new ArrayList<Fragment>() ;

        Fragment oneFragment = new FragmentOne() ;
        Fragment twoFragment = new FragmentTwo();
        Fragment thirdFragment = new FragmentThree();
        Fragment fourFragment = new FragmentFour();

        //将各Fragment加入数组中
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(thirdFragment);
        fragmentList.add(fourFragment);

        //设置ViewPager的设配器
        main_viewPager.setAdapter(new MyAdapter(getSupportFragmentManager() , fragmentList));
        //当前为第一个页面
        main_viewPager.setCurrentItem(0);
        //ViewPager的页面改变监听器
        main_viewPager.setOnPageChangeListener(new MyViewPageListner());
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
            //获取当前页面用于改变对应RadioButton的状态
            int current = main_viewPager.getCurrentItem() ;
            switch(current){
                case 0:
                    main_tab_RadioGroup.check(R.id.radio_channel);
                    break;
                case 1:
                    main_tab_RadioGroup.check(R.id.radio_message);
                    break;
                case 2:
                    main_tab_RadioGroup.check(R.id.radio_better);
                    break;
                case 3:
                    main_tab_RadioGroup.check(R.id.radio_setting);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页
        int current=0;
        switch(checkedId){
            case R.id.radio_channel:
                current = 0 ;
                break ;
            case R.id.radio_message:
                current = 1 ;
                break;
            case R.id.radio_better:
                current = 2 ;
                break;
            case R.id.radio_setting:
                current = 3 ;
                break ;
        }
        if(main_viewPager.getCurrentItem() != current){
            main_viewPager.setCurrentItem(current);
        }
    }
}
