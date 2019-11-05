package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsSongPlayerDataModel {
    @SerializedName("IsSongHeared")
    @Expose
    private String isSongHeared;
    @SerializedName("songId")
    @Expose
    private String songId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public String getIsSongHeared() {
        return isSongHeared;
    }

    public void setIsSongHeared(String isSongHeared) {
        this.isSongHeared = isSongHeared;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
