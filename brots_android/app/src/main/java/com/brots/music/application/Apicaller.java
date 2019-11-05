package com.brots.music.application;

import android.app.Activity;

import com.brots.music.application.activity.loginRegistraitionFlow.Otp_Registeration;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.AddNoteResponseModel;
import com.brots.music.application.model.response.ArtistEditProfileResponseModel;
import com.brots.music.application.model.response.ArtistEditProfileWithImageResponseModel;
import com.brots.music.application.model.response.ArtistGetProfileResponseModel;
import com.brots.music.application.model.response.ArtistGetSongResponseModel;
import com.brots.music.application.model.response.ArtistSendEmailResponseModel;
import com.brots.music.application.model.response.ArtistSongResponseModel;
import com.brots.music.application.model.response.ArtistUpdateEmailResponseModel;
import com.brots.music.application.model.response.AtristProfileWithTop3SongsResponseModel;
import com.brots.music.application.model.response.CategorySubCategoryResponseModel;
import com.brots.music.application.model.response.ChangePasswordResponseModel;
import com.brots.music.application.model.response.ContactFormResponseModel;
import com.brots.music.application.model.response.DeleteNoteResponseModel;
import com.brots.music.application.model.response.EditProfileWithoutImageResponseModel;
import com.brots.music.application.model.response.ForgetPasswordResponseModel;
import com.brots.music.application.model.response.GetArtistOnBehalfLikeDisLikeResponseModel;
import com.brots.music.application.model.response.GetUserProfileResponseModel;
import com.brots.music.application.model.response.IsSongPlayedModel;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.model.response.LibraryInstanceSongResponseModel;
import com.brots.music.application.model.response.LibrarySongsResponseModel;
import com.brots.music.application.model.response.LikeSongResponseModel;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.model.response.OtpResponseModel;
import com.brots.music.application.model.response.RemoveSongResponseModel;
import com.brots.music.application.model.response.RregistrationResponseModel;
import com.brots.music.application.model.response.SaveSongResponseModel;
import com.brots.music.application.model.response.SendEmailResponseModel;
import com.brots.music.application.model.response.UpdateEmailIdResponseModel;
import com.brots.music.application.model.response.UpdatePasswordRresponseModel;
import com.brots.music.application.model.response.UpdateTokenResponse;
import com.brots.music.application.apiInterface.UrlLocator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;

public class Apicaller {

