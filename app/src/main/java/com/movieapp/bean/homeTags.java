package com.movieapp.bean;

import java.util.List;

/**
 * Created by Javen on 2016/7/25.
 */
public class HomeTags {
    private boolean isTag;
    private String tagName;
    private int categoryId;
    private List<MovieModel> tagList;

    public boolean isTag() {
        return isTag;
    }

    public void setTag(boolean tag) {
        isTag = tag;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<MovieModel> getTagList() {
        return tagList;
    }

    public void setTagList(List<MovieModel> tagList) {
        this.tagList = tagList;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
