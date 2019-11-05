package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.DisLikeArtistMost;
import com.brots.music.application.model.responseData.LikedArtistMost;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetArtistOnBehalfLikeDisLikeResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("LikedArtistMost")
    @Expose
    private List<LikedArtistMost> likedArtistMost = null;
    @SerializedName("disLikeArtistMost")
    @Expose
    private List<DisLikeArtistMost> disLikeArtistMost = null;

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

    public List<LikedArtistMost> getLikedArtistMost() {
        return likedArtistMost;
    }

    public void setLikedArtistMost(List<LikedArtistMost> likedArtistMost) {
        this.likedArtistMost = likedArtistMost;
    }

    public List<DisLikeArtistMost> getDisLikeArtistMost() {
        return disLikeArtistMost;
    }

    public void setDisLikeArtistMost(List<DisLikeArtistMost> disLikeArtistMost) {
        this.disLikeArtistMost = disLikeArtistMost;
    }
}
