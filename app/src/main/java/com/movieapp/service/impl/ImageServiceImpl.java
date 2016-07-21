package com.movieapp.service.impl;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.movieapp.service.IImageService;

/**
 * Created by Javen on 2016/7/21.
 */
public class ImageServiceImpl implements IImageService{
    private static ImageServiceImpl imageServiceImpl;
    private Context mContext;
    private  ImageServiceImpl(Context context){
        mContext=context;
    }
    public static ImageServiceImpl getInstance(Context context){
        if (imageServiceImpl==null){
            synchronized (ImageServiceImpl.class){
                if (imageServiceImpl==null){
                    imageServiceImpl = new ImageServiceImpl(context);
                }
            }
        }
        return imageServiceImpl;
    }
    @Override
    public  void setImage(String data, ImageView imageView, int defaultImage) {
        Glide.with(mContext).load(data).fitCenter().placeholder(defaultImage).into(imageView);
    }
}
