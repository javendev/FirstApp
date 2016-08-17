package com.movieapp.eventbus;

import com.movieapp.bean.CategoryModel;
import com.movieapp.bean.HomePage;
import com.movieapp.bean.MovieModel;
import com.movieapp.bean.Page;
import com.movieapp.bean.UserModel;

import java.util.List;

/**
 * Created by Javen on 2016/7/29.
 */
public class Event {
    public static class getUserEvent {
        private   UserModel userModel;
        public getUserEvent(UserModel userModel) {
            this.userModel=userModel;
        }
        public UserModel getUserModel() {
            return userModel;
        }
    }
    //获取所有的分类
    public static class getCategoryEvent{
        private List<CategoryModel> categoryModels;
        private boolean isRefresh;
        public getCategoryEvent(List<CategoryModel> categoryModels,boolean isRefresh) {
            this.categoryModels=categoryModels;
            this.isRefresh = isRefresh;
        }

        public List<CategoryModel> getCategoryModels() {
            return categoryModels;
        }

        public boolean isRefresh() {
            return isRefresh;
        }
    }

    /**
     * 根据分类查询影片
     */
    public static class getMoviceByCategoryId{
        private Page page;
        private int type;

        public  getMoviceByCategoryId(Page page, int type){
            this.page=page;
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public Page getPage() {
            return page;
        }
    }

    /**
     * 查询精选中的影片
     */
    public static class getRankMovicesChoice{
        private Page page;
        private int type;

        public  getRankMovicesChoice(Page page, int type){
            this.page=page;
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public Page getPage() {
            return page;
        }
    }
    /**
     * 查询VIP中的影片
     */
    public static class getRankMovicesVip{
        private Page page;
        private int type;

        public  getRankMovicesVip(Page page, int type){
            this.page=page;
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public Page getPage() {
            return page;
        }
    }

    /**
     * 查询主页数据
     */
    public static class getMainPage{
        private HomePage homeTags;
        public  getMainPage(HomePage homeTags){
            this.homeTags = homeTags;
        }

        public HomePage getHomeTags() {
            return homeTags;
        }
    }

    /**
     * 播放页推荐
     */
    public static class getFour{
        private List<MovieModel> fours;
        public getFour(List<MovieModel> fours){
            this.fours = fours;
        }
        public List<MovieModel> getFours() {
            return fours;
        }

    }

}
