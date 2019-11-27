package com.brots.music.application;

import com.brots.music.application.model.response.ArtistSongResponseModel;
import com.brots.music.application.model.response.CategorySubCategoryResponseModel;
import com.google.gson.JsonObject;

public class Config {


    //public static String Baseurl = "ec2-54-184-212-143.us-west-2.compute.amazonaws.com";
    public static String Baseurl = "192.168.43.24";
    //public static String Base_url = "http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/";
    public static String Base_url = "http://192.168.43.24:9000/v1/";
    public static CategorySubCategoryResponseModel CatSubCat;
    public static ArtistSongResponseModel allArtistSong;
    public static String musiPlayerScreenType = "";
    public static int subcatPosition = 0;
    public static String exploreTabClicked = "0";
    public static int Clickedpostion ;
    public static String recyclerPoisitioninArtistListClicked = "";

    public static class Url {
        public final static String isUserExist = "user/isUserExist";
        public final static String isArtistExist = "artist/isUserExist";
        public final static String userlogin = "user/login";
        public final static String artistlogin = "artist/login";
        public final static String likeSong = "artistSong/Like";
        public final static String disLikeSong = "artistSong/disLike";
        public final static String updateUserDeviceToken = "user/updateDeviceToken";
        public final static String updateArtistDeviceToken = "artist/updateDeviceToken";
        public final static String getProfile = "user/getProfile";
        public final static String getCatSubcat = "allCategories/subcategories";
        public final static String getArtistSong = "artist/getAllSong";
        public final static String getInstanceSong="artistSong/geInstanceSong";
        public final static String getSavedSongs="artistSong/geSaveSongbyAlphabetically";
        public final static String getArtistOnBehalfLikeDislike="getAllArtist/onthebehalfof/like/dislike";
        public final static String sendEmailToEmailIfNotExist="sendMailToEmailIfNotExit";
        public final static String emailupdate="updateemail";
        public final static String editProfilewithoutPicture="user/updateProfile";
        public final static String saveSong="artistSong/Save";
        public final static String removeSong = "artistSong/remove";
        public final static String artistProfileWithTop3Song = "artist/profile/top3Song";
        public final static String artistGetProfile="artist/getProfile";
        public final static String getSingleArtistSongs = "artist/getSingleArtistAllSong";
        public final static String artistUpdate = "artist/updateProfile";
        public final static String artistAddNote = "artist/addNote";
        public final static String artistDeleteNote = "artist/deleteNote";
        public static String userchangePassword = "user/changepassword";
        public static String artistChangePassword = "artist/changepassword";
        public static String artistSendEmail = "artist/sendMailToEmailIfNotExit";
        public static String artistUpdateEmail = "artist/updateemail";
        public static String subMitForm = "contactAdminm";
        public static String isSongPlayed = "artist/IsSongHeared";
    }


}
