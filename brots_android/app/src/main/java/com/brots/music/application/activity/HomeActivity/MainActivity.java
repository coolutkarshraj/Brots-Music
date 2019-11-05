package com.brots.music.application.activity.HomeActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;

import com.brots.music.application.R;
import com.brots.music.application.util.Font;


public class MainActivity extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Font font=new Font(getAssets(),"Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));
        b1 = (Button) findViewById(R.id.b1);
    }
//
//    public void replay(View view) {
//        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
//        preferenceManager.setFirstLaunch(true);
//        startActivity(new Intent(MainActivity.this, MainScreen.class));
//        finish();
//    }
}
