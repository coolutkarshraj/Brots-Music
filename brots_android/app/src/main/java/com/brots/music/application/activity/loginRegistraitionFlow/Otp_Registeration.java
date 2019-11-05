package com.brots.music.application.activity.loginRegistraitionFlow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.R;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.model.response.OtpResponseModel;
import com.brots.music.application.model.response.RregistrationResponseModel;
import com.brots.music.application.model.response.UpdateTokenResponse;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.userOnlineInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Otp_Registeration extends AppCompatActivity {

    @BindView(R.id.statusTxt)
    TextView statusTxt;
    @BindView(R.id.seekbar_lay)
    LinearLayout seekbarLay;
    @BindView(R.id.numberTxt)
    TextView numberTxt;
    @BindView(R.id.timeTxt)
    TextView timeTxt;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mobile_no)
    TextView mobileNo;
    @BindView(R.id.msgTxt)
    TextView msgTxt;
    @BindView(R.id.otpEdit1)
    EditText otpEdit1;
    @BindView(R.id.otpEdit2)
    EditText otpEdit2;
    @BindView(R.id.otpEdit3)
    EditText otpEdit3;
    @BindView(R.id.otpEdit4)
    EditText otpEdit4;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    @BindView(R.id.resend)
    Button resend;
    @BindView(R.id.retryBtn)
    Button retryBtn;
    @BindView(R.id.manualLay)
    CardView manualLay;
    @BindView(R.id.coordinatorLayout1)
    LinearLayout coordinatorLayout1;
    userOnlineInfo user;
    NewProgressBar dialog;
    String firstname, lastname, dob, email, gender, password, Otp;
    String city, country, address, registratioDate, usertype;
    PreferenceManager sharedPreferences;

    String name, id, deviceToken, password1, email1;
    String userid, devicetokenid;
    String updatetoken, endpoint;
    String firebaseregId;
    private String endpointRegister;
    private String endpointTokenUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__registeration);
        ButterKnife.bind(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        sharedPreferences = new PreferenceManager(this);
        name = sharedPreferences.getString(PreferenceManager.name);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);

        getFirebaseID();
        firstname = intent.getStringExtra("fname");
        lastname = intent.getStringExtra("lname");
        email = intent.getStringExtra("email");
        city = intent.getStringExtra("city");
        country = intent.getStringExtra("country");
        usertype = intent.getStringExtra("usertype");
        address = intent.getStringExtra("address");
        registratioDate = intent.getStringExtra("registratioDate");
        gender = intent.getStringExtra("gender");
        dob = intent.getStringExtra("dob");
        password = intent.getStringExtra("password");
        Otp = intent.getStringExtra("otp");
        mobileNo.setText(intent.getStringExtra("email"));
        otpEdit1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otpEdit1.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        otpEdit2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otpEdit2.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit3.requestFocus();
                } else if (otpEdit2.getText().toString().length() == 0)     //size as per your requirement
                {
                    otpEdit1.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        otpEdit3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otpEdit3.getText().toString().length() == 1)     //size as per your requirement
                {
                    otpEdit4.requestFocus();
                } else if (otpEdit3.getText().toString().length() == 0)     //size as per your requirement
                {
                    otpEdit2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        otpEdit4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                if (otpEdit4.getText().toString().length() == 0)     //size as per your requirement
                {
                    otpEdit3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manualLay.setVisibility(View.VISIBLE);
                otpEdit1.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                retryBtn.setVisibility(View.GONE);
            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                resendOtp();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                String otp1 = otpEdit1.getText().toString();
                String otp2 = otpEdit2.getText().toString();
                String otp3 = otpEdit3.getText().toString();
                String otp4 = otpEdit4.getText().toString();
                String finalOTP = otp1.toString() + otp2.toString() + otp3.toString() + otp4.toString();
                if (otp1.length() == 1 && otp2.length() == 1 && otp3.length() == 1 && otp4.length() == 1 && finalOTP.equals(Otp)) {
                    Register();

                } else {
                    Toast.makeText(Otp_Registeration.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resendOtp() {
        user = new userOnlineInfo();
        if (user.isOnline(this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            setEndPoint();
            Random random = new Random();
            Otp = String.format("%04d", random.nextInt(10000));
            String name = firstname + lastname;
            Apicaller.sendRegistrationOtp(Otp_Registeration.this, endpoint, name, Otp, email,
                    new FutureCallback<OtpResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, OtpResponseModel result) {
                            if (e != null) {
                                Toast.makeText(Otp_Registeration.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;
                            }
                            if (result.getStatus().equals("false")) {
                                Toast.makeText(Otp_Registeration.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;

                            } else {
                                if (result.getStatus().equals("true")) {
                                    Otp = result.getDate().getPasscode();
                                    dialog.dismiss();
                                    Toast.makeText(Otp_Registeration.this, "Otp successfully sent to your registered mail", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Otp_Registeration.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    return;
                                }
                            }


                        }
                    }
            );
        } else {
            Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setEndPoint() {
        if (usertype.equals("2")) {
            endpoint = Config.Base_url + "artist/registrationVerify";
        } else if (usertype.equals("1")) {
            endpoint = Config.Base_url + "user/registrationVerify";
        }

        if (endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/user/registrationVerify")
                || endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/registrationVerify")) {

        } else {
            usertype = sharedPreferences.getString(PreferenceManager.userType);
            if (usertype.equals("2")) {
                endpoint = Config.Base_url + "artist/registrationVerify";
            } else if (usertype.equals("1")) {
                endpoint = Config.Base_url + "user/registrationVerify";
            }
        }
    }

    private void getFirebaseID() {
        firebaseregId = sharedPreferences.getString(PreferenceManager.REGID);
        Log.e("tag", "Firebase reg id: " + firebaseregId);
        if (!TextUtils.isEmpty(firebaseregId)) {
            Log.d("tag", "displayFirebaseRegId: " + firebaseregId);
        } else {
            Log.d("tag", "Firebase Reg Id is not received yet! ");
        }
    }

    private void UpdateToken() {
        user = new userOnlineInfo();
        firebaseregId = sharedPreferences.getString(PreferenceManager.REGID);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PreferenceManager.loginData);
        LoginResponseModel obj = gson.fromJson(json, LoginResponseModel.class);
        int id = obj.getData().getId();
        if (user.isOnline(Otp_Registeration.this)) {
            setEndPointForUpdateDeviceToken();
            Apicaller.updateDeviceTokenAfterRegistration(Otp_Registeration.this, Integer.parseInt(userid), firebaseregId, endpointTokenUpdate,
                    new FutureCallback<UpdateTokenResponse>() {
                        @Override
                        public void onCompleted(Exception e, UpdateTokenResponse result) {
                            if (e != null) {
                                return;
                            }
                            if (result.getStatus().equals("true")) {
                                sharedPreferences.putString(PreferenceManager.ISLOGGEDIN, "1");
                                sharedPreferences.putString(PreferenceManager.deviceToken, result.getData().getDeviceToken());
                                GoToHomeActivity();

                            }else {
                                Toast.makeText(Otp_Registeration.this, "Token Not Updated", Toast.LENGTH_SHORT).show();
                                GoToHomeActivity();
                            }
                        }
                    }
            );
        }


    }

    private void setEndPointForUpdateDeviceToken() {
        if (usertype.equals("2")) {
            endpointTokenUpdate = Config.Base_url + "artist/updateDeviceToken";
        } else if (usertype.equals("1")) {
            endpointTokenUpdate = Config.Base_url + "user/updateDeviceToken";
        }

        if (endpointTokenUpdate.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/updateDeviceToken")
                || endpointTokenUpdate.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/user/updateDeviceToken")) {

        } else {
            usertype = sharedPreferences.getString(PreferenceManager.userType);
            if (usertype.equals("2")) {
                endpointTokenUpdate = Config.Base_url + "artist/updateDeviceToken";
            } else if (usertype.equals("1")) {
                endpointTokenUpdate = Config.Base_url + "user/updateDeviceToken";
            }
        }
    }

    private void GoToHomeActivity() {
        if (sharedPreferences.getString(PreferenceManager.userType).equals("1")) {
            Log.e("status update token", "2000");
            Toast.makeText(this, "Registration Done Successfully.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            Intent intent = new Intent(Otp_Registeration.this, MainActivityTabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (sharedPreferences.getString(PreferenceManager.userType).equals("2")) {
            Log.e("status update token", "2000");
            Toast.makeText(this, "Registration Done Successful.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
            Intent intent = new Intent(Otp_Registeration.this, AfterArtistLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        }
    }

    private void Register() {
        user = new userOnlineInfo();
        dialog=new NewProgressBar(Otp_Registeration.this);
        dialog.show();
        if (user.isOnline(Otp_Registeration.this)) {
            setEndPointForRegistration();
            JsonObject json = new JsonObject();
            json.addProperty("email", email);
            json.addProperty("name", firstname + " " + lastname);
            json.addProperty("firstName", firstname);
            json.addProperty("lastname", lastname);
            json.addProperty("userType", usertype);
            json.addProperty("password", password);
            json.addProperty("gender", gender);
            json.addProperty("dob", dob);
            json.addProperty("city", city);
            json.addProperty("country", country);
            json.addProperty("address", address);
            json.addProperty("registratioDate", registratioDate);
            json.addProperty("deviceType", "1");
            json.addProperty("deviceToken", "null");
            json.addProperty("isTncAccepted", "1");
            Apicaller.rregister(Otp_Registeration.this, endpointRegister, json,
                    new FutureCallback<RregistrationResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, RregistrationResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Toast.makeText(Otp_Registeration.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (result.getStatus().equals("False")) {
                                dialog.dismiss();
                                Toast.makeText(Otp_Registeration.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                if (result.getStatus().equals("true")) {
                                    dialog.dismiss();
                                    saveLacalData(result);
                                    Toast.makeText(Otp_Registeration.this, result.getMessage(), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(Otp_Registeration.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }


                        }
                    }
            );
        }

    }

    private void saveLacalData(RregistrationResponseModel result) {
        sharedPreferences.putString(PreferenceManager.id, String.valueOf(result.getData().getId()));
        sharedPreferences.putString(PreferenceManager.name, result.getData().getName());
        sharedPreferences.putString(PreferenceManager.email, result.getData().getEmail());
        sharedPreferences.putString(PreferenceManager.userType, result.getData().getUserType());
        sharedPreferences.putString(PreferenceManager.password, result.getData().getPassword());
        sharedPreferences.putString(PreferenceManager.savedSongMemory, result.getData().getSavedSongMemory());
        sharedPreferences.putString(PreferenceManager.InstaMixMemory, result.getData().getInstaMixMemory());
        sharedPreferences.putString(PreferenceManager.REGID, result.getData().getDeviceToken());
        final Gson gson = new Gson();
        String json = gson.toJson(result);
        sharedPreferences.putString(PreferenceManager.loginData, json);
        userid = String.valueOf(result.getData().getId());
        devicetokenid = result.getData().getDeviceToken();
        UpdateToken();
    }

    private void setEndPointForRegistration() {

        if (usertype.equals("2")) {
            endpointRegister = Config.Base_url + "artist/register";
        } else if (usertype.equals("1")) {
            endpointRegister = Config.Base_url + "user/register";
        }

        if (endpointRegister.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/register")
                || endpointRegister.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/user/register")) {

        } else {
            usertype = sharedPreferences.getString(PreferenceManager.userType);
            if (usertype.equals("2")) {
                endpointRegister = Config.Base_url + "artist/registrationVerify";
            } else if (usertype.equals("1")) {
                endpointRegister = Config.Base_url + "user/registrationVerify";
            }
        }
    }



    private void message(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout1, message, Snackbar.LENGTH_SHORT);

        snackbar.setActionTextColor(Color.parseColor("#803d3b3b"));

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(Color.parseColor("#803d3b3b"));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextSize(16);
        //textView.setAllCaps(true);
        snackbar.show();


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
}
