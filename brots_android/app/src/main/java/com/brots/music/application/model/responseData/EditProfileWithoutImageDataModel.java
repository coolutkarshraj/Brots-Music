package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditProfileWithoutImageDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("MiddileName")
    @Expose
    private Object middileName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("LastLogin")
    @Expose
    private Object lastLogin;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("State")
    @Expose
    private Object state;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("DeviceToken")
    @Expose
    private String deviceToken;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("IsLoggedIn")
    @Expose
    private String isLoggedIn;
    @SerializedName("IsTncAccepted")
    @Expose
    private Integer isTncAccepted;
    @SerializedName("RegistrrationDate")
    @Expose
    private String registrrationDate;
    @SerializedName("DeviceType")
    @Expose
    private String deviceType;
    @SerializedName("OnlineStatus")
    @Expose
    private Object onlineStatus;
    @SerializedName("friendCount")
    @Expose
    private Integer friendCount;
    @SerializedName("followingCount")
    @Expose
    private Integer followingCount;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("About")
    @Expose
    private Object about;
    @SerializedName("SavedSongMemory")
    @Expose
    private Integer savedSongMemory;
    @SerializedName("InstaMixMemory")
    @Expose
    private Integer instaMixMemory;
    @SerializedName("TotalLike")
    @Expose
    private Object totalLike;
    @SerializedName("TotalDislike")
    @Expose
    private Object totalDislike;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

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

    public Object getMiddileName() {
        return middileName;
    }

    public void setMiddileName(Object middileName) {
        this.middileName = middileName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
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

    public Integer getIsTncAccepted() {
        return isTncAccepted;
    }

    public void setIsTncAccepted(Integer isTncAccepted) {
        this.isTncAccepted = isTncAccepted;
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

    public Object getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Object onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Integer getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(Integer friendCount) {
        this.friendCount = friendCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getAbout() {
        return about;
    }

    public void setAbout(Object about) {
        this.about = about;
    }

    public Integer getSavedSongMemory() {
        return savedSongMemory;
    }

    public void setSavedSongMemory(Integer savedSongMemory) {
        this.savedSongMemory = savedSongMemory;
    }

    public Integer getInstaMixMemory() {
        return instaMixMemory;
    }

    public void setInstaMixMemory(Integer instaMixMemory) {
        this.instaMixMemory = instaMixMemory;
    }

    public Object getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Object totalLike) {
        this.totalLike = totalLike;
    }

    public Object getTotalDislike() {
        return totalDislike;
    }

    public void setTotalDislike(Object totalDislike) {
        this.totalDislike = totalDislike;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
