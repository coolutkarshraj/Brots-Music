package com.brots.music.application.activity.loginRegistraitionFlow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.AfterSplash.After_Splash;
import com.brots.music.application.activity.afterrSelectUser.After_Select_User;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.model.response.IsUserExistResponseModel;
import com.brots.music.application.model.response.OtpResponseModel;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.util.Font;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.brots.music.application.apiInterface.validations;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.koushikdutta.async.future.FutureCallback;


public class Register_Simple_Screen extends AppCompatActivity implements  LocationListener {
    private static final int RC_SIGN_IN = 1;
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    double mLatitude;
    double mLongitude;
    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    @BindView(R.id.fname)
    EditText fname;
    @BindView(R.id.surname)
    EditText surname;
    @BindView(R.id.dob)
    EditText dob;
    @BindView(R.id.dobnew)
    EditText dobnew;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.city)
    EditText city;
    @BindView(R.id.country)
    EditText country;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.male)
    RadioButton male;
    @BindView(R.id.female)
    RadioButton female;
    @BindView(R.id.checkboxsignup)
    CheckBox checkboxsignup;
    @BindView(R.id.txt_terms)
    TextView txtTerms;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.signinlayout)
    LinearLayout signinlayout;
    @BindView(R.id.tv_already_account)
    LinearLayout tvAlreadyAccount;
    @BindView(R.id.fields_container)
    LinearLayout fieldsContainer;
    @BindView(R.id.cordinate)
    CoordinatorLayout cordinate;
    String gender_value;
    userOnlineInfo user;
    String formattedDate;
    String Otp;
    validations valid;
    boolean b;
    String endpoint;
    NewProgressBar dialog;
    String address, city1, state, country1, postalCode, knownName, usertype;
    CallbackManager callbackManager;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.facebook)
    ImageView Facebook;
    @BindView(R.id.google)
    ImageView Google;
    GoogleSignInClient mGoogleSignInClient;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__simple__screen);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        usertype = intent.getStringExtra("usertype");
        gender_value = "1";
        male.setChecked(true);
        female.setChecked(false);
        Random random = new Random();
        Otp = String.format("%04d", random.nextInt(10000));
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        preferenceManager = new PreferenceManager(Register_Simple_Screen.this);

        if (!isGPS && !isNetwork) {
            Log.e(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.e(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.e(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }
            getLocation();

        }

        Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Register_Simple_Screen.this,Arrays.asList("email", "public_profile"));
            }
        });
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.e("API123", loggedIn + " ??");
                Log.e("loginResult", loginResult + " ??");
                getUserProfile(AccessToken.getCurrentAccessToken());
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                System.out.print("User has canceled");
            }

            @Override
            public void onError(FacebookException error) {
                System.out.print(error);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void  handleSignInResult(Task<GoogleSignInAccount> completetask)
    {
        try {
            GoogleSignInAccount account1 = completetask.getResult(ApiException.class);
            Detail();
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Register_Simple_Screen.this);
        if (acct != null) {

            Detail();

        }
        super.onStart();
    }

    private void Detail() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(Register_Simple_Screen.this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            fname.setText(personName);
            surname.setText(personGivenName);
            email.setText(personEmail);
        }

    }

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.e("TAG", object.toString());

                        try {
                            String name=object.getString("name");
                            Log.e("name",name);
                            String last_name = object.getString("last_name");
                            Log.e("last_name",last_name);
                            String email1 = object.getString("email");
                            Log.e("email1",email1);
                            String id = object.getString("id");
                            Log.e("id",id);
                            try{
                                String birthday=object.getString("birthday");
                                Log.e("bday",birthday);
                                dob.setText(birthday);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                String Location=object.getString("location");
                                Log.e("Location",Location);
                                city.setText(Location);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                String Gender=object.getString("gender");
                                Log.e("Gender",Gender);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            try{
                                String pic=object.getString("picture");
                                String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            fname.setText(name);
                            surname.setText(last_name);

                            email.setText(email1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        // parameters.putString("fields", "first_name,last_name,email,id");
        parameters.putString("fields", "id,name,last_name,first_name,picture,email,gender,location,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged");
        updateUI(location);
    }

    public void sendOtp() {
        user = new userOnlineInfo();
        valid = new validations(cordinate);
        b = valid.registervalidate(Register_Simple_Screen.this, fname.getText().toString().trim(), surname.getText().toString().trim(), dob.getText().toString().trim(), email.getText().toString().trim(), password.getText().toString().trim(), city.getText().toString().trim(), country.getText().toString().trim(), gender_value);
        if (b) {
            if (user.isOnline(this)) {
                dialog = new NewProgressBar(this);
                dialog.show();
                setEndPoint();
                String name = fname.getText().toString().trim() + " " + surname.getText().toString().trim();
                Apicaller.sendRegistrationOtp(Register_Simple_Screen.this, endpoint,name,Otp,email.getText().toString().trim(),
                        new FutureCallback<OtpResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, OtpResponseModel result) {
                                if (e != null) {
                                    Toast.makeText(Register_Simple_Screen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    return;
                                }
                                if(result.getStatus().equals("false")){
                                    Toast.makeText(Register_Simple_Screen.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    return;

                                }else {
                                    if(result.getStatus().equals("true")){
                                        openOtpScreen();
                                    }else {
                                        Toast.makeText(Register_Simple_Screen.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }


                            }
                        }
                );
            }else {
                Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please Enter Valid Otp", Toast.LENGTH_SHORT).show();
        }
    }

    private void openOtpScreen() {
        Intent intent =  new Intent(Register_Simple_Screen.this,Otp_Registeration.class);
        intent.putExtra("fname", fname.getText().toString());
        intent.putExtra("lname", surname.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("gender", gender_value);
        intent.putExtra("city", city.getText().toString().trim());
        intent.putExtra("usertype", usertype);
        intent.putExtra("country", country.getText().toString().trim());
        intent.putExtra("address", mLatitude + "," + mLongitude);
        //intent.putExtra("address",address);
        Toast.makeText(this, "Otp Send Successfully", Toast.LENGTH_SHORT).show();
        intent.putExtra("dob", dobnew.getText().toString());
        intent.putExtra("password", password.getText().toString());
        intent.putExtra("otp", Otp);
        intent.putExtra("registratioDate", formattedDate + " " + "00:00:00");
        startActivity(intent);

    }

    private void setEndPoint() {
        if(usertype.equals("2")){
            endpoint=Config.Base_url +"artist/registrationVerify";
        }else if(usertype.equals("1")){
            endpoint=Config.Base_url +"user/registrationVerify";
        }

        if(endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/user/registrationVerify")
        ||endpoint.equals("http://ec2-54-184-212-143.us-west-2.compute.amazonaws.com:9000/v1/artist/registrationVerify")){

        }else {
          usertype = preferenceManager.getString(PreferenceManager.userType);
            if(usertype.equals("2")){
                endpoint=Config.Base_url +"artist/registrationVerify";
            }else if(usertype.equals("1")){
                endpoint=Config.Base_url +"user/registrationVerify";
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.e(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.e(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.e(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.e(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.e(TAG, provider);
            Log.e("location", provider);
            Log.e(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.e(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.e(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Register_Simple_Screen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void updateUI(Location loc) {
        Log.e(TAG, "updateUI");
        Log.e("Latitude", "" + Double.toString(loc.getLatitude()));
        Log.e("Longitude", "" + Double.toString(loc.getLongitude()));
        Log.e("Time", "" + DateFormat.getTimeInstance().format(loc.getTime()));
        Log.e("Date", "" + DateFormat.getDateInstance().format(loc.getTime()));
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        // SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c);
        Log.e("Date", "" + formattedDate);

        mLatitude = loc.getLatitude();
        mLongitude = loc.getLongitude();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {

            addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city1 = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country1 = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();
            city.setText(city1);
            country.setText(country1);

            Log.e("address", address);
            Log.e("city", city1);
            Log.e("state", state);
            Log.e("country", country1);
            Log.e("postalCode", postalCode);
            Log.e("knownName", knownName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }




    @OnClick({R.id.male,R.id.tv_login, R.id.female, R.id.checkboxsignup, R.id.dob, R.id.submit, R.id.tv_already_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.male:
                gender_value = "1";
                male.setChecked(true);
                female.setChecked(false);
                break;
            case R.id.female:
                gender_value = "2";
                female.setChecked(true);
                male.setChecked(false);
                break;
            case R.id.checkboxsignup:
                break;
            case R.id.submit:
                submit.setBackground(getDrawable(R.drawable.button_bg_rounded_corners));
                if(checkboxsignup.isChecked()){
                  sendOtp();
                }else {
                    Toast.makeText(this, "Before Registration Please  Accept Terms and Condition ", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.dob:
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
                break;
            case R.id.tv_already_account:
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this, After_Splash.class);
                startActivity(intent);
                break;

        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
            dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
            ;
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String formattedDate;

            TextView dob = (TextView) getActivity().findViewById(R.id.dob);
            TextView dobnew = (TextView) getActivity().findViewById(R.id.dobnew);

            Calendar cal = Calendar.getInstance();

            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate1 = format.format(chosenDate);
            String formattedDate11 = format1.format(chosenDate);


            dob.setText(formattedDate1);
            dobnew.setText(formattedDate11);
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
    public void onBackPressed() {
        Intent signup=new Intent(Register_Simple_Screen.this, After_Select_User.class);
        signup.putExtra("usertype",usertype);
        startActivity(signup);
        finish();
    }
}
