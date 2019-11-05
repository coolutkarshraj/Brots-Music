package com.brots.music.application.model;

public class ArtistSave {
    String song ;
    String url;
    String artist;
    String coverimage;

    public ArtistSave(String song, String url, String artist, String coverimage) {
        this.song = song;
        this.url = url;
        this.artist = artist;
        this.coverimage = coverimage;
    }

    public ArtistSave()
    {

    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
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

}
