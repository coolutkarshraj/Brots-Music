package com.brots.music.application.activity.Profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.EditProfileWithoutImageResponseModel;
import com.brots.music.application.model.response.GetUserProfileResponseModel;
import com.brots.music.application.model.response.SendEmailResponseModel;
import com.brots.music.application.model.response.UpdateEmailIdResponseModel;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.bumptech.glide.Glide;
import com.brots.music.application.util.ImageUtility;
import com.brots.music.application.util.PermissionFile;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;
import com.koushikdutta.async.future.FutureCallback;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class Edit_Profile extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 1004;
    private static final int PICK_IMAGE_REQUEST =1 ;
    private static final int REQUEST_CODE_LOCATION =1000;
    private static final int REQUEST_CODE_STORAGE =1003 ;
    private static int GalleryPicker=123;
    public  static  String imagezRe;
    int CameraPicker=124;
    userOnlineInfo user;
    NewProgressBar dialog;
    PreferenceManager sharedPreferences;
    SharedPreferences sharedPreferencess;
    SharedPreferences.Editor ed;
    Bitmap bitmap;
    Activity activity;
    String name, userType, id, deviceToken, password1, email1;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.input_layout_name)
    TextInputLayout inputLayoutName;
    @BindView(R.id.last_name)
    EditText lastName;
    @BindView(R.id.input_layout_lname)
    TextInputLayout inputLayoutLname;
    @BindView(R.id.about)
    EditText DetailAbout;
    String Aboutuser;
    @BindView(R.id.about_layout_lname)
    TextInputLayout aboutLayoutLname;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.mobile_layout_lname)
    TextInputLayout mobileLayoutLname;
    @BindView(R.id.dob)
    EditText dob;
    @BindView(R.id.dob_layout_lname)
    TextInputLayout dobLayoutLname;
    @BindView(R.id.btn_signup)
    Button UpdateBtn;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.city_layout)
    TextInputLayout cityLayout;
    @BindView(R.id.country)
    EditText country;
    @BindView(R.id.country_layout)
    TextInputLayout countryLayout;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    String value="";
    @BindView(R.id.changephoto)
    FloatingActionButton ChangeProfilePicture;
    @BindView(R.id.changeemail)
    ImageView ChangeEmail;
    Dialog dialog1,dialog2,dialog3;
    validations valid;
    String Otpvalue,emailnew;
    PermissionFile permissionFile;
    ImageUtility imageUtility;
    File destination;
    Uri outputFileUri;
    String licenseFile = "";
    String FirstName,LastName,Country,DAte,City,Phone,endPoint,endPointEmailSend,endPointEmailUpdate,endPointUpdateprofile;
    String gender="";
    public static String a="";
    String getemail;
    ImageView iv_toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        ButterKnife.bind(this);
        intializeviews();

    }

    private void intializeviews() {
        user = new userOnlineInfo();
        activity=Edit_Profile.this;
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        sharedPreferences = new PreferenceManager(this);
        iv_toolbar =  findViewById(R.id.iv_toolbar);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
        Profileget();
        ChangeProfilePicture.setOnClickListener(this);
        ChangeEmail.setOnClickListener(this);
        UpdateBtn.setOnClickListener(this);
        readWritePermission();
        permissionFile = new PermissionFile(activity);
        imageUtility = new ImageUtility( activity);
        multiplePermission();
        bindListner();

    }

    private void bindListner() {
        iv_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

    private void multiplePermission() {
        if (!permissionFile.checkLocStorgePermission(activity)) {
            permissionFile.checkLocStorgePermission(activity);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.changephoto){
            selectImage();
        }
        if(v.getId() ==R.id.changeemail) {
            email();
        }
        if(v.getId() == R.id.btn_signup) {
            updateProfiledata();
            Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateProfiledata() {
        FirstName =firstName.getText().toString().trim();
        LastName=lastName.getText().toString().trim();
        Country=country.getText().toString().trim();
        DAte=dob.getText().toString().trim();
        City=city.getText().toString().trim();
        Phone=mobile.getText().toString().trim();
        Aboutuser=DetailAbout.getText().toString().trim();
        if(male.isChecked()) {
            gender="1";
        }
        else{
            gender="2";
        }
        Log.e("gender12",gender);
        if(licenseFile.equals("")) {
            Updateprofile();
        }
        else {
            Log.e("Profilepicture",licenseFile);
            upddateImage(licenseFile,id,FirstName,LastName,Country,DAte,City,Phone,gender,Aboutuser);
        }
    }


    private void selectImage() {  // Dialog for select Image from Gallaery and Camera

        dialog1 = new Dialog(activity);
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

    private void GalleryIntent() {                          /*Image Pic From Gallery*/
        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, Edit_Profile.GalleryPicker);
    }

    private void cameraIntent() {                          /* Image pick from Camera*/
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File root = permissionFile.getFile();
        root.mkdirs();
        String filename = permissionFile.getUniqueImageFilename();
        destination = new File(root, filename);
        outputFileUri = FileProvider.getUriForFile(
                activity,
                activity
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
            onCaptureImageResult(data,"camera");
        }
    }


    void onCaptureImageResult(Intent data, String imageType) {
        if (imageType.equals("camera")) {
            licenseFile = imageUtility.compressImage(destination.getPath());
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();
            Log.e("camerapic",licenseFile);
            dialog1.dismiss();
            File imgFile = new  File(licenseFile);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);      /* Image Set Into Circular Image press Update then update Image*/
            }
        } else {
            licenseFile = imageUtility.compressImage(imageUtility.getRealPathFromURI(activity, data.getData()));
            Toast.makeText(activity, "submit", Toast.LENGTH_SHORT).show();
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(activity, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;

            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //sessionManager.selectImage(getActivity(), this, getString(R.string.RegisterScreen));
                }
                break;

        }
    }

    private void upddateImage( String  licenseFile,String id,String FirstName,String LastName,String Country,
                          String DAte,String City,String Phone ,String gender,String About) {
        if (user.isOnline(activity)) {
            dialog=new NewProgressBar(activity);
            dialog.show();
            apiUrl();
                 Apicaller.editProfileWithImage(activity, endPointUpdateprofile, id, FirstName,
                         LastName, FirstName, Phone, About, City, Country, DAte, gender
                          , userType, licenseFile, new FutureCallback<EditProfileWithoutImageResponseModel>() {
                   @Override
                   public void onCompleted(Exception e, EditProfileWithoutImageResponseModel result) {
                     if(e!=null) {
                         return;
                     }
                     dataupdateImage(result);
                   }
               });
        }else
            {
                commonDialog comdialog = new commonDialog();
                comdialog.dialogbox(activity);
            }
    }

    private void dataupdateImage(EditProfileWithoutImageResponseModel result) {

        String API_Result=result.getStatus();
        if(API_Result.equals("true")) {
            dialog.dismiss();
            Profileget();
            Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void apiUrl()             // All Api Url
    {
        endPoint=Config.Url.getProfile;                           // Api url get User Profile
        endPointEmailSend=Config.Url.sendEmailToEmailIfNotExist;    // Api send otp to mail id
        endPointEmailUpdate=Config.Url.emailupdate;                 //update Email id
        endPointUpdateprofile=Config.Url.editProfilewithoutPicture;
    }

    public void email()   // Dialog show edit Email id
    {
        dialog3 = new Dialog(activity);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog3.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog3.setContentView(R.layout.editemaildialog);
        dialog3.setTitle("");
        final EditText oldpass1 = (EditText) dialog3.findViewById(R.id.edittxt_name);
        oldpass1.setText(email1);
        final TextView  Heading=(TextView)dialog3.findViewById(R.id.headingedit);
        final TextView Sub=(TextView)dialog3.findViewById(R.id.txt_name);
        final TextView Verfiy = (TextView) dialog3.findViewById(R.id.verfiy);
        final TextView Cancel = (TextView) dialog3.findViewById(R.id.cancel);
        final ImageView Clear = (ImageView) dialog3.findViewById(R.id.clear);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"Proxima_Nova_Thin.otf");
        Heading.setTypeface(typeface);
        oldpass1.setTypeface(typeface);
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
                if(oldpass1.getText().length()==0){
                    oldpass1.setText("Please fill Email Address");
                }else{
                    sendEmail(oldpass1.getText().toString());
                    emailnew=oldpass1.getText().toString();
                }
            }
        });
        dialog3.show();
    }
    private void sendEmail(String s) {              /* Send  Otp to email Id*/
        user = new userOnlineInfo();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiUrl();
            Apicaller.sendMail(activity, endPointEmailSend, s, name, userType, id, new
                    FutureCallback<SendEmailResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, SendEmailResponseModel result) {
                            if(e!=null) {
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

    private void emailUpdateGetData(SendEmailResponseModel result) {
        String API_Status = result.getStatus();
        if(API_Status.equals("true")) {
            Otpvalue = result.getData().getPasscode();
            emailverfiy(Otpvalue);
            dialog.dismiss();
        }else {
            Toast.makeText(activity, ""+result.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private void Emailchange() {                   // Update Email Id
        user = new userOnlineInfo();
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            Apicaller.emailUpdate(activity, endPointEmailUpdate, emailnew, id, userType, password1, new
                    FutureCallback<UpdateEmailIdResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, UpdateEmailIdResponseModel result) {
                            if(e!=null) {
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

    private void getDataEmailChange(UpdateEmailIdResponseModel result) {
        String API_Status = result.getStatus();
        if (API_Status.equals("true")) {
            sharedPreferences.editor();
            sharedPreferences.putString(PreferenceManager.email, emailnew);
            sharedPreferences.commit();
            dialog.dismiss();
            dialog2.dismiss();
            dialog3.dismiss();
            Profileget();
            Toast.makeText(activity, result.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            dialog.dismiss();
        }
    }

    private void Updateprofile() {               // Update Profile Without Image
        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiUrl();
           Apicaller.editProfileWithoutImage(activity, endPointUpdateprofile, id, FirstName, LastName, FirstName, Phone,
                   Aboutuser, City, Country, DAte, gender, userType, licenseFile, new
                           FutureCallback<EditProfileWithoutImageResponseModel>() {
                               @Override
                               public void onCompleted(Exception e, EditProfileWithoutImageResponseModel result) {
                                   if(e!=null) {
                                       return;
                                   }
                                   sendeditprofiledata(result);
                               }
                           });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void sendeditprofiledata(EditProfileWithoutImageResponseModel result) {
        String API_Status=result.getStatus();
        if (API_Status.equals("true")) {
            dialog.dismiss();
            Profileget();
            Toast.makeText(activity,result.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            dialog.dismiss();

        }
    }

    public void emailverfiy(final String Otpvalue)
    {
        dialog2 = new Dialog(activity);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog2.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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
                if (ot1.getText().toString().length() == 1){     //size as per your requirement
                    ot2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });
        ot2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ot2.getText().toString().length() == 1) {    //size as per your requirement
                    ot3.requestFocus();
                } else if (ot2.getText().toString().length() == 0) {    //size as per your requirement
                    ot1.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        ot3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (ot3.getText().toString().length() == 1) {    //size as per your requirement
                    ot4.requestFocus();
                } else if (ot3.getText().toString().length() == 0) {    //size as per your requirement
                    ot2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        ot4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ot4.getText().toString().length() == 0)  {   //size as per your requirement

                    ot3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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
                Log.e("finalotp",finalOTP);
                Log.e("Otpvalue",Otpvalue);
                if (otp1.length() == 1 && Otp2.length() == 1 && Otp3.length() == 1 && Otp4.length() == 1 && finalOTP.equals(Otpvalue)) {
                    Emailchange();

                } else {
                    Toast.makeText(activity, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();
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

    private void Profileget(){             //Get User Profile
        if (user.isOnline(activity)) {
            apiUrl();
            dialog = new NewProgressBar(this);
            dialog.show();
            Apicaller.userGetProfile(activity, endPoint, id, userType, new
                    FutureCallback<GetUserProfileResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetUserProfileResponseModel result) {
                            if(e!=null) {
                                return;
                            }
                            setData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void setData(GetUserProfileResponseModel result) {    //get Profile data and Set Into Views
        dialog.dismiss();
        firstName.setText(result.getData().getName());
        lastName.setText(result.getData().getLastName());
        getemail=result.getData().getEmail();
        email.setText(getemail);
        mobile.setText(result.getData().getPhone());
        DetailAbout.setText(result.getData().getAddress());
        city.setText(result.getData().getCity());
        country.setText(result.getData().getCountry());
        dob.setText(result.getData().getDOB());
        value = result.getData().getGender();
        if (value.equals("1")) {
            male.setChecked(true);
            female.setChecked(false);
        } else if (value.equals("2")) {
            female.setChecked(true);
            male.setChecked(false);
        }
        String gender=result.getData().getGender();
        imagezRe= result.getData().getImageUrl();
        if(result.getData().getImageUrl().equals("null"))
        {
            if(gender.equals("1")) {
                Glide.with(activity).load(R.drawable.man).into(img);
            }
            else if(gender.equals("2")) {
                Glide.with(activity).load(R.drawable.girl).into(img);
            }
        }
        else{
            Glide.with(activity).load(result.getData().getImageUrl()).into(img);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    @OnClick({R.id.male, R.id.female})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.male:
                value="1";
                male.setChecked(true);
                female.setChecked(false);
                break;
            case R.id.female:
                value="2";
                female.setChecked(true);
                male.setChecked(false);
                break;
        }
    }


}
