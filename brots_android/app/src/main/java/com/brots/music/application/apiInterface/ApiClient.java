package com.brots.music.application.apiInterface;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-52-15-167-139.us-east-2.compute.amazonaws.com:9000/v1/")
                    .addConverterFactory(GsonConverterFactory.create())

                    //add this line for rx java
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }


    //get OkHttp instance

    private static OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // builder.interceptors().add(getHeadersForApis());
        builder.interceptors().add(httpLoggingInterceptor);
        builder.readTimeout(90, TimeUnit.SECONDS);
        builder.connectTimeout(90, TimeUnit.SECONDS);
        return builder.build();
    }
}
