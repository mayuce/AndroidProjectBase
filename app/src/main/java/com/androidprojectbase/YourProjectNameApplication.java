
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.util.Base64;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class YourProjectNameApplication extends Application {

    public static final String TAG = YourProjectNameApplication.class.getSimpleName();
    public static final String APPLICATION_CONTEXT = "applicationcontext";
    private static HashMap<String, Object> mGlobalVariables = new HashMap<>();


    public static void initImageLoader(Context context) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheOnDisk(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();

        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.defaultDisplayImageOptions(options);
        config.writeDebugLogs();
        ImageLoader.getInstance().init(config.build());
    }

    public static void putGlobal(String key, Object obj) {
        mGlobalVariables.put(key, obj);
    }

    public static Object getGlobal(String key) {
        return mGlobalVariables.get(key);
    }

    public static Context getAppContext() {
        Context ctx = null;
        Object obj = getGlobal(APPLICATION_CONTEXT);
        if (obj != null) {
            if (obj instanceof Context) {
                ctx = (Context) obj;
            }
        }
        return ctx;
    }

    private static void setAppContext(Context appContext) {
        putGlobal(APPLICATION_CONTEXT, appContext);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {

        super.onCreate();

        setAppContext(getApplicationContext());

        initImageLoader(getApplicationContext());

        //  initFacebookSDK();

        Fresco.initialize(this);
        //  printFacebookKeyHash();

    }

    private void initFacebookSDK() {
      //  FacebookSdk.sdkInitialize(getApplicationContext()); -- Depracated / No need anymore
        AppEventsLogger.activateApp(this);
    }

    public void printFacebookKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.servisimapp", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("hash key", new String(Base64.encode(md.digest(), 0)));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

}