package com.brots.music.application.activity.notifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.model.response.AddNoteResponseModel;
import com.brots.music.application.model.response.DeleteNoteResponseModel;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SendnotificationActivity extends AppCompatActivity implements OnApihit {

    TextView Notice_Message;
    Dialog dialog1;
    Dialog dialog2;
    Dialog dialog3;
    @BindView(R.id.profile_imagex)
    CircleImageView profileImagex;
    @BindView(R.id.show_message)
    TextView showMessage;
    @BindView(R.id.txt_seen)
    TextView txtSeen;
    @BindView(R.id.text_send)
    EditText textSend;
    @BindView(R.id.btn_send)
    ImageButton btnSend;
    String formattedDate,endPoint,endPointDelete;
    userOnlineInfo user;
    NewProgressBar dialog; String msgss,pic;
    Activity activity;
    PreferenceManager sharedPreferences;
    String name, userType, id, deviceToken, password1, email1;
    @BindView(R.id.showallmessage)
    RelativeLayout showallmessage;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendnotification);
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        ButterKnife.bind(this);
        user = new userOnlineInfo();
        activity=SendnotificationActivity.this;
        localStorage();
        iv_back = findViewById(R.id.iv_back);
        Notice_Message = (TextView) findViewById(R.id.show_message);
        getNote();

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        Log.e("Date", "" + formattedDate);
        Notice_Message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectImage();
                return false;
            }
        });
        bindListner();

        
    }

    private void bindListner() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNotificcation();
            }
        });
    }

    private void localStorage() {
        sharedPreferences = new PreferenceManager(this);
        name = sharedPreferences.getString(PreferenceManager.name);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        email1 = sharedPreferences.getString(PreferenceManager.email);
    }


    private void apiUrl() {

        endPoint = Config.Url.artistAddNote;        // add note+ edit note
        endPointDelete = Config.Url.artistDeleteNote;
    }

    private void SendNotificcation() {  // artist add note


        if (user.isOnline(activity)) {

            dialog = new NewProgressBar(this);
            dialog.show();
            apiUrl();
            Apicaller.artistAddNote(activity, endPoint, email1, id, textSend.getText().toString().trim(),
                    formattedDate, new FutureCallback<AddNoteResponseModel>() {
                       @Override
                       public void onCompleted(Exception e, AddNoteResponseModel result) {
                           if(e!=null) {
                               return;
                           }
                           addNoteData(result);
                       }
            });
                } else {
                    commonDialog comdialog = new commonDialog();
                    comdialog.dialogbox(activity);
                }
    }

    private void addNoteData(AddNoteResponseModel result) {
        dialog.dismiss();
        try {
            String API_Status =result.getStatus();
            String message = result.getMessage();

            if (API_Status.equals("true")) {

                showallmessage.setVisibility(View.VISIBLE);
                Notice_Message.setText(textSend.getText().toString().trim());
                textSend.setText("");
                Picasso.get().load(pic).into(profileImagex);
                SendPushNotificcation();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        } catch (Exception e) {
            System.out.println(e);
            dialog.dismiss();
            dialog2.dismiss();
        }

    }
    private void selectImage() {            // Option for dialog you want to perform action like  edit note or delete note

        dialog1 = new Dialog(SendnotificationActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog1.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog1.setContentView(R.layout.messagelayout);
        dialog1.setTitle("");
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        final TextView Head = (TextView) dialog1.findViewById(R.id.msghead);
        final TextView edit = (TextView) dialog1.findViewById(R.id.editnotice);
        final TextView delete = (TextView) dialog1.findViewById(R.id.deletenotice);
        final ImageView cancel = (ImageView) dialog1.findViewById(R.id.clear);

        Typeface face = Typeface.createFromAsset(getAssets(), "Proxima_Nova_Thin.otf");
        Head.setTypeface(face);
        edit.setTypeface(face);
        delete.setTypeface(face);

        cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNote();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Deletedialog();

            }
        });
        dialog1.show();

    }

    /* ------------------------------------Edit note Dialog-------------------------------------*/

    public void editNote() {
        dialog3 = new Dialog(SendnotificationActivity.this);
        dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog3.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog3.setContentView(R.layout.popoutdialog);
        dialog3.setTitle("");
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));

        final TextView HEad = (TextView) dialog3.findViewById(R.id.editn);
        final TextView sub = (TextView) dialog3.findViewById(R.id.txt_name);
        final EditText oldpass = (EditText) dialog3.findViewById(R.id.edittxt_name);
        final TextView Update = (TextView) dialog3.findViewById(R.id.update);
        final TextView Cancel = (TextView) dialog3.findViewById(R.id.cancel);
        final ImageView Clear = (ImageView) dialog3.findViewById(R.id.clear);

        Typeface face = Typeface.createFromAsset(getAssets(), "Proxima_Nova_Thin.otf");
        HEad.setTypeface(face);
        sub.setTypeface(face);
        oldpass.setTypeface(face);
        Update.setTypeface(face);
        Cancel.setTypeface(face);
        oldpass.setText(msgss);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog3.dismiss();

            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Update.setTextColor(R.color.skyblue);
                msgss=oldpass.getText().toString().trim();
                SendNote(msgss);
            }
        });
        dialog3.show();
    }


    private void SendNote(String msgss) {                // edit note api
        user = new userOnlineInfo();
        if (user.isOnline(SendnotificationActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            apiUrl();
            Apicaller.artistAddNote(activity, endPoint, email1, id, msgss, formattedDate, new
                    FutureCallback<AddNoteResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, AddNoteResponseModel result) {
                            if(e!=null)
                            {
                                return;
                            }
                            editNoteData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(SendnotificationActivity.this);
        }
    }

    private void editNoteData(AddNoteResponseModel result) {           /* edit Noted ata*/
        dialog.dismiss();
        dialog1.dismiss();
        dialog3.dismiss();

        try {
            String API_Status = result.getStatus();
            String message = result.getMessage();
            if (API_Status.equals("true")) {
                textSend.setText("");
                showallmessage.setVisibility(View.VISIBLE);
                Notice_Message.setText(msgss);
                SendPushNotificcation();
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialog1.dismiss();
                dialog3.dismiss();
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialog1.dismiss();
                dialog3.dismiss();

            }
        } catch (Exception e) {
            System.out.println(e);
            dialog.dismiss();
            dialog1.dismiss();
            dialog3.dismiss();

        }

    }

    /* ------------------------------------------Delete Note Dialog------------------------------------*/

/*    private void DeleteNote() {
        user = new userOnlineInfo();
        if (user.isOnline(SendnotificationActivity.this)) {
            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String, String> params = new HashMap<>();
            params.put("email", email1);
            params.put("id", id);
            String link = Config.Base_url + "artist/deleteNote";
            new VolleyBase(this).main(params, link, 3);
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(SendnotificationActivity.this);
        }
    }*/
    public void Deletedialog() {
        dialog2 = new Dialog(SendnotificationActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog2.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog2.setContentView(R.layout.deletedialog);
        dialog2.setTitle("");
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));


        final TextView Head = (TextView) dialog2.findViewById(R.id.delhead);
        final TextView Sub = (TextView) dialog2.findViewById(R.id.txt_name);
        final TextView Yes = (TextView) dialog2.findViewById(R.id.yes);
        final TextView No = (TextView) dialog2.findViewById(R.id.no);
        final ImageView Clear = (ImageView) dialog2.findViewById(R.id.clear);

        Typeface face = Typeface.createFromAsset(getAssets(), "Proxima_Nova_Thin.otf");
        Head.setTypeface(face);
        Sub.setTypeface(face);
        Yes.setTypeface(face);
        No.setTypeface(face);
        Clear.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                dialog2.dismiss();
            }
        });
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                DeleteNote();

            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        dialog2.show();

    }

    private void DeleteNote() {

        if (user.isOnline(activity)) {
            dialog = new NewProgressBar(activity);
            dialog.show();
            apiUrl();
            Apicaller.artistDeleteNote(activity, endPointDelete, email1, id, new
                    FutureCallback<DeleteNoteResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, DeleteNoteResponseModel result) {
                            if(e!=null){
                                return;
                            }
                            deleteNoteData(result);
                        }
                    });
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(activity);
        }
    }

    private void deleteNoteData(DeleteNoteResponseModel result) {

        dialog.dismiss();
        dialog1.dismiss();
        dialog2.dismiss();
        try {
            String API_Status = result.getStatus();
            String message = result.getMessage();
            if (API_Status.equals("true")) {
                Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
                showallmessage.setVisibility(View.GONE);
                dialog.dismiss();
                dialog1.dismiss();
                dialog2.dismiss();
            } else {
                Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialog1.dismiss();
                dialog2.dismiss();

            }
        } catch (Exception e) {
            System.out.println(e);
            dialog.dismiss();
            dialog1.dismiss();
            dialog2.dismiss();
        }
    }

    private void SendPushNotificcation() {
        user = new userOnlineInfo();

        if (user.isOnline(SendnotificationActivity.this)) {

            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String, String> params = new HashMap<>();

            params.put("userId", id);

            String link = Config.Base_url + "sendGCMNotification";
            new VolleyBase(this).main(params, link, 1);
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(SendnotificationActivity.this);
        }
    }
    private void getNote() {
        user = new userOnlineInfo();

        if (user.isOnline(SendnotificationActivity.this)) {

            dialog = new NewProgressBar(this);
            dialog.show();
            Map<String, String> params = new HashMap<>();

            params.put("id", id);
            params.put("userType", "2");

            String link = Config.Base_url + "artist/getProfile";
            new VolleyBase(this).main(params, link, 2);
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(SendnotificationActivity.this);
        }
    }







    @Override
    public void success(String Response, int index) {

        if (index == 0) {
            Log.e("sendnote", Response);
            dialog.dismiss();
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");
                String message = jobj.getString("message");

                // Log.e("message",jobj.getString("message"));
                if (API_Status.equals("true")) {

                    showallmessage.setVisibility(View.VISIBLE);
                    Notice_Message.setText(textSend.getText().toString().trim());
                    textSend.setText("");
                    SendPushNotificcation();
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // message(jobj.getString("message"))
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
                dialog2.dismiss();
            }

        }

        if (index == 1) {
            dialog.dismiss();
            Log.e("Received notification", Response);
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");
                // Log.e("message",jobj.getString("message"));
                if (API_Status.equals("true")) {
//                    Intent intent = new Intent(SendnotificationActivity.this, SendnotificationActivity.class);
//                    startActivity(intent);
//                    finish();
                    //  Toast.makeText(this, jobj.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // message(jobj.getString("message"))
                } else {
                    dialog.dismiss();
                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
            }

        }
        if (index == 2) {
            dialog.dismiss();
            Log.e("getartistprofile", Response);
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");
                // Log.e("message",jobj.getString("message"));
                if (API_Status.equals("true")) {
                    JSONObject jo = jobj.getJSONObject("data");
                    msgss=jo.getString("AddNote");
                    pic = jo.getString("imageUrl");
                    if(msgss.equals("")){
                        showallmessage.setVisibility(View.GONE);
                    }
                    else{
                        showallmessage.setVisibility(View.VISIBLE);
                        Notice_Message.setText(jo.getString("AddNote"));
                        Picasso.get().load(jo.getString("imageUrl")).into(profileImagex);
                    }

//                    Intent intent = new Intent(SendnotificationActivity.this, SendnotificationActivity.class);
//                    startActivity(intent);
//                    finish();
                    //  Toast.makeText(this, jobj.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    // message(jobj.getString("message"))
                } else {
                    dialog.dismiss();
                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
            }

        }
        if (index == 3) {
            dialog.dismiss();
            dialog1.dismiss();
            dialog2.dismiss();

            Log.e("Delete_notes", Response);
            try {
                JSONObject jobj = new JSONObject(Response);
                String API_Status = jobj.getString("status");
                String message = jobj.getString("message");
                // Log.e("message",jobj.getString("message"));
                if (API_Status.equals("true")) {
                    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
                    showallmessage.setVisibility(View.GONE);
//                    Notice_Message.setText(jo.getString("AddNote"));
//                    msgss=jo.getString("AddNote");
//                    Intent intent = new Intent(SendnotificationActivity.this, SendnotificationActivity.class);
//                    startActivity(intent);
//                    finish();
                    //  Toast.makeText(this, jobj.getString("message"), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog1.dismiss();
                    dialog2.dismiss();

                    // message(jobj.getString("message"))
                } else {
                    Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    dialog1.dismiss();
                    dialog2.dismiss();

                }
            } catch (Exception e) {
                System.out.println(e);
                dialog.dismiss();
                dialog1.dismiss();
                dialog2.dismiss();
            }
        }

    }

    @Override
    public void error(VolleyError error, int index) {
    }
}
