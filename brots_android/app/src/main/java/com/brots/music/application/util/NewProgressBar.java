package com.brots.music.application.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.brots.music.application.R;


/**
 * Created by Arvind on 04-Apr-17.
 */

public class NewProgressBar {
    Context con;
    Dialog dialog;
    public NewProgressBar(Context con  )
    {
        this.con  =con;
    }
    public void show ()
    {
            dialog = new Dialog(con);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.progress_layout);
            dialog.show();

        }

        public void dismiss()
        {

            dialog.dismiss();
        }


}
