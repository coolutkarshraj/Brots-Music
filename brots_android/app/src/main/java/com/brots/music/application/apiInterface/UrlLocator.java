package com.brots.music.application.apiInterface;

import com.brots.music.application.Config;

/**
 * Created by team edealsUtkarsh on 27-02-2018.
 */

public class UrlLocator {
    private static String tempIP = "";

    public static String getBaseIP() {
        return Config.Baseurl;

    }

    public static String getFinalUrl(String url) {
        String ip = getBaseIP();
        return "http://" + ip + ":9000/v1/" + url;
    }

}
