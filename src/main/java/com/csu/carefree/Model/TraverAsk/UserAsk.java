package com.csu.carefree.Model.TraverAsk;

import java.io.Serializable;

public class UserAsk implements Serializable {
    private String id;
    private String title;
    private String ask_content;
    private String star_num;
    private String add_time;
    private String city_id;
    private String user_id;

    @Override
    public String toString() {
        return "UserAsk{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ask_content='" + ask_content + '\'' +
                ", star_num='" + star_num + '\'' +
                ", user_id='" + user_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", city_id='" + city_id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAsk_content() {
        return ask_content;
    }

    public void setAsk_content(String ask_content) {
        this.ask_content = ask_content;
    }

    public String getStar_num() {
        return star_num;
    }

    public void setStar_num(String star_num) {
        this.star_num = star_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String scenic_id) {
        this.city_id = city_id;
    }

    public UserAsk() {
    }

    public UserAsk(String id,
                   String title,
                   String ask_content,
                   String star_num,
                   String add_time,
                   String user_id,
                   String city_id) {
        this.id = id;
        this.title = title;
        this.ask_content = ask_content;
        this.star_num = star_num;
        this.user_id = user_id;
        this.add_time = add_time;
        this.city_id = city_id;
    }
}
