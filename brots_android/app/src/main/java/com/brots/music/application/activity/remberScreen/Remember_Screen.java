package com.brots.music.application.activity.remberScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.activity.loginRegistraitionFlow.Login_Activity;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import com.squareup.picasso.Picasso;

public class Remember_Screen extends AppCompatActivity  implements OnApihit {
    PreferenceManager sharedPreferences;

    String name, id, deviceToken, userType,password1, email1;
    @BindView(R.id.be_a_traveller)
    TextView beATraveller;
    @BindView(R.id.already_text)
    TextView alreadyText;
    @BindView(R.id.profileimage)
    CircleImageView profileimage;
    @BindView(R.id.name)
    TextView name1;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.signIn_text)
    TextView signInText;
    @BindView(R.id.button_sign_up)
    Button buttonSignUp;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    userOnlineInfo user;
    NewProgressBar dialog;
    String devicetokenid,userid;
    String firebaseregId,gender;
    Activity activity;
    PreferenceManager preferenceManager;
    String updatetokenendpoint,loginendpoint,endPoint;
    String R_name,R_email,R_pic,R_Usertype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember__screen);
        ButterKnife.bind(this);
        activity=Remember_Screen.this;
        user = new userOnlineInfo();
        preferenceManager = new PreferenceManager(activity);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        sharedPreferences = new  PreferenceManager(this);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
        Intent intent = getIntent();
        R_name = intent.getStringExtra("name");
        R_email = intent.getStringExtra("email");
        R_pic = intent.getStringExtra("pic");
        R_Usertype=intent.getStringExtra("usertype");

        getFirebaseID();

        if(R_Usertype.equals("1")){
            updatetokenendpoint="user/updateDeviceToken";
            loginendpoint="user/login";
        }else  if(R_Usertype.equals("2")){
            updatetokenendpoint="artist/updateDeviceToken";
            loginendpoint="artist/login";
        }
        email.setText(R_email);
        name1.setText(R_name);
        Picasso.get().load(R_pic).into(profileimage);

    }
    private void Login() {

        if (user.isOnline(Remember_Screen.this)) {

            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String, String> params = new HashMap<>();
            params.put("email", R_email);
            params.put("password", password.getText().toString().trim());
            String link = Config.Base_url+loginendpoint;
            new VolleyBase(this).main(params, link, 0);
        }
        else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(Remember_Screen.this);
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

    @OnClick({R.id.signIn_text, R.id.button_sign_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signIn_text:
                    Intent intent=new Intent(Remember_Screen.this, Login_Activity.class);
                    intent.putExtra("usertype",userType);
                    startActivity(intent);

                break;
            case R.id.button_sign_up:
                if(password.getText().length()==0){
                    password.setError("Please fill password field");
                    buttonSignUp.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                }else{
                    Login();
                }

                break;
        }
    }
    private void UpdateToken() {
        user = new userOnlineInfo();
        if (user.isOnline(Remember_Screen.this)) {

            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String, String> params = new HashMap<>();
            params.put("id", userid);
            params.put("deviceToken",firebaseregId);


            Log.e("id",userid);
            Log.e("deviceToken"+"devid",devicetokenid);
            String link =Config.Base_url+updatetokenendpoint;
            new VolleyBase(this).main(params, link, 1);
        }

        else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(Remember_Screen.this);
        }
    }

    @Override
    public void success(String Response, int index) {
        if(index==0) {
            Log.e("ResponseLogin", "Reponse:" + Response);
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
                    sharedPreferences.putString(PreferenceManager.savedSongMemory, jo.getString("SavedSongMemory"));
                    sharedPreferences.putString(PreferenceManager.InstaMixMemory, jo.getString("InstaMixMemory"));
                    userid = jo.getString("id");
                    devicetokenid = jo.getString("DeviceToken");

                    UpdateToken();
                    Log.e("id", jo.getString("id"));
                    Log.e("Name", jo.getString("Name"));
                    Log.e("email", jo.getString("Email"));
                    Log.e("userType", jo.getString("UserType"));
                    Log.e("password", jo.getString("Password"));
                    Log.e("deviceToken", jo.getString("DeviceToken"));


                    // message(jobj.getString("message"));

                } else {
                    dialog.dismiss();
                    message(jobj.getString("error"));
                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
            }
        }
        if(index==1){
            Log.e("UpdateToken", "Reponse:" + Response);
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");

                if (API_Status.equals("true")) {
                    Log.e("status update token","2000");
                    Toast.makeText(this, "Login Successful.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    if(R_Usertype.equals("1")){
                        Intent intent=new Intent(Remember_Screen.this, MainActivityTabs.class);
                        startActivity(intent);
                        finish();
                    }else if(R_Usertype.equals("2")){
                        Intent intent=new Intent(Remember_Screen.this, AfterArtistLogin.class);
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
    }

    private void message(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);

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
    public void onBackPressed() {
        Intent signup=new Intent(Remember_Screen.this, After_Splash.class);
        signup.putExtra("usertype",userType);
        startActivity(signup);
        finish();
    }
    @Override
    public void error(VolleyError error, int index) {
        dialog.dismiss();
    }
}
