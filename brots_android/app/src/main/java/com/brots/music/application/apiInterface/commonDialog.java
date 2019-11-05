package com.brots.music.application.apiInterface;

import android.app.AlertDialog;
import android.content.Context;

public class commonDialog {

	public void dialogbox(Context c) {

	AlertDialog.Builder buildr  = new AlertDialog.Builder(c);
	buildr.setCancelable(true);
	buildr.setMessage("No Internet Connection available");
	 AlertDialog alertDialog = buildr.create();
     alertDialog.show();
     
	}
}
