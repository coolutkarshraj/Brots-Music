package com.brots.music.application.activity.ExplorerArtistActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.R;
import com.brots.music.application.Config;
import com.brots.music.application.model.responseData.ArtistSongList;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.bumptech.glide.Glide;
import com.brots.music.application.adapter.SongListAdapter;
import com.brots.music.application.apiInterface.OnApihit;
import com.brots.music.application.apiInterface.VolleyBase;
import com.brots.music.application.apiInterface.commonDialog;
import com.brots.music.application.util.userOnlineInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreArtistActivity extends AppCompatActivity implements OnApihit {
    RecyclerView recyclerView;
    SongListAdapter adapter;
    List<ArtistSongList> item;
    SearchView searchVieww;
    userOnlineInfo user;
    NewProgressBar dialog;
    PreferenceManager preferenceManager;
    String name, userType, id, deviceToken, password1, email1;
    String Artistid, name11, imageUrl;
    @BindView(R.id.img)
    CircleImageView img;
    @BindView(R.id.name)
    TextView name1;
    @BindView(R.id.city)
    TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exploreartist);
        ButterKnife.bind(this);
        name = preferenceManager.getString(PreferenceManager.name);
        userType = preferenceManager.getString(PreferenceManager.userType);
        id = preferenceManager.getString(PreferenceManager.id);
        deviceToken = preferenceManager.getString(PreferenceManager.deviceToken);
        password1 = preferenceManager.getString(PreferenceManager.password);
        email1 = preferenceManager.getString(PreferenceManager.email);

        Font font = new Font(getAssets(), "Proxima_Nova_Thin.otf");
        font.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        Intent intent = getIntent();
        Artistid = intent.getStringExtra("id");
        imageUrl = intent.getStringExtra("imageUrl");
        name11 = intent.getStringExtra("name");
        name1.setText(name11);
        Glide.with(this).load(imageUrl).into(img);
        artistsong();
        item = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        searchVieww = (SearchView) findViewById(R.id.serach);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    private void artistsong() {
        user = new userOnlineInfo();
        if (user.isOnline(this)) {

            Map<String, String> params = new HashMap<>();
            params.put("artistId",Artistid);
            params.put("userId", id);
            String link = Config.Base_url + "artist/getSingleArtistAllSong";
            new VolleyBase(this).main(params, link, 0);
        } else {
            commonDialog comdialog = new commonDialog();
            comdialog.dialogbox(this);
        }
    }

    @Override
    public void success(String Response, int index) {
        Log.e("artistget", "song:" + Response);

        Log.e("Gesponse", Response);
        //  dialog2.dismiss();
        item.clear();
        try {
            JSONObject jobj = new JSONObject(Response);
            String API_Status = jobj.getString("status");
            if (API_Status.equals("true")) {
                //  dialog2.dismiss();
                JSONArray result = jobj.getJSONArray("data");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject childobject = result.getJSONObject(i);
                    ArtistSongList model = new ArtistSongList();
                    model.setid(childobject.getString("id"));
                    model.setSong(childobject.getString("SongName"));
                    model.setArtist(childobject.getString("ArtistName"));
                    model.setArtistid(childobject.getString("ArtistId"));
                    model.setDuration(childobject.getString("Duration"));
                    model.setFullSongUrl(childobject.getString("FullSongUrl"));
                    model.setUrl(childobject.getString("ImageUrl"));
                    model.setMeomry(childobject.getString("Memory"));
                    model.setAlbumName(childobject.getString("AlbumName"));
                    model.settotallike(childobject.getString("Like"));
                    model.settotaldislike(childobject.getString("DisLike"));
                    model.setCoverimage(childobject.getString("CoverImage"));
                    JSONArray result1 = childobject.getJSONArray("likedUser");
                    if(result1.length()!=0){
                        for (int j = 0; j < result1.length(); j++) {
                            JSONObject childobject1 = result1.getJSONObject(j);
                            JSONObject childobject2 = childobject1.getJSONObject("SongThrough");
                            model.setLikeSong(childobject2.getString("Like"));
                            model.setDislikeSong(childobject2.getString("DisLike"));
                            model.setSongid(childobject2.getString("songId"));
                        }

                    }else{
                        model.setLikeSong("0");
                        model.setDislikeSong("0");
                    }

                    JSONArray result2 = childobject.getJSONArray("SaveAdminUser");
                    if(result2.length()!=0) {
                        for (int k = 0; k < result2.length(); k++) {
                            JSONObject childobject3 = result2.getJSONObject(k);
                            JSONObject childobject4 = childobject3.getJSONObject("AdminSaveSongThrough");
                            model.setIssavedSong(childobject4.getString("isSaved"));
                        }
                    }else{
                        model.setIssavedSong("0");
                    }
                    item.add(model);
                }

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            } else {
                // dialog2.dismiss();

            }


        } catch (Exception e) {
            System.out.println(e);
        }


    }

    @Override
    public void error(VolleyError error, int index) {

    }
}
