package com.brots.music.application.model;

public class Notification {

    String name;
    String message;
    String time;
    Integer img;

    public Notification(String name, String message, String time,Integer img) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.img=img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
