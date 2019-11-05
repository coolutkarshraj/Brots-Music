package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.AtristProfileDataModel;
import com.brots.music.application.model.responseData.Top3SongDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AtristProfileWithTop3SongsResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("Song")
    @Expose
    private List<Top3SongDataModel> song = null;
    @SerializedName("Profile")
    @Expose
    private AtristProfileDataModel profile;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Top3SongDataModel> getSong() {
        return song;
    }

    public void setSong(List<Top3SongDataModel> song) {
        this.song = song;
    }

    public AtristProfileDataModel getProfile() {
        return profile;
    }

    public void setProfile(AtristProfileDataModel profile) {
        this.profile = profile;
    }
}
