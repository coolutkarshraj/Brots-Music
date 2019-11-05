package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongLikedDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("SongThrough")
    @Expose
    private SongLikeDislikeModel songThrough;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SongLikeDislikeModel getSongThrough() {
        return songThrough;
    }

    public void setSongThrough(SongLikeDislikeModel songThrough) {
        this.songThrough = songThrough;
    }
}
