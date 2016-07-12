package com.movieapp.mian;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.movieapp.R;
import com.movieapp.fragment.FragmentFour;
import com.movieapp.fragment.FragmentOne;
import com.movieapp.fragment.FragmentThree;
import com.movieapp.fragment.FragmentTwo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //UI Object
//    private TextView txt_topbar;
    private TextView txt_channel;
    private TextView txt_message;
    private TextView txt_better;
    private TextView txt_setting;
    private FrameLayout ly_content;

    //Fragment Object
    private FragmentOne fg1;
    private FragmentTwo fg2;
    private FragmentThree fg3;
    private FragmentFour fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        txt_channel.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
//        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_channel = (TextView) findViewById(R.id.txt_channel);
        txt_message = (TextView) findViewById(R.id.txt_message);
        txt_better = (TextView) findViewById(R.id.txt_better);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_channel.setOnClickListener(this);
        txt_message.setOnClickListener(this);
        txt_better.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_channel.setSelected(false);
        txt_message.setSelected(false);
        txt_better.setSelected(false);
        txt_setting.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        switch (v.getId()){
            case R.id.txt_channel:
                setSelected();
                txt_channel.setSelected(true);
                if(fg1 == null){
                    fg1 = new FragmentOne();
                }
                fTransaction.replace(R.id.ly_content,fg1);
                break;
            case R.id.txt_message:
                setSelected();
                txt_message.setSelected(true);
                if(fg2 == null){
                    fg2 =new FragmentTwo();
                }
                    fTransaction.replace(R.id.ly_content,fg2);
                break;
            case R.id.txt_better:
                setSelected();
                txt_better.setSelected(true);
                if(fg3 == null){
                    fg3 = new FragmentThree();
                }
                    fTransaction.replace(R.id.ly_content,fg3);

                break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
                if(fg4 == null){
                    fg4 =new FragmentFour();
                }
                    fTransaction.replace(R.id.ly_content,fg4);
                break;
        }
        fTransaction.commit();
    }
}
