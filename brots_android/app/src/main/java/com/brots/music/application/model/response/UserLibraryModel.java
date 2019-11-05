package com.brots.music.application.model.response;

public class UserLibraryModel {
    String song ;
    String Fullurl ;
    String ArtistId ;
    String totallike ;
    String totaldislike ;
    String id ;
    String url;
    String artist;
    String coverimage;
    String Memory;

    String LikeSong;
    String PointSong;
    String DislikeSong;
    String Issaved;
    String Songid;
    String Duration;
    String AlbumName;


    public UserLibraryModel(){}

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }


    public String getArtistId() {
        return ArtistId;
    }

    public void setArtistId(String ArtistId) {
        this.ArtistId = ArtistId;
    }

    public String getFullurl() {
        return Fullurl;
    }

    public void setFullurl(String Fullurl) {
        this.Fullurl = Fullurl;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String Memory) {
        this.Memory = Memory;
    }


    public String getDuration() {
        return Duration;
    }

    public void setDuration(String Duration) {
        this.Duration = Duration;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String AlbumName) {
        this.AlbumName = AlbumName;
    }

    public String getid() {
        return id;
    }public void setid(String id) {
        this.id = id;
    }

    public String gettotallike() {
        return totallike;
    }public void settotallike(String totallike) {
        this.totallike = totallike;
    }
    public String gettotaldislike() {
        return totaldislike;
    }public void settotaldislike(String totaldislike) {
        this.totaldislike = totaldislike;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getLikeSong() {
        return LikeSong;
    }

    public void setLikeSong(String likeSong) {
        LikeSong = likeSong;
    }

    public String getPointSong() {
        return PointSong;
    }

    public void setPointSong(String pointSong) {
        PointSong = pointSong;
    }

    public String getDislikeSong() {
        return DislikeSong;
    }

    public void setDislikeSong(String dislikesong) {
        DislikeSong = dislikesong;
    }


    public String getIssavedSong() {
        return Issaved;
    }

    public void setIssavedSong(String issaved) {
        Issaved = issaved;
    }

    public String getSetSongid() {
        return Songid;
    }

    public void setSongid(String setSongid) {
        Songid = setSongid;
    }
}
