package com.movieapp.utils;

/**
 * Created by Javen on 2016/8/3.
 */
public class CommonUtils {
    public final static String APPID="5e29b6df6p1";
    public final static int TANK_TYPE_CHOICE=1;//精选
    public final static int TANK_TYPE_VIP=2;//VIP
    public final static  String BASE_URL="http://192.168.111.244:8080/Gather";
    /**
     * 绑定用户
     */
    public final static String GET_BUILD_USER_URL=BASE_URL+"/API/buildUser";
    public final static String GET_GET_USER_URL=BASE_URL+"/API/getUser";
    /**
     * 获取所有的分类
     */
    public final static String GET_CATEGORY_URL=BASE_URL+"/API/getType";
    /**
     * 获取分类中所有的影片
     */
    public final static String GET_CATEGORY_MOVIE_URL=BASE_URL+"/API/getTypeMovie";
    /**
     * 根据分类查询影片
     */
    public final static String GET_TYPE_MOVIE_URL=BASE_URL+"/API/getRankType";
    /**
     * 根据Id查询影片
     */
    public final static String GET_MOVIE_URL=BASE_URL+"/API/getByContent";
    /**
     * 首页查询
     */
    public final static String GET_MAIN_URL=BASE_URL+"/API/getMain";
    /**
     * 播放页推荐
     */
    public final static String GET_FOUR_URL=BASE_URL+"/API/getFour";

}
