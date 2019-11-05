package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactFormResponseData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("Body")
    @Expose
    private String body;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
