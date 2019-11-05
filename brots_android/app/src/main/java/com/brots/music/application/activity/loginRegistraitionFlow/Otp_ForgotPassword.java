package com.brots.music.application.activity.loginRegistraitionFlow;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
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

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ForgetPasswordResponseModel;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;
import com.koushikdutta.async.future.FutureCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Otp_ForgotPassword extends AppCompatActivity {

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
    String email, Otp;
    userOnlineInfo user;
    NewProgressBar dialog;
    validations valid;
    boolean b;
    PreferenceManager preferenceManager;
    private String endPointForgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__forgot_password);
        ButterKnife.bind(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        Otp = intent.getStringExtra("Otp");
        mobileNo.setText(intent.getStringExtra("email"));
        Log.e("Otp", Otp);
        preferenceManager =new PreferenceManager(this);


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

    }

    private void ValidationRegistar() {
        Intent intent = new Intent(Otp_ForgotPassword.this, After_Forget_SetPassword.class);
        intent.putExtra("email",email);
        startActivity(intent);

    }

    @OnClick({R.id.submitBtn, R.id.resend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submitBtn:
                String otp1 = otpEdit1.getText().toString();
                String otp2 = otpEdit2.getText().toString();
                String otp3 = otpEdit3.getText().toString();
                String otp4 = otpEdit4.getText().toString();
                String finalOTP = otp1.toString() + otp2.toString() + otp3.toString() + otp4.toString();
                if (otp1.length() == 1 && otp2.length() == 1 && otp3.length() == 1 && otp4.length() == 1 && finalOTP.equals(Otp)) {
                    ValidationRegistar();
                } else {
                    Toast.makeText(Otp_ForgotPassword.this, "Please Enter correct OTP", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.resend:
                resendOtp();
                break;
        }
    }

    private void resendOtp() {
        user = new userOnlineInfo();
        dialog = new NewProgressBar(this);
        dialog.show();
        if (user.isOnline(Otp_ForgotPassword.this)) {
            setEndPoint();

            Apicaller.sendForgetPasswordOtp(Otp_ForgotPassword.this, endPointForgetPassword,email,preferenceManager.getString(PreferenceManager.userType),
                    new FutureCallback<ForgetPasswordResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ForgetPasswordResponseModel result) {
                            if (e != null) {
                                dialog.dismiss();
                                Toast.makeText(Otp_ForgotPassword.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                return;
                            }
                           if(result.getStatus().equals("false")){
                               dialog.dismiss();
                               Toast.makeText(Otp_ForgotPassword.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                               return;
                            }else{
                               if(result.getStatus().equals("true")){
                                   dialog.dismiss();
                                   Otp =  String.valueOf(result.getData().getPasscode());
                                   Toast.makeText(Otp_ForgotPassword.this, "OTP Successfully  sent to your registered Email", Toast.LENGTH_SHORT).show();
                               }else {
                                   dialog.dismiss();
                                   Toast.makeText(Otp_ForgotPassword.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                   return;
                               }
                            }


                        }
                    }
            );
        }
    }

    private void setEndPoint() {
            String usertype = preferenceManager.getString(PreferenceManager.userType);
            if (usertype.equals("2")) {
                endPointForgetPassword = Config.Base_url + "artist/getPasswordResetCode";
            } else if (usertype.equals("1")) {
                endPointForgetPassword = Config.Base_url + "getPasswordResetCode";
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
