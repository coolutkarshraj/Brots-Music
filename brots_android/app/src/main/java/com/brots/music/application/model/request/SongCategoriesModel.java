package com.brots.music.application.model.request;

public class SongCategoriesModel {

    String name;
    String id;

    public SongCategoriesModel(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getid() {
        return id;
    }
    public void setid(String id) {
        this.id = id;
    }
}
