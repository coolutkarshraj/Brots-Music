package com.brots.music.application.model.responseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LibraryInstanceSongDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SongName")
    @Expose
    private String songName;
    @SerializedName("ArtistId")
    @Expose
    private String artistId;
    @SerializedName("FullSongImageUrl")
    @Expose
    private String fullSongImageUrl;
    @SerializedName("ArtistName")
    @Expose
    private String artistName;
    @SerializedName("Duration")
    @Expose
    private String duration;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("AlbumName")
    @Expose
    private String albumName;
    @SerializedName("SongId")
    @Expose
    private Integer songId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("BannerImage")
    @Expose
    private String bannerImage;
    @SerializedName("Memory")
    @Expose
    private Integer memory;
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

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getFullSongImageUrl() {
        return fullSongImageUrl;
    }

    public void setFullSongImageUrl(String fullSongImageUrl) {
        this.fullSongImageUrl = fullSongImageUrl;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
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
