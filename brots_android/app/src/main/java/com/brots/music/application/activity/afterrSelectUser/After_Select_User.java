package com.brots.music.application.activity.afterrSelectUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.activity.loginRegistraitionFlow.Login_Activity;
import com.brots.music.application.activity.loginRegistraitionFlow.Register_Screen;
import com.brots.music.application.R;
import com.brots.music.application.activity.loginRegistraitionFlow.Register_Simple_Screen;
import com.brots.music.application.util.Font;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class After_Select_User extends AppCompatActivity {

    @BindView(R.id.login)
    Button login;
    @BindView(R.id.register)
    Button register;
    String usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__select__user);
        ButterKnife.bind(this);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        usertype = intent.getStringExtra("usertype");
    }

    @OnClick({R.id.login, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                // login.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                login.setBackground(getDrawable(R.drawable.button_shape_green));
                //  login.setBackgroundColor(Color.parseColor("#02a9f4"));
                Intent signin = new Intent(After_Select_User.this, Login_Activity.class);
                signin.putExtra("usertype", usertype);
                startActivity(signin);
                finish();
                break;
            case R.id.register:
                register.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                Intent signup = new Intent(After_Select_User.this, Register_Simple_Screen.class);
                signup.putExtra("usertype", usertype);
                startActivity(signup);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent signup = new Intent(After_Select_User.this, After_Splash.class);
        signup.putExtra("usertype", usertype);
        startActivity(signup);
        finish();
    }
}
