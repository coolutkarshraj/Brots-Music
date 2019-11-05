package com.brots.music.application.activity.termsAndCondition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.brots.music.application.util.Font;
import com.brots.music.application.R;

public class TermsandCondtion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_condtion);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
    }
}
