
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 12.02.2018 11:07
 *
 */

package com.androidprojectbase.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androidprojectbase.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BaseActivity extends AppCompatActivity {
    protected Toolbar mActionBarToolbar;
    protected DisplayImageOptions mDisplayImageOptions;
    protected Animation animationFadeIn,animationFadeOut;
    protected BaseActivity parentAcitivty;
    private String TAG = BaseActivity.class.getSimpleName();

    private static Intent getIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentAcitivty=this;
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        mDisplayImageOptions = new DisplayImageOptions.Builder()
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .build();

        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeIn.setDuration(1000);
        animationFadeOut= AnimationUtils.loadAnimation(this, R.anim.fade_out);
        animationFadeOut.setDuration(1000);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = findViewById(R.id.MainToolbar);
            if (mActionBarToolbar != null) {
                mActionBarToolbar.setTitle(getString(R.string.empty));
                mActionBarToolbar.setNavigationContentDescription(getResources().getString(R.string.app_name));
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    protected void initActionBarToolbar() {
        mActionBarToolbar = getActionBarToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarToolbarBackPressed();
            }
        });
    }

    protected void initActionBarToolbarBack() {
        mActionBarToolbar = getActionBarToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionBarToolbarBackPressed();
            }
        });
    }

    protected void setVisibility(View view, int visibility)
    {
        if (view!=null)
        {
            view.setVisibility(visibility);
        }
    }

    protected void setText(TextView view, String data)
    {
        if (view!=null)
        {
            if (data!=null)
                view.setText(data);
        }
    }

    protected void setText(TextView view, int data)
    {
        if (view!=null)
        {
            view.setText(String.valueOf(data));
        }
    }

    protected void setText(TextView view, Object data)
    {
        if (view!=null)
        {
            if (data!=null)
                view.setText(String.valueOf(data));
        }
    }

    protected void onActionBarToolbarBackPressed() {
        finish();
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions(String[] permissions) { // "android.permission.WRITE_EXTERNAL_STORAGE"
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
}
