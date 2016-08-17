package com.movieapp.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movieapp.R;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.mian.UserActivity;
import com.movieapp.mian.XYActivity;
import com.movieapp.service.IMoviceService;
import com.movieapp.service.impl.MoviceServiceImpl;
import com.movieapp.utils.CommonUtils;
import com.movieapp.utils.PhoneHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentFive extends BaseFragment {


    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.rl_xy)
    RelativeLayout rlXy;
//    @BindView(R.id.rl_hc)
//    RelativeLayout rlHc;
    @BindView(R.id.rl_us)
    RelativeLayout rlUs;

    IMoviceService moviceService;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        if(moviceService== null){
            moviceService = new MoviceServiceImpl(mContext);
        }
//        new GetDiskCacheSizeTask(tvUser).execute(new File(getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
    }

    @Override
    public void loadData() {
        moviceService.buildUser(mContext, CommonUtils.GET_BUILD_USER_URL);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUserEvent(Event.getUserEvent event) {
        UserModel userModel = event.getUserModel();
        tvUser.setText(userModel.getUserid());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.rl_user, R.id.rl_phone, R.id.rl_xy, R.id.rl_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_user:
                String userId = tvUser.getText().toString();
                if (userId!=null) {
                    Intent intent = new Intent(mContext, UserActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
                break;
            case R.id.rl_phone:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText(getResources().getString(R.string.setting_phone))
                        .setContentText(getResources().getString(R.string.setting_email))
                        .show();
                break;
            case R.id.rl_xy:
                startActivity(new Intent(mContext, XYActivity.class));
                break;

            case R.id.rl_us:
                final Map<String, Object> appInfoMap = PhoneHelper.getAppInfoMap(getActivity().getApplication());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText((String) appInfoMap.get("appName"))
                        .setContentText((String) appInfoMap.get("versionName"))
                        .setCustomImage((Drawable) appInfoMap.get("icon"))
                        .show();
                break;
        }
    }
}
