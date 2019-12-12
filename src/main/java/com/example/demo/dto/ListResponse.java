package com.example.demo.dto;

import java.util.List;

public class ListResponse<T> {

    Integer totalPage;

    List<T> data;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
