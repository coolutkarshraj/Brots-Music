package com.brots.music.application.activity.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ArtistEditProfileResponseModel;
import com.brots.music.application.model.response.ArtistEditProfileWithImageResponseModel;
import com.brots.music.application.model.response.ArtistGetProfileResponseModel;
import com.brots.music.application.model.response.ArtistSendEmailResponseModel;
import com.brots.music.application.model.response.ArtistUpdateEmailResponseModel;
import com.brots.music.application.model.response.SendEmailResponseModel;
import com.brots.music.application.model.response.UpdateEmailIdResponseModel;
import com.brots.music.application.model.responseData.ArtistUpdateEmailDataModel;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.bumptech.glide.Glide;
import com.brots.music.application.util.ImageUtility;
import com.brots.music.application.util.PermissionFile;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.google.android.gms.common.api.Api;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ArtisteditProfileActivity extends AppCompatActivity implements OnApihit,View.OnClickListener {

    String Otpvalue;
  int CameraPicker=124;
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static final int REQUEST_CODE_LOCATION =1000;
    private static final int REQUEST_CODE_STORAGE =1003 ;
    private static int GalleryPicker=123;
    TextView textView;
    userOnlineInfo user;
    NewProgressBar dialog;
    String value = "";
    Dialog dialog1;
    Dialog dialog3;
    Dialog dialog2;
    String name1, userType, id, deviceToken, password1, email1,endPointSendMail,endPointUpdateMail;
    PreferenceManager sharedPreferences;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.change)
    TextView change;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.edittxt_name)
    EditText edittxtName;
    @BindView(R.id.txt_bio)
    TextView txtBio;
    @BindView(R.id.bio)
    EditText bio;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    String emailnew;
    @BindView(R.id.edittxtemail)
    EditText edittxtemail;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.edittxt_phone)
    EditText edittxtPhone;
    @BindView(R.id.txt_gender)
    TextView txtGender;
    @BindView(R.id.gender)
    EditText gender;
    @BindView(R.id.city_a)
    EditText artistCity;
    @BindView(R.id.countrya)
    EditText artistCountry;
    String Gender,CityArtist,CountryArtist;
    Activity activity;
    PermissionFile permissionFile;
    ImageUtility imageUtility;
    File destination;
    Uri outputFileUri;
    String licenseFile = "";
    String endpoint, sendemailendpoint, updateemailendpoint;
    SharedPreferences.Editor ed;
    String city, Country, Dob, Address;
    @BindView(R.id.saveprofile)
    ImageView saveprofile;
    String FirstName, LastName;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.editmail)
    ImageView Mail;
    @BindView(R.id.back)
    ImageView BackButton;
    String phone,About,endpointupdateprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artistedit_profile);

        ButterKnife.bind(this);
        BackButton.setOnClickListener(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        activity=ArtisteditProfileActivity.this;

        localStorage();
        ProfileViewUser();
        Mail.setOnClickListener(this);
        change.setOnClickListener(this);
        saveprofile.setOnClickListener(this);
        readWritePermission();
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility(activity);
        multiplePermission();

    }


    private void localStorage()
    {
        sharedPreferences = new PreferenceManager(this);
        name1 = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==R.id.editmail)
         {
          email();
         }
             if(v.getId() == R.id.change)
             {
                selectImage();
              }
              if(v.getId() == R.id.saveprofile)
              {
                  saveProfileData();
              }
                if(v.getId() == R.id.back)
                {
                    onBackPressed();
                }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void readWritePermission() {
        boolean hasPermission = (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    void multiplePermission() {

        if (!permissionFile.checkLocStorgePermission(this)) {
            permissionFile.checkLocStorgePermission(this);
        }
    }

    private void selectImage() {
        dialog1 = new Dialog(ArtisteditProfileActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog1.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog1.setContentView(R.layout.userprofilephoto);
        dialog1.setTitle("");
        final TextView Takephoto = (TextView) dialog1.findViewById(R.id.take);
        final TextView Gallery = (TextView) dialog1.findViewById(R.id.gall);
        final ImageView cancel = (ImageView) dialog1.findViewById(R.id.clear);
        final TextView Choose=(TextView)dialog1.findViewById(R.id.chooseoption);
        Typeface face=Typeface.createFromAsset(getAssets(),"Proxima_Nova_Thin.otf");
        Takephoto.setTypeface(face);
        Gallery.setTypeface(face);
        Choose.setTypeface(face);
        cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        Takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });
        Gallery.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                GalleryIntent();
            }
        });
        dialog1.show();
    }

 private void GalleryIntent() {
     Intent pickIntent = new Intent(Intent.ACTION_PICK);
     pickIntent.setType("image/*");
     startActivityForResult(pickIntent, ArtisteditProfileActivity.GalleryPicker);
 }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File root = permissionFile.getFile();
        root.mkdirs();
        String filename = permissionFile.getUniqueImageFilename();
        destination = new File(root, filename);
        outputFileUri = FileProvider.getUriForFile(
                ArtisteditProfileActivity.this,
                ArtisteditProfileActivity.this
                        .getPackageName() + ".provider", destination);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, CameraPicker);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (requestCode == GalleryPicker) {
                onCaptureImageResult(data, "gallery");

            }
        } else if (resultCode == RESULT_OK && requestCode == CameraPicker) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            onCaptureImageResult(data, "camera");

        }
    }


    void onCaptureImageResult(Intent data, String imageType) {

        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Toast.makeText(ArtisteditProfileActivity.this, "submit", Toast.LENGTH_SHORT).show();
            Log.e("camerapic",licenseFile);
            dialog1.dismiss();
            File imgFile = new  File(licenseFile);
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);

            }

        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(ArtisteditProfileActivity.this, data.getData()));
            Toast.makeText(ArtisteditProfileActivity.this, "submit", Toast.LENGTH_SHORT).show();
            Log.e("gallerypic",licenseFile);
            dialog1.dismiss();
            File imgFile = new  File(licenseFile);
            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);

            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                } else {
                    Toast.makeText(ArtisteditProfileActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;

            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                }
                break;

        }

    }

    private void saveProfileData() {   //update profile with image and without image
        FirstName= edittxtName.getText().toString().trim();
        LastName= bio.getText().toString().trim();
        phone= edittxtPhone.getText().toString().trim();
        CityArtist= artistCity.getText().toString().trim();
        CountryArtist= artistCountry.getText().toString().trim();
        Gender=gender.getText().toString().trim();
        if(Gender.equals("Male") || Gender.equals("male")) {
            value="1";
        }else {
            value="2";
        }

        if (licenseFile.equals("")) {
            updateProfilewithoutImage();      // only profile update
        } else {

            /*
             profile update with image*/
            upddate(licenseFile, id, FirstName, LastName, CountryArtist, Dob, CityArtist, phone, value,About);
        }
    }

    /*
    * update profile without image*/

    private void updateProfilewithoutImage() {
        user = new userOnlineInfo();
        if (user.isOnline(ArtisteditProfileActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiurl();
            Apicaller.artisteditProfileWithoutImage(activity, endpointupdateprofile, id, FirstName, LastName, FirstName + " " + LastName, phone, "", CityArtist, CountryArtist, Dob,value
                    , userType,"", new FutureCallback<ArtistEditProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistEditProfileResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            updateData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void updateData(ArtistEditProfileResponseModel result) {
        String API_Status = result.getStatus();
        if (API_Status.equals("true")) {
            dialog.dismiss();
            ProfileViewUser();
            Toast.makeText(activity, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            dialog.dismiss();
        }
    }

    private void apiurl()
    {
        endpoint = Config.Url.artistGetProfile;
        endpointupdateprofile = Config.Url.artistUpdate;
        endPointSendMail = Config.Url.artistSendEmail;
        endPointUpdateMail = Config.Url.artistUpdateEmail;
    }

        /* upadte profile with image*/

    private void upddate( String  licenseFile,String id,String FirstName,String LastName,String Country,String DAte,String City,String Phone ,String gender,String About) {

        user = new userOnlineInfo();
        if (user.isOnline(ArtisteditProfileActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiurl();
            Apicaller.artistEditProfileWithImage(activity, endpointupdateprofile, id, FirstName, LastName, FirstName + " " + LastName,
                    Phone, "", City, Country, DAte, gender, userType, licenseFile, new FutureCallback<ArtistEditProfileWithImageResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistEditProfileWithImageResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            String API_Status=result.getStatus();
                            if(API_Status.equals("true"))
                            {
                                dialog.dismiss();
                                ProfileViewUser();
                                Toast.makeText(activity, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }


    private void ProfileViewUser() {              // Artist profile
        user = new userOnlineInfo();
        if (user.isOnline(ArtisteditProfileActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiurl();
            Apicaller.artistGetProfile(activity, endpoint, id, userType, new
                    FutureCallback<ArtistGetProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistGetProfileResponseModel result) {
                            if( e!=null)
                            {
                                return;
                            }
                            getProfileData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(ArtisteditProfileActivity.this);
        }
    }

    private void getProfileData(ArtistGetProfileResponseModel result) {   // profile get data set into views
        String API_Status = result.getStatus();
        if (API_Status.equals("true")) {
            dialog.dismiss();
            edittxtName.setText(result.getData().getFirstName());
            edittxtemail.setText(result.getData().getEmail());
            edittxtPhone.setText(result.getData().getPhone());
            bio.setText(result.getData().getLastName());
            artistCountry.setText(result.getData().getCountry());
            artistCity.setText(result.getData().getCity());

            FirstName = result.getData().getFirstName();
            LastName = result.getData().getLastName();
            city = result.getData().getCity();
            Country = result.getData().getCountry();
            Address = result.getData().getAddress();
            Dob = result.getData().getDOB();
            phone = result.getData().getPhone();
            About = result.getData().getAbout();
            value = result.getData().getGender();

            if (value.equals("1")) {
                gender.setText("Male");
                male.setChecked(true);
                female.setChecked(false);

            } else if (value.equals("2")) {
                gender.setText("Female");
                male.setChecked(false);
                female.setChecked(true);
            }
            Glide.with(activity).load(result.getData().getImageUrl()).into(img);

        } else {
            dialog.dismiss();

        }
    }

/*---------------------------------------------- Edit Email id dialog-----------------------------------------------*/

    public void email() {
        dialog3 = new Dialog(ArtisteditProfileActivity.this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog3.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog3.setContentView(R.layout.editemaildialog);
        dialog3.setTitle("");
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        final EditText oldpass = (EditText) dialog3.findViewById(R.id.edittxt_name);
        oldpass.setText(email1);
        final TextView  Heading=(TextView)dialog3.findViewById(R.id.headingedit);
        final TextView Sub=(TextView)dialog3.findViewById(R.id.txt_name);
        final TextView Verfiy = (TextView) dialog3.findViewById(R.id.verfiy);
        final TextView Cancel = (TextView) dialog3.findViewById(R.id.cancel);
        final ImageView Clear = (ImageView) dialog3.findViewById(R.id.clear);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"Proxima_Nova_Thin.otf");
        Heading.setTypeface(typeface);
        oldpass.setTypeface(typeface);
        Verfiy.setTypeface(typeface);
        Cancel.setTypeface(typeface);
        Sub.setTypeface(typeface);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                dialog3.dismiss();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
        Verfiy.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (oldpass.getText().length() == 0) {
                    oldpass.setText("Please fill Email Address");
                } else {
                    sendOTPToMail(oldpass.getText().toString());
                    emailnew = oldpass.getText().toString();

                }

            }
        });
        dialog3.show();
    }

    /*------------------------------------------------------send otp yo email id--------------------------------------------------*/
    private void sendOTPToMail(String s) {
        user = new userOnlineInfo();
        if (user.isOnline(ArtisteditProfileActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiurl();
            Apicaller.artistSendMail(activity, endPointSendMail, s, name1, userType, id, new
                    FutureCallback<ArtistSendEmailResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ArtistSendEmailResponseModel result) {
                            if(e!=null){
                                return;
                            }
                          emailUpdateGetData(result);
                        }
                    });

        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void emailUpdateGetData(ArtistSendEmailResponseModel result) {
        String API_Status = result.getStatus();
        if(API_Status.equals("true")) {
            Otpvalue =result.getData().getPasscode();
            emailverfiy(Otpvalue);
            dialog.dismiss();
        }else {
            Toast.makeText(activity, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }


/*------------------------------------------ OTP fill into this dialog-----------------------------------------*/

        public void emailverfiy(final String Otpvalue) {
            dialog2 = new Dialog(ArtisteditProfileActivity.this);
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog2.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog2.setContentView(R.layout.emailverifydialog);
            dialog2.setTitle("");
            Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
            font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

            final TextView HeadVer=(TextView)dialog2.findViewById(R.id.headingverify);
            final TextView Name = (TextView) dialog2.findViewById(R.id.txt_name);
            final TextView Emailid = (TextView) dialog2.findViewById(R.id.emailid);
            Emailid.setText(emailnew);
            final ImageView Clear = (ImageView) dialog2.findViewById(R.id.clear);
            final TextView Message = (TextView) dialog2.findViewById(R.id.msgTxt);
            final EditText ot1 = (EditText) dialog2.findViewById(R.id.otpEdit1);
            final TextView ot2 = (EditText) dialog2.findViewById(R.id.otpEdit2);
            final TextView ot3 = (EditText) dialog2.findViewById(R.id.otpEdit3);
            final TextView ot4 = (EditText) dialog2.findViewById(R.id.otpEdit4);
            final TextView Verfy = (TextView) dialog2.findViewById(R.id.submit);
            final TextView Resend = (TextView) dialog2.findViewById(R.id.resend);
            Typeface typeface=Typeface.createFromAsset(getAssets(),"Proxima_Nova_Thin.otf");
            HeadVer.setTypeface(typeface);
            Name.setTypeface(typeface);
            Emailid.setTypeface(typeface);
            Message.setTypeface(typeface);
            ot1.setTypeface(typeface);
            ot2.setTypeface(typeface);
            ot3.setTypeface(typeface);
            ot4.setTypeface(typeface);
            Verfy.setTypeface(typeface);
            Resend.setTypeface(typeface);


            ot1.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (ot1.getText().toString().length() == 1){
                        ot2.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }

            });
            ot2.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (ot2.getText().toString().length() == 1) {
                        ot3.requestFocus();
                    } else if (ot2.getText().toString().length() == 0) {
                        ot1.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                public void afterTextChanged(Editable s) {

                }
            });
            ot3.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (ot3.getText().toString().length() == 1) {
                        ot4.requestFocus();
                    } else if (ot3.getText().toString().length() == 0) {
                        ot2.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {

                }

            });
            ot4.addTextChangedListener(new TextWatcher() {

                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (ot4.getText().toString().length() == 0) {
                        ot3.requestFocus();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }

            });


            Clear.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    dialog2.dismiss();
                    dialog3.dismiss();
                }
            });
            Verfy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String otp1 = ot1.getText().toString();
                    String Otp2 = ot2.getText().toString();
                    String Otp3 = ot3.getText().toString();
                    String Otp4 = ot4.getText().toString();
                    String finalOTP = otp1 + Otp2 + Otp3 + Otp4;
                    Log.e("finalotp", finalOTP);
                    Log.e("Otpvalue", Otpvalue);
                    if (otp1.length() == 1 && Otp2.length() == 1 && Otp3.length() == 1 && Otp4.length() == 1 && finalOTP.equals(Otpvalue)) {
                        emailUpdate();
                    } else {
                        Toast.makeText(ArtisteditProfileActivity.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            Resend.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    dialog2.dismiss();
                }
            });
            dialog2.show();
        }

/* --------------------------------------------------Artist Update Email id------------------------------------------*/

    private void emailUpdate() {
        user = new userOnlineInfo();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(this);
            dialog.show();
           apiurl();
           Apicaller.artistEmailUpdate(activity, endPointUpdateMail, emailnew, userType, id, password1, new
                   FutureCallback<ArtistUpdateEmailResponseModel>() {
                       @Override
                       public void onCompleted(Exception e, ArtistUpdateEmailResponseModel result) {
                           if(e!=null){
                               return;
                           }
                           getDataEmailChange(result);
                       }
                   });

        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    /*------------------------------------------ Artist update Data---------------------------------------*/

    private void getDataEmailChange(ArtistUpdateEmailResponseModel result) {
        String API_Status = result.getStatus();
        if (API_Status.equals("true")) {

            sharedPreferences.editor();
            sharedPreferences.putString(PreferenceManager.email, emailnew);
            sharedPreferences.commit();
            dialog.dismiss();
            dialog2.dismiss();
            dialog3.dismiss();
            ProfileViewUser();
            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            dialog.dismiss();
            dialog2.dismiss();
            dialog3.dismiss();
        }
    }






    @Override
    public void success(String Response, int index) {


        if (index == 2) {
            Log.e("emailchange", "Reponse:" + Response);
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");
                Log.e("message", jobj.getString("message"));
                if (API_Status.equals("true")) {
                    sharedPreferences.putString(PreferenceManager.email, emailnew);
                    ed.commit();
                    ed.apply();
                    dialog.dismiss();
                    dialog2.dismiss();
                    dialog3.dismiss();
                    ProfileViewUser();
                    Toast.makeText(ArtisteditProfileActivity.this, jobj.getString("message"), Toast.LENGTH_SHORT).show();

                    // JSONObject jo = jobj.getJSONObject("data");

                    // Log.e("passcode", jo.getString("passcode"));
                    // emailverfiy(jo.getString("passcode"));

                } else {
                    dialog.dismiss();

                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
            }
        }
    }

    @Override
    public void error(VolleyError error, int index) {

    }
}
