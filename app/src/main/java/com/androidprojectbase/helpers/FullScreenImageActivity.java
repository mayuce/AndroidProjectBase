/*
 * *
 *  * Created by Muhammed Ali YUCE (@mayuce on github) on 11/2/18 10:01 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 11/2/18 9:55 PM
 *
 */

package com.androidprojectbase.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.androidprojectbase.Constants;
import com.androidprojectbase.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import ooo.oxo.library.widget.PullBackLayout;

/**
 * Created by tuzlu on 29.03.2018.
 */

public class FullScreenImageActivity extends AppCompatActivity implements PullBackLayout.Callback {
    private PhotoView mImage;
    private RelativeLayout mPnlContainer;
    private int mType;
    private String mURL;
    protected DisplayImageOptions mDisplayImageOptions;
    private PullBackLayout puller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreenimageview);
        mImage = findViewById(R.id.image);
        mPnlContainer = findViewById(R.id.PnlContainer);
        puller=findViewById(R.id.puller);
        Bundle data = getIntent().getExtras();
        Utility.getInstance(this).transparentStatusBar(this);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .resetViewBeforeLoading(true)
                .build();
        mType = data.getInt(Constants.IMAGE_TYPE, 0);
        if (mType == 0) {
            mURL = data.getString(Constants.URL, "");
            if (mURL.toLowerCase().contains("http")) {
                ImageLoader.getInstance().displayImage(mURL, mImage, mDisplayImageOptions, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        mPnlContainer.setBackgroundColor(loadedImage.getPixel(loadedImage.getWidth() / 2, loadedImage.getHeight() / 2));
                        //changeStatusBarColor(loadedImage.getPixel(loadedImage.getWidth()/2,loadedImage.getHeight()/2));
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                    }
                });
            }
            else
            {
                byte[] decodedString = Base64.decode(mURL, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mImage.setImageBitmap(decodedByte);
                mPnlContainer.setBackgroundColor(decodedByte.getPixel(decodedByte.getWidth() / 2, decodedByte.getHeight() / 2));
            }
        }
        puller.setCallback(this);
    }

    @Override
    public void onBackPressed() {
        if (mImage.getScale()!=1f)
        {
            mImage.setScale(1f);
        }
        else
            super.onBackPressed();
    }

    @Override
    public void onPullStart() {
    }

    @Override
    public void onPull(float progress) {
        mPnlContainer.setAlpha(1-(progress));
    }

    @Override
    public void onPullCancel() {
        mPnlContainer.setAlpha(1);
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    private void changeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

}