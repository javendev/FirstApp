package com.movieapp.bean;

/**
 * Created by Javen on 2016/7/25.
 */
public class MovieModel {
    private String videoId;
    private String name;
    private String url;
    private String imageUrl;
    private String desc;

    public MovieModel(String name, String url, String imageUrl, String desc) {
        this.name = name;
        this.url = url;
        this.imageUrl = imageUrl;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
