package com.movieapp.service;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Javen on 2016/7/28.
 */
public interface IUserService {
    public void getUserById(Context context,String  url,String userId);
    public void buildUser(Context context, String  url,HashMap<String,Object> params );
}
