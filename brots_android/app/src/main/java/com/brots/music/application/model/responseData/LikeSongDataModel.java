package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeSongDataModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Like")
    @Expose
    private Integer like;
    @SerializedName("DisLike")
    @Expose
    private Integer disLike;
    @SerializedName("Memory")
    @Expose
    private Integer memory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public void setDisLike(Integer disLike) {
        this.disLike = disLike;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }
}
