/*
 * *
 *  * Created by Muhammed Ali YUCE (@mayuce on github) on 11/2/18 10:01 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 11/2/18 9:41 PM
 *
 */

package com.androidprojectbase.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.androidprojectbase.R;
import com.androidprojectbase.base.BaseActivity;
import com.androidprojectbase.ui.main.MainActivity;

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }, 1000);
    }
}
