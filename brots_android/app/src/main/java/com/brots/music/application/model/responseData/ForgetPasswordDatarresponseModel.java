package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPasswordDatarresponseModel {
    @SerializedName("passcode")
    @Expose
    private Integer passcode;

    public Integer getPasscode() {
        return passcode;
    }

    public void setPasscode(Integer passcode) {
        this.passcode = passcode;
    }
}
