package com.movieapp.service.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.movieapp.bean.UserModel;
import com.movieapp.eventbus.Event;
import com.movieapp.service.IUserService;
import com.movieapp.volley.VolleyUtils;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by Javen on 2016/7/28.
 */
public class UserServiceImpl implements IUserService{

    @Override
    public void getUserById(Context context, String url, String userId) {

    }

    @Override
    public void buildUser(final Context context, String url, HashMap<String, Object> params) {
        VolleyUtils.doPost(context, url, params, new VolleyUtils.volleyListener() {
            @Override
            public void onResponse(JSONObject response) {
                Logger.i("获取到的结果为："+response.toString());



                UserModel userModel = new Gson().fromJson(response.toString(), UserModel.class);
                EventBus.getDefault().post(new Event.buildUserEvent(userModel));
            }

            @Override
            public void onErrorResponse(String message) {
                Logger.e(message);
            }
        });
    }
}
