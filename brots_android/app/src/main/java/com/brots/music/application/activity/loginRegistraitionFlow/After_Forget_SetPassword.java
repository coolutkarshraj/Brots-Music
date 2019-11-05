package com.brots.music.application.activity.loginRegistraitionFlow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.Config;
import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.model.response.UpdatePasswordRresponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class After_Forget_SetPassword extends AppCompatActivity implements OnApihit {

    @BindView(R.id.txt_back)
    ImageButton txtBack;
    @BindView(R.id.txt_forgotpassword)
    TextView txtForgotpassword;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.newpassword)
    EditText newpassword;
    @BindView(R.id.cconfirm_password)
    EditText cconfirmPassword;
    @BindView(R.id.button_forgot_pass)
    Button buttonForgotPass;
    @BindView(R.id.coordinatorLayout2)
    LinearLayout coordinatorLayout2;
    userOnlineInfo user;
    validations valid;
    @BindView(R.id.coordinate)
    CoordinatorLayout coordinate;
    boolean b;
    NewProgressBar dialog;
    PreferenceManager sharedPreferences;
    SharedPreferences.Editor ed;
    String endpoint;
    String name, userType, id, deviceToken, password1, email1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__forget__set_password);
        sharedPreferences = new PreferenceManager(this);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        Intent intent = getIntent();
        email1 = intent.getStringExtra("email");
        ButterKnife.bind(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }

    @OnClick(R.id.button_forgot_pass)
    public void onViewClicked() {
        Resetpassword();

    }

    private void Resetpassword() {
        user = new userOnlineInfo();
        valid = new validations(coordinate);

            b = valid.resetpassword(After_Forget_SetPassword.this, newpassword.getText().toString().trim(), cconfirmPassword.getText().toString().trim());
            if (b) {
                dialog = new NewProgressBar(this);
                dialog.show();
                JsonObject json = new JsonObject();
                json.addProperty("email", email1);
                json.addProperty("userType", userType);
                json.addProperty("password", cconfirmPassword.getText().toString().trim());
                if (user.isOnline(After_Forget_SetPassword.this)) {
                    setEndPoint();
                    Apicaller.updatePassword(After_Forget_SetPassword.this, endpoint,json,
                            new FutureCallback<UpdatePasswordRresponseModel>() {
                                @Override
                                public void onCompleted(Exception e, UpdatePasswordRresponseModel result) {
                                    if (e != null) {
                                        return;
                                    }
                                   if(result.getStatus().equals("true")){
                                       Intent intent = new Intent(After_Forget_SetPassword.this, After_Splash.class);
                                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                       startActivity(intent);
                                       finish();

                                   }else {
                                       Toast.makeText(After_Forget_SetPassword.this, "Password Not Updated Please try again after some times", Toast.LENGTH_SHORT).show();
                                   }


                                }
                            }
                    );
                }


            } else {
//                    submit.setBackground(getDrawable(R.drawable.button_shape_green));
            }
        }

    private void setEndPoint() {
        if (userType.equals("2")) {
            endpoint = Config.Base_url + "artist/updatePassword";
        } else if (userType.equals("1")) {
            endpoint = Config.Base_url +"updatePassword";
        }

        if (endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/updatePassword")
                || endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/updatePassword")) {

        } else {
            userType = sharedPreferences.getString(PreferenceManager.userType);
            if (userType.equals("2")) {
                endpoint = Config.Base_url + "artist/updatePassword";
            } else if (userType.equals("1")) {
                endpoint = Config.Base_url + "updatePassword";
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

    @Override
    public void success(String Response, int index) {
        Log.e("Aftersetpassword", "Reponse:" + Response);
        try {
            JSONObject jobj = new JSONObject(Response);
            String API_Status = jobj.getString("status");

            Log.e("message", jobj.getString("message"));
            if (API_Status.equals("true")) {

                Toast.makeText(this, jobj.getString("message"), Toast.LENGTH_SHORT).show();
                dialog.dismiss();


                JSONObject jo = jobj.getJSONObject("data");
                sharedPreferences.putString(PreferenceManager.id, jo.getString("id"));
                sharedPreferences.putString(PreferenceManager.name, jo.getString("Name"));
                sharedPreferences.putString(PreferenceManager.email, jo.getString("Email"));
                sharedPreferences.putString(PreferenceManager.userType, jo.getString("UserType"));
                sharedPreferences.putString(PreferenceManager.password, jo.getString("Password"));
                sharedPreferences.putString(PreferenceManager.deviceToken, jo.getString("DeviceToken"));

                // message(jobj.getString("message"));
                if (userType.equals("1")) {
                    Intent intent = new Intent(After_Forget_SetPassword.this, MainActivityTabs.class);
                    startActivity(intent);
                    finish();
                } else if (userType.equals("2")) {
                    Intent intent = new Intent(After_Forget_SetPassword.this, AfterArtistLogin.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                dialog.dismiss();
                message(jobj.getString("error"));
            }
        } catch (Exception e) {
            System.out.println(e);
            dialog.dismiss();
        }
    }

    private void message(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinate, message, Snackbar.LENGTH_SHORT);

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
    public void error(VolleyError error, int index) {

    }
}
