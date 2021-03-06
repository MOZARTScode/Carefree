package com.csu.carefree.Model.TraverMsg;

import java.io.Serializable;

public class ProvinceMsg implements Serializable {
    private String id;
    private String name;
    private String img_url;


    @Override
    public String toString() {
        return "ProvinceMsg{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public ProvinceMsg() {
    }

    public ProvinceMsg(String id, String name, String img_url) {
        this.id = id;
        this.name = name;
        this.img_url = img_url;
    }
}
