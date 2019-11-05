package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.ContactFormResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactFormResponseModel {
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
    private ContactFormResponseData data;

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

    public ContactFormResponseData getData() {
        return data;
    }

    public void setData(ContactFormResponseData data) {
        this.data = data;
    }
}
