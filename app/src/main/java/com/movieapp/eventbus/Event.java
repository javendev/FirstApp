package com.movieapp.eventbus;

import com.movieapp.bean.CategoryModel;
import com.movieapp.bean.Page;
import com.movieapp.bean.UserModel;

import java.util.List;

/**
 * Created by Javen on 2016/7/29.
 */
public class Event {
    public static class buildUserEvent {
        private   UserModel userModel;
        public buildUserEvent(UserModel userModel) {
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
     * 根据分类查询所有的影片
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
}
