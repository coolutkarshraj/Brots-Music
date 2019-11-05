package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrrationResponseDataModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("RegistrrationDate")
    @Expose
    private String registrrationDate;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("IsTncAccepted")
    @Expose
    private String isTncAccepted;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("DeviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("friendCount")
    @Expose
    private String friendCount;
    @SerializedName("followingCount")
    @Expose
    private String followingCount;
    @SerializedName("SavedSongMemory")
    @Expose
    private String savedSongMemory;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("IsLoggedIn")
    @Expose
    private String isLoggedIn;
    @SerializedName("TotalLike")
    @Expose
    private Integer totalLike;
    @SerializedName("TotalDislike")
    @Expose
    private Integer totalDislike;
    @SerializedName("InstaMixMemory")
    @Expose
    private String instaMixMemory;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegistrrationDate() {
        return registrrationDate;
    }

    public void setRegistrrationDate(String registrrationDate) {
        this.registrrationDate = registrrationDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIsTncAccepted() {
        return isTncAccepted;
    }

    public void setIsTncAccepted(String isTncAccepted) {
        this.isTncAccepted = isTncAccepted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(String friendCount) {
        this.friendCount = friendCount;
    }

    public String getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(String followingCount) {
        this.followingCount = followingCount;
    }

    public String getSavedSongMemory() {
        return savedSongMemory;
    }

    public void setSavedSongMemory(String savedSongMemory) {
        this.savedSongMemory = savedSongMemory;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(String isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalDislike() {
        return totalDislike;
    }

    public void setTotalDislike(Integer totalDislike) {
        this.totalDislike = totalDislike;
    }

    public String getInstaMixMemory() {
        return instaMixMemory;
    }

    public void setInstaMixMemory(String instaMixMemory) {
        this.instaMixMemory = instaMixMemory;
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
