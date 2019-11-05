package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.SendEmailDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendEmailResponseModel {
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
    private SendEmailDataModel data;

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

    public SendEmailDataModel getData() {
        return data;
    }

    public void setData(SendEmailDataModel data) {
        this.data = data;
    }
}
