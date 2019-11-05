package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.LikeMemory;
import com.brots.music.application.model.responseData.LikeSongDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeSongResponseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private LikeSongDataModel data;
    @SerializedName("memory")
    @Expose
    private LikeMemory memory;

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

    public LikeSongDataModel getData() {
        return data;
    }

    public void setData(LikeSongDataModel data) {
        this.data = data;
    }

    public LikeMemory getMemory() {
        return memory;
    }

    public void setMemory(LikeMemory memory) {
        this.memory = memory;
    }

}
