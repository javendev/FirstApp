package com.movieapp.bean;

import java.util.List;

/**
 * Created by Javen on 2016/7/25.
 */
public class HomePage {
    //广告栏
    private List<MovieModel> top;

    private List<Title> title;

    public List<MovieModel> getTop() {
        return top;
    }

    public void setTop(List<MovieModel> top) {
        this.top = top;
    }

    public List<Title> getTitle() {
        return title;
    }

    public void setTitle(List<Title> title) {
        this.title = title;
    }
    public class Title {
        //推荐
        private List<MovieModel> content;
        CategoryModel category;

        public List<MovieModel> getContent() {
            return content;
        }

        public void setContent(List<MovieModel> content) {
            this.content = content;
        }

        public CategoryModel getCategory() {
            return category;
        }

        public void setCategory(CategoryModel category) {
            this.category = category;
        }
    }


}


