package com.brots.music.application.activity.loginRegistraitionFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.activity.afterrSelectUser.After_Select_User;
import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.LoginResponseModel;
import com.brots.music.application.model.response.UpdateTokenResponse;
import com.brots.music.application.util.Font;
import com.brots.music.application.R;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login_Activity extends AppCompatActivity {

    private PreferenceManager sharedPreferences;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.usernametext)
    EditText usernametext;
    @BindView(R.id.passwordtext)
    EditText passwordtext;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.signinlayout)
    LinearLayout signinlayout;
    @BindView(R.id.tv_forgot_password)
    TextView tvForgotPassword;
    @BindView(R.id.tv_already_account)
    LinearLayout tvAlreadyAccount;
    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;
    @BindView(R.id.cordinate)
    CoordinatorLayout cordinate;
    String usertype;
    String password, email;
    NewProgressBar dialog;
    String firebaseregId;
    private String endpoint;
    private userOnlineInfo user;
    Activity activity;
    private String name, deviceToken, passwordUser, email1,userType,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        activity = Login_Activity.this;
        user = new userOnlineInfo();
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        usertype = intent.getStringExtra("usertype");
        sharedPreferences = new PreferenceManager(this);
    }

    @OnClick({R.id.submit, R.id.tv_forgot_password, R.id.tv_already_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit:
                submit.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                Login();
                break;

            case R.id.tv_forgot_password:
                Intent forgot = new Intent(Login_Activity.this, Forgot_password.class);
                forgot.putExtra("usertype", usertype);

                startActivity(forgot);
                finish();
                break;
            case R.id.tv_already_account:
                Intent signup = new Intent(Login_Activity.this, Register_Simple_Screen.class);
                signup.putExtra("usertype", usertype);
                startActivity(signup);
                finish();
                break;
        }
    }

    private void Login() {
        email = usernametext.getText().toString().trim();
        password = passwordtext.getText().toString().trim();
        if (user.isOnline(activity)) {
            dialog=new NewProgressBar(activity);
            dialog.show();
            setLoginEndPoint();
            Apicaller.doLogin(activity, endpoint, email, password,
                    new FutureCallback<LoginResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, LoginResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            if(result.getError().equals("False")){
                                Toast.makeText(activity, "Invalid Credential", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                return;}
                            if(result.getMessage().equals("200"))
                            {
                                dialog.dismiss();
                            }

                            Toast.makeText(activity, "Login Success", Toast.LENGTH_SHORT).show();
                            saveLoginDatra(result);
                            getLoginDataAndSaveSomeValue(result);
                            getAndUpdateFireBaseId();

                        }
                    }
            );
        }else
        {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }

    }

    private void getLoginDataAndSaveSomeValue(LoginResponseModel responseModel) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PreferenceManager.loginData);
        LoginResponseModel obj = gson.fromJson(json, LoginResponseModel.class);
        name = obj.getData().getName();
        userId = String.valueOf(obj.getData().getId());
        deviceToken = obj.getData().getDeviceToken();
        passwordUser = obj.getData().getPassword();
        email1 = obj.getData().getEmail();
        userType = obj.getData().getUserType();

        sharedPreferences.putString(PreferenceManager.name,name);
        sharedPreferences.putString(PreferenceManager.id,userId);
        sharedPreferences.putString(PreferenceManager.name,name);
        sharedPreferences.putString(PreferenceManager.email,email1);
        sharedPreferences.putString(PreferenceManager.userType,userType);
        sharedPreferences.putString(PreferenceManager.password,password);
    }

    private void setLoginEndPoint() {
        if (usertype.equals("1")) {
            endpoint = Config.Url.userlogin;
        } else if (usertype.equals("2")) {
            endpoint = Config.Url.artistlogin;
        }
    }

    private void getAndUpdateFireBaseId() {
        firebaseregId = sharedPreferences.getString(PreferenceManager.REGID);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PreferenceManager.loginData);
        LoginResponseModel obj = gson.fromJson(json, LoginResponseModel.class);
        int id = obj.getData().getId();
        if (user.isOnline(activity)) {
            setEndPoint();
            Apicaller.updateDeviceToken(activity, id, firebaseregId, endpoint,
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

                            }
                            GoToHomeActivity();

                        }
                    }
            );
        }
    }

    private void GoToHomeActivity() {
        if(userType.equals("1")){
            Intent intent = new Intent(activity, MainActivityTabs.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else if(userType.equals("2")){
            Intent intent = new Intent(activity, AfterArtistLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }

    private void saveLoginDatra(LoginResponseModel result) {
        final Gson gson = new Gson();
        String json = gson.toJson(result);
        sharedPreferences.putString(PreferenceManager.loginData, json);
    }

    private void setEndPoint() {
        if (usertype.equals("1")) {
            endpoint = Config.Url.updateUserDeviceToken;
        } else if (usertype.equals("2")) {
            endpoint = Config.Url.updateArtistDeviceToken;
        }

    }

    @Override
    public void onBackPressed() {
        Intent signup = new Intent(Login_Activity.this, After_Select_User.class);
        signup.putExtra("usertype", usertype);
        startActivity(signup);
        finish();
    }

}
