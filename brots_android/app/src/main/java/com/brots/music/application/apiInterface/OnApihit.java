package com.brots.music.application.apiInterface;


import com.android.volley.VolleyError;

/**
 * Created by Tejinder on 7/22/2016.
 */
public interface OnApihit {
   void success(String Response, int index);
    void error(VolleyError error, int index);
}
