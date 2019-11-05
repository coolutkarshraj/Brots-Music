package com.brots.music.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.brots.music.application.activity.ArtistUserProfile.ArtistProfileWithTopThreeSong;
import com.brots.music.application.localStorage.PreferenceManager;
import com.brots.music.application.util.Font;
import com.brots.music.application.util.NewProgressBar;
import com.brots.music.application.util.userOnlineInfo;
import com.bumptech.glide.Glide;
import com.brots.music.application.fragment.ExploreArtist;
import com.brots.music.application.R;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by home on 8/4/2017.
 */

public class MainCategoryListAdpter extends RecyclerView.Adapter<MainCategoryListAdpter.ItemRowHolder> {

    private Context mContext;
    int check;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ExploreArtist myFragment;
    userOnlineInfo user;
    NewProgressBar dialog;
    PreferenceManager sharedPreferences;
    String name, id, deviceToken, password1, userType, email1, ArtistId;
    FragmentManager childFragmentManager;

    public MainCategoryListAdpter(Context context, ArrayList<HashMap<String, String>> list) {
        this.mContext = context;
        this.check = check;
        this.list = list;
        this.childFragmentManager = childFragmentManager;

    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false);
        ItemRowHolder mh = new ItemRowHolder(v);
        Font fontChanger = new Font(mContext.getAssets(), "Proxima_Nova_Thin.otf");
        fontChanger.replaceFonts((ViewGroup) v);
        sharedPreferences =new PreferenceManager(mContext);
        name = sharedPreferences.getString(PreferenceManager.name);
        id = sharedPreferences.getString(PreferenceManager.id);
        deviceToken = sharedPreferences.getString(PreferenceManager.deviceToken);
        password1 = sharedPreferences.getString(PreferenceManager.password);
        userType = sharedPreferences.getString(PreferenceManager.userType);
        email1 = sharedPreferences.getString(PreferenceManager.email);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {
        itemRowHolder.text1.setText(list.get(i).get("Name"));
        Glide.with(mContext).load(list.get(i).get("imageUrl")).into(itemRowHolder.package_image);

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView text1;
        protected CircleImageView package_image;
        LinearLayout layout, click,ll_artist;

        Button connect;


        public ItemRowHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layout);
            ll_artist = (LinearLayout) view.findViewById(R.id.ll_artist);
            click = (LinearLayout) view.findViewById(R.id.click);
            connect = (Button) view.findViewById(R.id.connect);
            text1 = (TextView) view.findViewById(R.id.name);
            package_image = (CircleImageView) view.findViewById(R.id.image);

            ll_artist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      Intent intent =  new Intent(mContext, ArtistProfileWithTopThreeSong.class);
                      intent.putExtra("artistId",list.get(getAdapterPosition()).get("id"));
                      mContext.startActivity(intent);

                }
            });
        }
    }
}
