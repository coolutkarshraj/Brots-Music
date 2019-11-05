package com.brots.music.application.util;



import android.content.Context;
import android.net.ConnectivityManager;

public class userOnlineInfo {

	public boolean isOnline(Context c) {
	     ConnectivityManager cm =
	         (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

	     return cm.getActiveNetworkInfo() != null && 
	        cm.getActiveNetworkInfo().isConnectedOrConnecting();
	 }
}
