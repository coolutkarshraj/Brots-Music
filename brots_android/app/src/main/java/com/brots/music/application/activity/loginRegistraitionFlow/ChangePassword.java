package com.brots.music.application.activity.loginRegistraitionFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.ChangePasswordResponseModel;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassword extends AppCompatActivity  implements OnApihit {

    @BindView(R.id.txt_back)
    ImageButton txtBack;
    @BindView(R.id.txt_forgotpassword)
    TextView txtForgotpassword;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.old)
    EditText old;
    @BindView(R.id.newpassword)
    EditText newpassword;
    @BindView(R.id.cconfirm_password)
    EditText cconfirmPassword;
    @BindView(R.id.button_forgot_pass)
    Button buttonForgotPass;
    @BindView(R.id.coordinatorLayout2)
    LinearLayout coordinatorLayout2;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    userOnlineInfo user;
    validations valid;
    NewProgressBar dialog;
    boolean b;
    Activity activity;
    String endpoint;
    PreferenceManager sharedPreferences;
    ImageButton txt_back;
    String name,userType,id,deviceToken,password1,email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        activity=ChangePassword.this;
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        txt_back = findViewById(R.id.txt_back);
        sharedPreferences = new PreferenceManager(this);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);

        if(userType.equals("1")){
            endpoint=Config.Url.userchangePassword;
        }else if(userType.equals("2")){
            endpoint=Config.Url.artistChangePassword;
        }
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

    @OnClick(R.id.button_forgot_pass)
    public void onViewClicked() {
        Changepassword();

    }
    private void Changepassword() {
        user = new userOnlineInfo();
        valid = new validations(coordinatorLayout);
        if (user.isOnline(ChangePassword.this)) {
            b = valid.ChangePasswordValidate(ChangePassword.this,old.getText().toString().trim(),newpassword.getText().toString().trim(), cconfirmPassword.getText().toString().trim());
            if (b) {
                dialog = new NewProgressBar(this);
                dialog.show();
                Apicaller.changePasswordboth(activity, endpoint, email1, old.getText().toString().trim(),
                        cconfirmPassword.getText().toString().trim(), userType, id, new
                                FutureCallback<ChangePasswordResponseModel>() {
                                    @Override
                                    public void onCompleted(Exception e, ChangePasswordResponseModel result) {
                                      if(e!=null)
                                      {
                                          return;
                                      }
                                      changePasswordData(result);

                                    }
                                });
            } else {
            }
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(ChangePassword.this);
        }
    }

    private void changePasswordData(ChangePasswordResponseModel result) {
        String API_Status = result.getStatus();

        if (API_Status.equals("true")) {

            Toast.makeText(this, "Password Change Successfully.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            if(userType.equals("1")){
                Intent intent=new Intent(ChangePassword.this, MainActivityTabs.class);
                startActivity(intent);
                finish();
            }else if(userType.equals("2")){
                Intent intent=new Intent(ChangePassword.this, AfterArtistLogin.class);
                startActivity(intent);
                finish();
            }
        } else {
            dialog.dismiss();
            Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

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

    @Override
    public void success(String Response, int index) {
        Log.e("ResponseChangepassword", "Reponse:" + Response);
        try {
            JSONObject jobj = new JSONObject(Response);
            String API_Status = jobj.getString("status");

            Log.e("message",jobj.getString("message"));
            if (API_Status.equals("true")) {

                Toast.makeText(this, "Password Change Successfully.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                if(userType.equals("1")){
                    Intent intent=new Intent(ChangePassword.this, MainActivityTabs.class);
                    startActivity(intent);
                    finish();
                }else if(userType.equals("2")){
                    Intent intent=new Intent(ChangePassword.this, AfterArtistLogin.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(this, jobj.getString("message"), Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            System.out.println(e);
            dialog.dismiss();
        }
    }
    @Override
    public void error(VolleyError error, int index) {
    }
}
