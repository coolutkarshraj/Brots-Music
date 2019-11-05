package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.ForgetPasswordDatarresponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgetPasswordResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private ForgetPasswordDatarresponseModel data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public ForgetPasswordDatarresponseModel getData() {
        return data;
    }

    public void setData(ForgetPasswordDatarresponseModel data) {
        this.data = data;
    }

}
