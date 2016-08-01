package com.movieapp.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.movieapp.R;
import com.movieapp.eventbus.Event;
import com.movieapp.mian.XYActivity;
import com.movieapp.task.GetDiskCacheSizeTask;
import com.movieapp.utils.PhoneHelper;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class FragmentFive extends BaseFragment {
    @BindView(R.id.id_settings_person_text)
    TextView settingsPersonText;
    @BindView(R.id.id_settings_person)
    RelativeLayout settingsPerson;
    @BindView(R.id.id_settings_phone)
    RelativeLayout settingsPhone;
    @BindView(R.id.id_settings_protocol)
    RelativeLayout settingsProtocol;
    @BindView(R.id.id_settings_cacheSize)
    TextView settingsCacheSize;
    @BindView(R.id.id_settings_cleanCache)
    RelativeLayout settingsCleanCache;
    @BindView(R.id.id_settings_aboutus)
    RelativeLayout settingsAboutus;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        new GetDiskCacheSizeTask(settingsCacheSize).execute(new File(DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
    }


    @OnClick({R.id.id_settings_person, R.id.id_settings_phone, R.id.id_settings_protocol, R.id.id_settings_cleanCache, R.id.id_settings_aboutus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_settings_person:
                break;
            case R.id.id_settings_phone:
                new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText(getResources().getString(R.string.setting_phone))
                        .setContentText(getResources().getString(R.string.setting_email))
                        .show();
                break;
            case R.id.id_settings_protocol:
                startActivity(new Intent(mContext, XYActivity.class));
                break;
            case R.id.id_settings_cleanCache:

                break;
            case R.id.id_settings_aboutus:
                final Map<String, Object> appInfoMap = PhoneHelper.getAppInfoMap(getActivity().getApplication());
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText((String)appInfoMap.get("appName"))
                        .setContentText((String)appInfoMap.get("versionName"))
                        .setCustomImage((Drawable)appInfoMap.get("icon"))
                        .show();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void buildUserEvent(Event.buildUserEvent event) {
        Logger.i("FragmentFive 接收到的值："+event.userModel.getUserid());
        Toast.makeText(mContext, "F获取到信息了", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
