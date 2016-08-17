package com.movieapp.mian;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movieapp.R;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.utils.CommonUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends Activity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_vp)
    TextView tvVp;
    @BindView(R.id.user_backLayout)
    RelativeLayout userBackLayout;
    private String userId;
    IMoviceService moviceService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getString("userId");
            if (userId != null) {
                EventBus.getDefault().register(this);
                moviceService = new MoviceServiceImpl(this);
                moviceService.buildUser(this, CommonUtils.GET_BUILD_USER_URL);
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserEvent(Event.getUserEvent event) {
        UserModel userModel = event.getUserModel();
        Logger.e("获取到的user："+userModel.toString());
        tvName.setText(userModel.getUserid());
        int paystatus = Integer.valueOf(userModel.getPaystatus());
        if (paystatus == 1) {
            tvVp.setText("付费会员");
        } else {
            tvVp.setText("未付费观众");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }



    @OnClick(R.id.user_backLayout)
    public void onClick() {
        finish();
    }
}
