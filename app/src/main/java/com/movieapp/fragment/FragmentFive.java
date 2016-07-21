package com.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.movieapp.R;
import com.orhanobut.logger.Logger;

public class FragmentFive extends Fragment implements View.OnClickListener{

    TextView settingPersonText;
    RelativeLayout settingsPerson;
    RelativeLayout settingPhone;
    RelativeLayout settingsProtocol;
    RelativeLayout settingCleanCache;
    RelativeLayout settingAboutus;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.fragment_five, container, false);
        initView(view);
        return view;
    }

    private  void initView(View view){
        settingPersonText = (TextView) view.findViewById(R.id.id_settings_person_text);
        settingsPerson = (RelativeLayout) view.findViewById(R.id.id_settings_person);
        settingPhone = (RelativeLayout) view.findViewById(R.id.id_settings_phone);
        settingCleanCache = (RelativeLayout) view.findViewById(R.id.id_settings_cleanCache);
        settingsProtocol = (RelativeLayout) view.findViewById(R.id.id_settings_protocol);
        settingAboutus = (RelativeLayout) view.findViewById(R.id.id_settings_aboutus);
        settingsPerson.setOnClickListener(this);
        settingPhone.setOnClickListener(this);
        settingCleanCache.setOnClickListener(this);
        settingsProtocol.setOnClickListener(this);
        settingAboutus.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_settings_person:
                settingPersonText.setText("1234567890123456");
                break;
            case R.id.id_settings_phone:
                Logger.e("点击了..id_setting_phone");
                break;
            case R.id.id_settings_protocol:
                Logger.e("点击了..id_settings_protocol");
                break;
            case R.id.id_settings_cleanCache:
                Logger.e("点击了..id_setting_cleanCache");
                break;
            case R.id.id_settings_aboutus:
                Logger.e("点击了..id_setting_aboutus");
                break;
        }
    }
}
