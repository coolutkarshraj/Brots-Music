package com.brots.music.application.apiInterface;




import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Varinder on 7/22/2016.
 */
public class VolleyBase {
    String request;
    OnApihit api;
    public VolleyBase(OnApihit api)
    {
        this.api=api;
    }

    public String main(final Map<String, String> params, String link, final int index)
    {

        StringRequest postRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        request=response;
                        api.success(response, index);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        api.error(error, index);
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        AppController.getInstance().addToRequestQueue(postRequest);
       return request;
    }
}
