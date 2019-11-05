package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Top3SongDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SongName")
    @Expose
    private String songName;
    @SerializedName("ArtistName")
    @Expose
    private String artistName;
    @SerializedName("ArtistId")
    @Expose
    private String artistId;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("FullSongUrl")
    @Expose
    private String fullSongUrl;
    @SerializedName("AlbumName")
    @Expose
    private String albumName;
    @SerializedName("Like")
    @Expose
    private Integer like;
    @SerializedName("DisLike")
    @Expose
    private Integer disLike;
    @SerializedName("CoverImage")
    @Expose
    private String coverImage;
    @SerializedName("Memory")
    @Expose
    private Integer memory;
    @SerializedName("IsTrending")
    @Expose
    private String isTrending;
    @SerializedName("IsDispopular")
    @Expose
    private String isDispopular;
    @SerializedName("TrendingDate")
    @Expose
    private String trendingDate;
    @SerializedName("SongHearCount")
    @Expose
    private Object songHearCount;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("subcategoryId")
    @Expose
    private Integer subcategoryId;

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

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullSongUrl() {
        return fullSongUrl;
    }

    public void setFullSongUrl(String fullSongUrl) {
        this.fullSongUrl = fullSongUrl;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDisLike() {
        return disLike;
    }

    public void setDisLike(Integer disLike) {
        this.disLike = disLike;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getIsTrending() {
        return isTrending;
    }

    public void setIsTrending(String isTrending) {
        this.isTrending = isTrending;
    }

    public String getIsDispopular() {
        return isDispopular;
    }

    public void setIsDispopular(String isDispopular) {
        this.isDispopular = isDispopular;
    }

    public String getTrendingDate() {
        return trendingDate;
    }

    public void setTrendingDate(String trendingDate) {
        this.trendingDate = trendingDate;
    }

    public Object getSongHearCount() {
        return songHearCount;
    }

    public void setSongHearCount(Object songHearCount) {
        this.songHearCount = songHearCount;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
}
