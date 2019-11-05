package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeMemory {

    @SerializedName("instamixMemory")
    @Expose
    private Integer instamixMemory;

    public Integer getInstamixMemory() {
        return instamixMemory;
    }

    public void setInstamixMemory(Integer instamixMemory) {
        this.instamixMemory = instamixMemory;
    }

}
