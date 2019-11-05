package com.brots.music.application.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brots.music.application.Apicaller;
import com.brots.music.application.activity.HomeActivity.MainActivityTabs;
import com.brots.music.application.model.response.GetArtistOnBehalfLikeDisLikeResponseModel;
import com.brots.music.application.model.response.GetUserProfileResponseModel;
import com.brots.music.application.localStorage.PreferenceManager;
import com.bumptech.glide.Glide;
import com.brots.music.application.adapter.MainCategoryListAdpter;
import com.brots.music.application.Config;
import com.brots.music.application.activity.Profile.Edit_Profile;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.R;
import com.brots.music.application.activity.remberScreen.Remember_Screen;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView crew, editProfile;
    Button Logout, followers;
    userOnlineInfo user;
    NewProgressBar dialog1;
    PreferenceManager preferenceManager;
    String name, id, deviceToken, password1, userType, email1, endPoint, endPoint1, savedSongMemory, InstaMixMemory, img;
    @BindView(R.id.name)
    TextView name1;
    @BindView(R.id.about)
    TextView About;
    @BindView(R.id.imageshow)
    CircleImageView imageshow;
    Unbinder unbinder;
    Dialog dialog2;
    RecyclerView guide_list, guide_list1, guide_list2;
    MainCategoryListAdpter adapter, adapter1;
    ArrayList<HashMap<String, String>> mywrite_list = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> mywrite_list1 = new ArrayList<HashMap<String, String>>();
    String R_name, R_email, R_profilepic, R_usertyper;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, v);
        intializeview(v);
        getArtistBehalfLikeDislike();
        return v;
    }

    private void intializeview(View v) {
        Font fontChanger = new Font(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        user = new userOnlineInfo();
        preferenceManager = new PreferenceManager(getActivity());
        name = preferenceManager.getString(PreferenceManager.name);
        id = preferenceManager.getString(PreferenceManager.id);
        deviceToken = preferenceManager.getString(PreferenceManager.deviceToken);
        password1 = preferenceManager.getString(PreferenceManager.password);
        userType = preferenceManager.getString(PreferenceManager.userType);
        email1 = preferenceManager.getString(PreferenceManager.email);
        savedSongMemory = preferenceManager.getString(PreferenceManager.savedSongMemory);
        InstaMixMemory = preferenceManager.getString(PreferenceManager.InstaMixMemory);
        try {
            ProfileViewUser();
        } catch (Exception e) {
            e.printStackTrace();
        }


        followers = (Button) v.findViewById(R.id.followers);
        crew = (TextView) v.findViewById(R.id.crew);
        Logout = (Button) v.findViewById(R.id.logout);
        editProfile = (TextView) v.findViewById(R.id.editprofile);
        guide_list = (RecyclerView) v.findViewById(R.id.guide_list);
        guide_list1 = (RecyclerView) v.findViewById(R.id.guide_list1);
        guide_list2 = (RecyclerView) v.findViewById(R.id.guide_list2);
        Logout.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        followers.setOnClickListener(this);
        crew.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout) {
            Logout();
        }
        if (v.getId() == R.id.editprofile) {
            startActivity(new Intent(getActivity(), Edit_Profile.class));
        }
        if (v.getId() == R.id.crew) {
//            Intent intent = new Intent(getActivity(),Crew_Activity.class);
//            getActivity().startActivity(intent);
//            getActivity().finish();
            Toast.makeText(getActivity(), "At this time This Feature is under Maintainance we are comming soon ", Toast.LENGTH_SHORT).show();
        }

    }

    private void ProfileViewUser() {         // Get User Profile
        try {
            if (user.isOnline(getActivity())) {
                setEndPoint();
                Apicaller.userGetProfile(getActivity(), endPoint, id, userType, new
                        FutureCallback<GetUserProfileResponseModel>() {
                            @Override
                            public void onCompleted(Exception e, GetUserProfileResponseModel result) {
                                if (e != null) {
                                    return;
                                }

                                setData(result);
                            }
                        });
            } else {
                commonDialog comdialog = new commonDialog();
                comdialog.dialogbox(getActivity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEndPoint() {
        endPoint = Config.Url.getProfile;
    }

    private void setData(GetUserProfileResponseModel result) {        //Get data set Into Views
        R_name = result.getData().getFirstName();
        R_email = result.getData().getEmail();
        R_usertyper = result.getData().getUserType();
        R_profilepic = result.getData().getImageUrl();
        name1.setText(result.getData().getFirstName() + " " + result.getData().getLastName());
        About.setText(result.getData().getCity() + " " + result.getData().getCountry());
        String gender = String.valueOf(result.getData().getGender());
        img = result.getData().getImageUrl();
        if (img.equals("null")) {
            if (gender.equals("1")) {
                Glide.with(getActivity()).load(R.drawable.man).into(imageshow);
            } else if (gender.equals("2")) {
                Glide.with(getActivity()).load(R.drawable.girl).into(imageshow);
            }
        } else {
            Glide.with(getActivity()).load(result.getData().getImageUrl()).into(imageshow);
        }
    }

    private void getArtistBehalfLikeDislike() {        // GetArtistBehalfofLike & Dislike
        if (user.isOnline(getActivity())) {
            dialog1 = new NewProgressBar(getActivity());
            dialog1.show();
            setEndPointartistget();
            Apicaller.artistGetBehalfofDislikeLike(getActivity(), endPoint1, new
                    FutureCallback<GetArtistOnBehalfLikeDisLikeResponseModel>() {
                        @Override
                        public void onCompleted(Exception e, GetArtistOnBehalfLikeDisLikeResponseModel result) {

                            if (e != null) {
                                return;
                            }

                            setartistData(result);
                        }
                    });


        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(getActivity());
        }
    }


    private void setEndPointartistget() {
        endPoint1 = Config.Url.getArtistOnBehalfLikeDislike;
    }

    private void setartistData(GetArtistOnBehalfLikeDisLikeResponseModel result) {

        dialog1.dismiss();
        for (int i = 0; i < result.getLikedArtistMost().size(); i++)   // for Liked Most Song Artist
        {
            HashMap<String, String> list;
            list = new HashMap<String, String>();
            list.put("id", String.valueOf(result.getLikedArtistMost().get(i).getId()));
            list.put("Name", result.getLikedArtistMost().get(i).getName());
            list.put("Email", result.getLikedArtistMost().get(i).getEmail());
            list.put("Password", result.getLikedArtistMost().get(i).getPassword());
            list.put("imageUrl", result.getLikedArtistMost().get(i).getImageUrl());
            mywrite_list.add(list);
        }

        adapter = new MainCategoryListAdpter(getActivity(), mywrite_list);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        guide_list.setLayoutManager(manager);
        guide_list.addItemDecoration(new ProfileFragment.SpacesItemDecoration(2));
        guide_list.setAdapter(adapter);
        guide_list.setNestedScrollingEnabled(true);
        guide_list.setHasFixedSize(true);

        for (int i = 0; i < result.getDisLikeArtistMost().size(); i++)      // for  Disliked Most Song Artist
        {

            HashMap<String, String> list;
            list = new HashMap<String, String>();
            list.put("id", String.valueOf(result.getDisLikeArtistMost().get(i).getId()));
            list.put("Name", result.getDisLikeArtistMost().get(i).getName());
            list.put("Email", result.getDisLikeArtistMost().get(i).getEmail());
            list.put("Password", result.getDisLikeArtistMost().get(i).getPassword());
            list.put("imageUrl", result.getDisLikeArtistMost().get(i).getImageUrl());
            mywrite_list1.add(list);
        }

        adapter1 = new MainCategoryListAdpter(getActivity(), mywrite_list1);
        GridLayoutManager manager1 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        guide_list1.setLayoutManager(manager1);
        guide_list1.addItemDecoration(new ProfileFragment.SpacesItemDecoration(2));
        guide_list1.setAdapter(adapter1);
        guide_list1.setNestedScrollingEnabled(true);
        guide_list1.setHasFixedSize(true);

        GridLayoutManager manager2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        guide_list2.setLayoutManager(manager2);
        guide_list2.addItemDecoration(new ProfileFragment.SpacesItemDecoration(2));
        guide_list2.setAdapter(adapter);
        guide_list2.setNestedScrollingEnabled(true);
        guide_list2.setHasFixedSize(true);

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int mSpacing;

        public SpacesItemDecoration(int spacing) {
            mSpacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            outRect.left = mSpacing;
            outRect.top = mSpacing;
            outRect.right = mSpacing;
            outRect.bottom = mSpacing;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void Logout() {
        dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog2.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog2.setContentView(R.layout.logoutdialog);
        dialog2.setTitle("");
        final TextView Yes = (TextView) dialog2.findViewById(R.id.yes);
        final TextView No = (TextView) dialog2.findViewById(R.id.no);
        final ImageView Clear = (ImageView) dialog2.findViewById(R.id.clear);
        final TextView Logout = (TextView) dialog2.findViewById(R.id.textlogout);
        final TextView textView = (TextView) dialog2.findViewById(R.id.txt_name);
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "Proxima_Nova_Thin.otf");
        Yes.setTypeface(face);
        No.setTypeface(face);
        Logout.setTypeface(face);
        textView.setTypeface(face);

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
                preferenceManager.putString(PreferenceManager.id, "");
                preferenceManager.putString(PreferenceManager.savedSongMemory, "");
                preferenceManager.putString(PreferenceManager.InstaMixMemory, "");
                preferenceManager.putString(PreferenceManager.deviceToken, "");
                preferenceManager.clearAll();
                Intent logout_intent1 = new Intent(getActivity(), Remember_Screen.class);
                logout_intent1.putExtra("name", R_name);
                logout_intent1.putExtra("email", R_email);
                logout_intent1.putExtra("pic", R_profilepic);
                logout_intent1.putExtra("usertype", R_usertyper);
                logout_intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(logout_intent1);
                ((Activity) getActivity()).finish();
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
