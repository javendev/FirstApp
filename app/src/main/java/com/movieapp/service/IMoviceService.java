package com.movieapp.service;

import android.content.Context;

/**
 * Created by Javen on 2016/7/28.
 */
public interface IMoviceService {
    public void getUserById(Context context,String  url,String userId);
    public void buildUser(Context context, String  url);

    /**
     * 根据appId获取所有分类
     * @param context
     * @param appId
     */
    public void getAllCategory(Context context,String appId,boolean isRefresh);

    /**
     * 根据分类获取所有的影片
     * @param context
     * @param categoryId
     * @param pageNumber 当前页
     * @param pageSize 每页的记录数
     */
    public void getMoviesByCategoryId(Context context,String appId,int categoryId,int pageNumber,int pageSize,int type);

    /**
     *
     * @param context
     * @param appId
     * @param rankType
     * @param pageNumber
     * @param pageSize
     */
    public void getRankMovices(Context context,String appId,int rankType,int pageNumber ,int pageSize,int type);

    /**
     * 查询影片资源（id，名称，描述（模糊查询）等）
     * @param context
     * @param id
     * @param title
     * @param description
     */
    public void getMovice(Context context,int id,String title,String description);

    public void getMain(Context context);
    public void getFour(Context context,int videoId);
}
