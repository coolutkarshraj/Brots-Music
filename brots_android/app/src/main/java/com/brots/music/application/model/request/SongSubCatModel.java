package com.brots.music.application.model.request;

public class SongSubCatModel {

    String name;
    String id;

    public SongSubCatModel(String name, String id) {
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