    public static void isUserExistOrNot(Activity activity, String url,
                                        final FutureCallback<IsUserExistResponseModel> apiCallBack) {
        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        String email = obj.getData().getEmail();
        String userType = obj.getData().getUserType();
        JsonObject json = new JsonObject();
        json.addProperty("userType", userType);
        json.addProperty("email", email);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        IsUserExistResponseModel isUserExistResponseModel = gson.fromJson(result, IsUserExistResponseModel.class);
                        apiCallBack.onCompleted(e, isUserExistResponseModel);
                    }
                });
    }

    public static void sendRegistrationOtp(Activity activity, String url,String name,String otp,String email,
                                        final FutureCallback<OtpResponseModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("otp", otp);
        json.addProperty("email", email);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(url)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OtpResponseModel otpResponseModel = gson.fromJson(result, OtpResponseModel.class);
                        apiCallBack.onCompleted(e, otpResponseModel);
                    }
                });
    }
    public static void sendForgetPasswordOtp(Activity activity, String url,String email,String usertype,
                                           final FutureCallback<ForgetPasswordResponseModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("userType", usertype);
        json.addProperty("email", email);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(url)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ForgetPasswordResponseModel forgetPasswordResponseModel = gson.fromJson(result, ForgetPasswordResponseModel.class);
                        apiCallBack.onCompleted(e, forgetPasswordResponseModel);
                    }
                });
    }

    public static void rregister(Activity activity, String url, JsonObject json,
                                             final FutureCallback<RregistrationResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(url)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        RregistrationResponseModel rregistrationResponseModel = gson.fromJson(result, RregistrationResponseModel.class);
                        apiCallBack.onCompleted(e, rregistrationResponseModel);
                    }
                });
    }


    public static void updatePassword(Activity activity, String url, JsonObject json,
                                 final FutureCallback<UpdatePasswordRresponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(url)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdatePasswordRresponseModel updatePasswordRresponseModel = gson.fromJson(result, UpdatePasswordRresponseModel.class);
                        apiCallBack.onCompleted(e, updatePasswordRresponseModel);
                    }
                });
    }


    public static void doLogin(Activity activity, String url, String email, String password,
                               final FutureCallback<LoginResponseModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LoginResponseModel loginResponseModel = gson.fromJson(result, LoginResponseModel.class);
                        apiCallBack.onCompleted(e, loginResponseModel);
                    }
                });
    }

    public static void updateDeviceToken(Activity activity, int id, String deviceToken, String url,
                                         final FutureCallback<UpdateTokenResponse> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("deviceToken", deviceToken);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdateTokenResponse updateTokenResponse = gson.fromJson(result, UpdateTokenResponse.class);
                        apiCallBack.onCompleted(e, updateTokenResponse);
                    }
                });

    }
    public static void updateDeviceTokenAfterRegistration(Activity activity, int id, String deviceToken, String url,
                                         final FutureCallback<UpdateTokenResponse> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.addProperty("deviceToken", deviceToken);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(url)
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdateTokenResponse updateTokenResponse = gson.fromJson(result, UpdateTokenResponse.class);
                        apiCallBack.onCompleted(e, updateTokenResponse);
                    }
                });

    }

    public static void getCatSubCat(Activity activity, String url,
                                    final FutureCallback<CategorySubCategoryResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        CategorySubCategoryResponseModel categorySubCategoryResponseModel = gson.fromJson(result, CategorySubCategoryResponseModel.class);
                        apiCallBack.onCompleted(e, categorySubCategoryResponseModel);
                    }
                });

    }


    public static void getAllArtistSong(Activity activity, String url, String catId, String subCatId, String id,
                                        final FutureCallback<ArtistSongResponseModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("categoryId", catId);
        json.addProperty("subcategoryId", subCatId);
        json.addProperty("userId", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistSongResponseModel updateTokenResponse = gson.fromJson(result, ArtistSongResponseModel.class);
                        Config.allArtistSong = updateTokenResponse;
                        apiCallBack.onCompleted(e, updateTokenResponse);
                    }
                });

    }

    public static void saveSong(Activity activity, String url, int totalSavedMemory, String songId, String songName, String songDuration,
                                String artistId, String fullSongurl, String songImageUrl, String SavedSongMemory, int userId,
                                String bannerImage, String artistName, String albumname, String singleSaveMemory,
                                final FutureCallback<SaveSongResponseModel> apiCallBack) {

        JsonObject json = new JsonObject();
        json.addProperty("userId", userId);
        json.addProperty("songId", songId);
        json.addProperty("song_name", songName);
        json.addProperty("songduration", songDuration);
        json.addProperty("artist_id", artistId);
        json.addProperty("full_song_url", fullSongurl);
        json.addProperty("song_img_url", songImageUrl);
        json.addProperty("singleSaveMemory", singleSaveMemory);
        json.addProperty("savedSongMemory", SavedSongMemory);
        json.addProperty("bannerImage", bannerImage);
        json.addProperty("artist_name", artistName);
        json.addProperty("album_name", albumname);
        json.addProperty("isSaved", "1");
        json.addProperty("totalSavedMemory", totalSavedMemory);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        SaveSongResponseModel saveSongResponseModel = gson.fromJson(result, SaveSongResponseModel.class);
                        apiCallBack.onCompleted(e, saveSongResponseModel);
                    }
                });


    }

    public static void libraryInstanceSong(Activity activity, String url, String id,
                                           final FutureCallback<LibraryInstanceSongResponseModel> apiCallBack) {
        JsonObject json = new JsonObject();
        json.addProperty("userId", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LibraryInstanceSongResponseModel updateTokenResponse = gson.fromJson(result, LibraryInstanceSongResponseModel.class);
                        apiCallBack.onCompleted(e, updateTokenResponse);
                    }
                });

    }

    public static void librarySavedSongs(Activity activity, String url, String id,
                                         final FutureCallback<LibrarySongsResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userId", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LibrarySongsResponseModel librarySongsResponseModel = gson.fromJson(result, LibrarySongsResponseModel.class);
                        apiCallBack.onCompleted(e, librarySongsResponseModel);
                    }
                });
    }

    public static void userGetProfile(Activity activity, String url, String id, String userType,
                                      final FutureCallback<GetUserProfileResponseModel> apiCalkBack) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("userType", userType);
            final Gson gson = new Gson();
            Ion.with(activity)
                    .load(UrlLocator.getFinalUrl(url))
                    .noCache().setJsonObjectBody(jsonObject)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            GetUserProfileResponseModel getUserProfileResponseModel = gson.fromJson(result, GetUserProfileResponseModel.class);
                            apiCalkBack.onCompleted(e, getUserProfileResponseModel);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void artistGetBehalfofDislikeLike(Activity activity, String url,
                                                    final FutureCallback<GetArtistOnBehalfLikeDisLikeResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userType", "2");
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        GetArtistOnBehalfLikeDisLikeResponseModel getArtistOnBehalfLikeDisLikeResponseModel = gson.fromJson(result, GetArtistOnBehalfLikeDisLikeResponseModel.class);
                        apiCallBack.onCompleted(e, getArtistOnBehalfLikeDisLikeResponseModel);
                    }
                });

    }

    public static void sendMail(Activity activity, String url, String email, String name, String userType, String id,
                                final FutureCallback<SendEmailResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("id", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        SendEmailResponseModel sendEmailResponseModel = gson.fromJson(result, SendEmailResponseModel.class);
                        apiCallBack.onCompleted(e, sendEmailResponseModel);
                    }
                });
    }

    public static void emailUpdate(Activity activity, String url, String email, String id, String userType, String password,
                                   final FutureCallback<UpdateEmailIdResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("password", password);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        UpdateEmailIdResponseModel updateEmailIdResponseModel = gson.fromJson(result, UpdateEmailIdResponseModel.class);
                        apiCallBack.onCompleted(e, updateEmailIdResponseModel);
                    }
                });
    }

    public static void editProfileWithoutImage(Activity activity, String url, String id, String firstname, String lastname, String name
            , String phone, String about, String city, String country, String dob, String gender, String userType,
                                               String image, final FutureCallback<EditProfileWithoutImageResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("firstName", firstname);
        jsonObject.addProperty("lastname", lastname);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("address", about);
        jsonObject.addProperty("city", city);
        jsonObject.addProperty("country", country);
        jsonObject.addProperty("dob", dob);
        jsonObject.addProperty("gender", gender);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("image", "");

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        EditProfileWithoutImageResponseModel editprofile = gson.fromJson(result, EditProfileWithoutImageResponseModel.class);
                        apiCallBack.onCompleted(e, editprofile);
                    }
                });
    }

    public static void editProfileWithImage(Activity activity, String url, String id, String firstname, String lastname, String name
            , String phone, String address, String city, String country, String dob, String gender, String userType,
                                            String image, final FutureCallback<EditProfileWithoutImageResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setMultipartParameter("id", id)
                .setMultipartParameter("firstName", firstname)
                .setMultipartParameter("lastname", lastname)
                .setMultipartParameter("name", name)
                .setMultipartParameter("phone", phone)
                .setMultipartParameter("address", "xx")
                .setMultipartParameter("city", city)
                .setMultipartParameter("country", country)
                .setMultipartParameter("dob", dob)
                .setMultipartParameter("gender", gender)
                .setMultipartParameter("userType", userType)
                .setMultipartFile("image", "image/jpeg", new File(image))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        EditProfileWithoutImageResponseModel editprofile = gson.fromJson(result, EditProfileWithoutImageResponseModel.class);
                        apiCallBack.onCompleted(e, editprofile);
                    }
                });
    }

    public static void likeSong(Activity activity, String songId, String like, String disLike, String url,
                                final FutureCallback<LikeSongResponseModel> apiCallBack) {
        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        int userId = obj.getData().getId();
        JsonObject json = new JsonObject();
        json.addProperty("like", "1");
        json.addProperty("disLike", "0");
        json.addProperty("userId", userId);
        json.addProperty("songId", songId);
        json.addProperty("likes", like);
        json.addProperty("disLikes", disLike);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LikeSongResponseModel likeSongResponseModel = gson.fromJson(result, LikeSongResponseModel.class);
                        apiCallBack.onCompleted(e, likeSongResponseModel);
                    }
                });
    }

    public static void disLikeSong(Activity activity, String songId, String like, String disLike, String url,
                                   final FutureCallback<LikeSongResponseModel> apiCallBack) {
        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);

        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        int userId = obj.getData().getId();
        JsonObject json = new JsonObject();
        json.addProperty("like", "0");
        json.addProperty("disLike", "1");
        json.addProperty("userId", userId);
        json.addProperty("songId", songId);
        json.addProperty("likes", like);
        json.addProperty("disLikes", disLike);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        LikeSongResponseModel likeSongResponseModel = gson.fromJson(result, LikeSongResponseModel.class);
                        apiCallBack.onCompleted(e, likeSongResponseModel);
                    }
                });
    }

    public static void removeSong(Activity activity, String url, int userId, int totalSavedMemory, String SingleSavedMemory, String SongId,
                                  final FutureCallback<RemoveSongResponseModel> apiCallBack) {

        JsonObject json = new JsonObject();
        json.addProperty("userId", userId);
        json.addProperty("savedSongMemory", totalSavedMemory);
        json.addProperty("singleSaveMemory", SingleSavedMemory);
        json.addProperty("songId", SongId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        RemoveSongResponseModel saveSongResponseModel = gson.fromJson(result, RemoveSongResponseModel.class);
                        apiCallBack.onCompleted(e, saveSongResponseModel);
                    }
                });


    }

    public static void getTop3SongWitArtistProfile(Activity activity, String url, String artistId,
                                                   final FutureCallback<AtristProfileWithTop3SongsResponseModel> apiCallBack) {

        JsonObject json = new JsonObject();
        json.addProperty("artistId", artistId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AtristProfileWithTop3SongsResponseModel saveSongResponseModel = gson.fromJson(result, AtristProfileWithTop3SongsResponseModel.class);
                        apiCallBack.onCompleted(e, saveSongResponseModel);
                    }
                });


    }

    public static void artistGetProfile(Activity activity, String url, String id, String userType,
                                        final FutureCallback<ArtistGetProfileResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("userType", userType);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistGetProfileResponseModel getProfileResponseModel = gson.fromJson(result, ArtistGetProfileResponseModel.class);
                        apiCallBack.onCompleted(e, getProfileResponseModel);
                    }
                });
    }


    public static void getSingleArtistAllSongs(Activity activity, String url, String artistId, String userId,
                                               final FutureCallback<ArtistGetSongResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("artistId", artistId);
        jsonObject.addProperty("userId", userId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistGetSongResponseModel getSongResponseModel = gson.fromJson(result, ArtistGetSongResponseModel.class);
                        apiCallBack.onCompleted(e, getSongResponseModel);
                    }
                });
    }

    public static void changePasswordboth(Activity activity, String url, String email, String oldPassword, String newPassword, String
            userType, String id, final FutureCallback<ChangePasswordResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", oldPassword);
        jsonObject.addProperty("newPassword", newPassword);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("id", id);

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ChangePasswordResponseModel changePasswordResponseModel = gson.fromJson(result, ChangePasswordResponseModel.class);
                        apiCallBack.onCompleted(e, changePasswordResponseModel);
                    }
                });

    }

    public static void artisteditProfileWithoutImage(Activity activity, String url, String id, String firstname, String lastname, String name
            , String phone, String about, String city, String country, String dob, String gender, String userType,
                                                     String image, final FutureCallback<ArtistEditProfileResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("firstName", firstname);
        jsonObject.addProperty("lastname", lastname);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("phone", phone);
        jsonObject.addProperty("address", about);
        jsonObject.addProperty("about", "");
        jsonObject.addProperty("city", city);
        jsonObject.addProperty("country", country);
        jsonObject.addProperty("dob", dob);
        jsonObject.addProperty("gender", gender);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("image", "");

        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistEditProfileResponseModel editprofile = gson.fromJson(result, ArtistEditProfileResponseModel.class);
                        apiCallBack.onCompleted(e, editprofile);
                    }
                });
    }

    public static void artistEditProfileWithImage(Activity activity, String url, String id, String firstname, String lastname, String name
            , String phone, String address, String city, String country, String dob, String gender, String userType,
                                                  String image, final FutureCallback<ArtistEditProfileWithImageResponseModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .setMultipartParameter("id", id)
                .setMultipartParameter("firstName", firstname)
                .setMultipartParameter("lastname", lastname)
                .setMultipartParameter("name", name)
                .setMultipartParameter("phone", phone)
                .setMultipartParameter("address", "xx")
                .setMultipartParameter("city", city)
                .setMultipartParameter("country", country)
                .setMultipartParameter("dob", dob)
                .setMultipartParameter("gender", gender)
                .setMultipartParameter("userType", userType)
                .setMultipartFile("image", "image/jpeg", new File(image))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistEditProfileWithImageResponseModel editprofile = gson.fromJson(result, ArtistEditProfileWithImageResponseModel.class);
                        apiCallBack.onCompleted(e, editprofile);
                    }
                });
    }

    public static void artistAddNote(Activity activity, String url, String email, String id, final String addNote, String addNoteDate,
                                     final FutureCallback<AddNoteResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("addnote", addNote);
        jsonObject.addProperty("addnoteDate", addNoteDate);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        AddNoteResponseModel addNoteResponseModel = gson.fromJson(result, AddNoteResponseModel.class);
                        apiCallBack.onCompleted(e, addNoteResponseModel);
                    }
                });
    }

    public static void artistDeleteNote(Activity activity, String url, String email, String id,
                                        final FutureCallback<DeleteNoteResponseModel> apiCallBack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("id", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        DeleteNoteResponseModel deleteNoteResponseModel = gson.fromJson(result, DeleteNoteResponseModel.class);
                        apiCallBack.onCompleted(e, deleteNoteResponseModel);
                    }
                });
    }

    public static void artistSendMail(Activity activity, String url, String email, String name, String userType, String id,
                                      final FutureCallback<ArtistSendEmailResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("id", id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistSendEmailResponseModel artistSendEmailResponseModel = gson.fromJson(result, ArtistSendEmailResponseModel.class);
                        apiCallBack.onCompleted(e, artistSendEmailResponseModel);
                    }
                });
    }

    public static void artistEmailUpdate(Activity activity, String url, String email, String userType, String id, String password,
                                         final FutureCallback<ArtistUpdateEmailResponseModel> apiCallBack) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("userType", userType);
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("password", password);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(jsonObject)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ArtistUpdateEmailResponseModel artistUpdateEmailResponseModel = gson.fromJson(result, ArtistUpdateEmailResponseModel.class);
                        apiCallBack.onCompleted(e, artistUpdateEmailResponseModel);
                    }
                });
    }

    public static void contactAdmin(Activity activity, String url, String email, String title, String body,
                                    final FutureCallback<ContactFormResponseModel> apiCallBack) {
        Gson gsonLoginData = new Gson();
        String jsonLogin = PreferenceManager.getInstance(activity).getString(PreferenceManager.loginData);
        LoginResponseModel obj = gsonLoginData.fromJson(jsonLogin, LoginResponseModel.class);
        String userType = obj.getData().getUserType();
        String id = String.valueOf(obj.getData().getId());
        JsonObject json = new JsonObject();
        json.addProperty("title", title);
        json.addProperty("user_Id", id);
        json.addProperty("messageBody", body);
        json.addProperty("email", email);
        json.addProperty("user_name", obj.getData().getName());
        json.addProperty("userType", userType);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        ContactFormResponseModel isUserExistResponseModel = gson.fromJson(result, ContactFormResponseModel.class);
                        apiCallBack.onCompleted(e, isUserExistResponseModel);
                    }
                });
    }

    public static void callApiThatSongHasPlayed(Activity activity, String url, String userId, String SongId,
                                                final FutureCallback<IsSongPlayedModel> apiCallBack) {

        JsonObject json = new JsonObject();
        json.addProperty("songId", SongId);
        json.addProperty("userId", userId);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(url))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        IsSongPlayedModel updateTokenResponse = gson.fromJson(result, IsSongPlayedModel.class);
                        apiCallBack.onCompleted(e, updateTokenResponse);
                    }
                });

    }


}
