package com.brots.music.application.model.response;

import com.brots.music.application.model.responseData.UpdateTokenResponseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTokenResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("data")
    @Expose
    private UpdateTokenResponseData data;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UpdateTokenResponseData getData() {
        return data;
    }

    public void setData(UpdateTokenResponseData data) {
        this.data = data;
    }

}
