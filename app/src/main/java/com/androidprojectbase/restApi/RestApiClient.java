
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 12:02
 *
 */

package com.androidprojectbase.restApi;

import com.androidprojectbase.BuildConfig;
import com.androidprojectbase.Constants;
import com.facebook.login.LoginManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {

    private static RestApiClient instance;
    private static RestApi restApiInstance;

    static {
        createRestApiClient();
    }

    private RestApiClient() {
    }

    public static synchronized RestApiClient getInstance() {
        if (instance == null) {
            synchronized (LoginManager.class) {
                if (instance == null) {
                    instance = new RestApiClient();
                }
            }
        }
        return instance;
    }

    public static synchronized RestApi getRestApiClient() {
        return restApiInstance;
    }

    private static void createRestApiClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS);


        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }

        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.ServiceURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new RestApiCaller.ErrorHandlingCallAdapterFactory())
                .build();

        restApiInstance = retrofit.create(RestApi.class);
    }
}
