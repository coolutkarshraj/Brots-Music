package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveAdminUser {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("AdminSaveSongThrough")
    @Expose
    private SongSaveDataModel adminSaveSongThrough;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SongSaveDataModel getAdminSaveSongThrough() {
        return adminSaveSongThrough;
    }

    public void setAdminSaveSongThrough(SongSaveDataModel adminSaveSongThrough) {
        this.adminSaveSongThrough = adminSaveSongThrough;
    }


}
