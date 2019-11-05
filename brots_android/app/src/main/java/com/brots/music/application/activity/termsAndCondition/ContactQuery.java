package com.brots.music.application.activity.termsAndCondition;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.Config;
import com.brots.music.application.R;
import com.brots.music.application.activity.AfterLogin.AfterArtistLogin;
import com.brots.music.application.apiInterface.UrlLocator;
import com.brots.music.application.model.response.CategorySubCategoryResponseModel;
import com.brots.music.application.model.response.ContactFormResponseModel;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.userOnlineInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import retrofit2.http.Body;

public class ContactQuery extends AppCompatActivity implements View.OnClickListener {

    EditText Name, Phone, Message;
    Button Submit;
    Activity activity;
    userOnlineInfo user;
    ImageButton txt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_query);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        intializeViews();
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

    private void intializeViews() {
        user = new userOnlineInfo();
        activity = ContactQuery.this;
        Name = (EditText) findViewById(R.id.name);
        Phone = (EditText) findViewById(R.id.phone);
        Message = (EditText) findViewById(R.id.message);
        Submit = (Button) findViewById(R.id.submit);
        txt_back = (ImageButton) findViewById(R.id.txt_back);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
          String email = Name.getText().toString().trim();
          String title =  Phone.getText().toString().trim();
          String body = Message.getText().toString().trim();
            if (email.equals("") || title.equals("") || body.equals("")) {
                Toast.makeText(activity, "Fields Not Empty", Toast.LENGTH_SHORT).show();
            }
            else{
            subMitForrm(email,title,body);
            }
        }
    }

    private void subMitForrm(String email, String title, String body){
        if (user.isOnline(activity)) {
             String endpoint = Config.Url.subMitForm;
            Apicaller.contactAdmin(activity, endpoint,email,title,body,
                    new FutureCallback<ContactFormResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, ContactFormResponseModel result) {
                            if (e != null) {
                                return;
                            }
                            System.out.print(result);
                            System.out.print(result.getData());
                            if(result.getStatus().equals("false")){
                                Toast.makeText(activity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(activity, "Requested Submitted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ContactQuery.this, AfterArtistLogin.class);
                            startActivity(intent);

                        }
                    }
            );
        }
    }
}