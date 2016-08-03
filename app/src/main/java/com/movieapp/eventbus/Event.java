package com.movieapp.eventbus;

import com.movieapp.bean.UserModel;

/**
 * Created by Javen on 2016/7/29.
 */
public class Event {
    public static class buildUserEvent {
        public  UserModel userModel;
        public buildUserEvent(UserModel userModel) {
            this.userModel=userModel;
        }
    }
}
