package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.OtpResponseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("date")
    @Expose
    private OtpResponseDataModel date;

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

    public OtpResponseDataModel getDate() {
        return date;
    }

    public void setDate(OtpResponseDataModel date) {
        this.date = date;
    }
}
