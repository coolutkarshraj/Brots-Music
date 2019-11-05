package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongHearedByUser {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("songHearthrough")
    @Expose
    private SongHearthrough songHearthrough;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SongHearthrough getSongHearthrough() {
        return songHearthrough;
    }

    public void setSongHearthrough(SongHearthrough songHearthrough) {
        this.songHearthrough = songHearthrough;
    }

}
