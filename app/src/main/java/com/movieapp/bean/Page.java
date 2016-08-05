package com.movieapp.bean;

import java.util.List;

/**
 * Created by Javen on 2016/8/5.
 */
public class Page {

    private int pageSize;
    private int pageNumber;
    private boolean firstPage;
    private boolean lastPage;
    private int totalRow;
    private int totalPage;

    private List<MovieModel> list;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public boolean isFirstPage() {
        return firstPage;
    }

    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isLastPage() {
        return lastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<MovieModel> getList() {
        return list;
    }

    public void setList(List<MovieModel> list) {
        this.list = list;
    }
}
