package com.brots.music.application.activity.loginRegistraitionFlow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ForgetPasswordResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Forgot_password extends AppCompatActivity {

    @BindView(R.id.txt_back)
    ImageButton txtBack;
    @BindView(R.id.txt_forgotpassword)
    TextView txtForgotpassword;
    @BindView(R.id.text)
    TextView text;
    validations valid;
    userOnlineInfo user;
    @BindView(R.id.email_forgot)
    EditText emailForgot;
    @BindView(R.id.button_forgot_pass)
    Button buttonForgotPass;
    @BindView(R.id.signup)
    Button signup;
    NewProgressBar dialog;
    @BindView(R.id.cordinate)
    CoordinatorLayout coordinatorLayout2;
    boolean b;
    String Otp;
    String usertype;
    String endpoint;
    ImageButton txt_back;
    PreferenceManager preferenceManager;
    private String endPointForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Random random = new Random();
        Otp = String.format("%04d", random.nextInt(10000));
        Log.e("id", Otp);
        txt_back = findViewById(R.id.txt_back);
        Intent intent = getIntent();
        usertype = intent.getStringExtra("usertype");
        preferenceManager = new PreferenceManager(this);
        bindListner();
    }

    private void bindListner() {
        txt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @OnClick({R.id.button_forgot_pass, R.id.signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_forgot_pass:
                buttonForgotPass.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                Forgot_PasswordMethod();
                break;
            case R.id.signup:
                signup.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                Intent intent = new Intent(Forgot_password.this, Register_Simple_Screen.class);
                intent.putExtra("usertype", usertype);
                startActivity(intent);
                break;
        }

    }

    private void Forgot_PasswordMethod() {
        user = new userOnlineInfo();
        valid = new validations(coordinatorLayout2);
        b = valid.forgotvalidate(Forgot_password.this, emailForgot.getText().toString().trim());
        dialog = new NewProgressBar(this);
        dialog.show();
        if (b) {
            if (user.isOnline(Forgot_password.this)) {
                setEndPointForgetPassword();

                Apicaller.sendForgetPasswordOtp(Forgot_password.this, endPointForgetPassword, emailForgot.getText().toString().trim(), preferenceManager.getString(PreferenceManager.userType),
                        new FutureCallback<ForgetPasswordResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, ForgetPasswordResponseModel result) {
                                if (e != null) {
                                    dialog.dismiss();
                                    Toast.makeText(Forgot_password.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (result.getStatus().equals("false")) {
                                    dialog.dismiss();
                                    Toast.makeText(Forgot_password.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    if (result.getStatus().equals("true")) {
                                        dialog.dismiss();
                                        moveToOtpScreen(result);
                                        Toast.makeText(Forgot_password.this, "OTP Successfully  sent to your registered Email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(Forgot_password.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }


                            }
                        }
                );
            }else {
                Toast.makeText(this, "Please check your internet Connection", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please enter valid Otp", Toast.LENGTH_SHORT).show();
        }
    }

    private void moveToOtpScreen(ForgetPasswordResponseModel result) {
        Intent intent = new Intent(Forgot_password.this, Otp_ForgotPassword.class);
        intent.putExtra("Otp", String.valueOf(result.getData().getPasscode()));
        intent.putExtra("email", emailForgot.getText().toString());
        startActivity(intent);
        finish();

    }

    private void setEndPointForgetPassword() {

        if (usertype.equals("2")) {
            endPointForgetPassword = Config.Base_url + "artist/getPasswordResetCode";
        } else if (usertype.equals("1")) {
            endPointForgetPassword = Config.Base_url + "getPasswordResetCode";
        }

        if (endPointForgetPassword.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/getPasswordResetCode")
                || endPointForgetPassword.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/getPasswordResetCode")) {

        } else {
            usertype = preferenceManager.getString(PreferenceManager.userType);
            if (usertype.equals("2")) {
                endPointForgetPassword = Config.Base_url + "artist/getPasswordResetCode";
            } else if (usertype.equals("1")) {
                endPointForgetPassword = Config.Base_url + "getPasswordResetCode";
            }
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





}
