
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 01.02.2018 17:16
 *
 */

package com.androidprojectbase.base;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.androidprojectbase.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public abstract class BaseFragment extends Fragment {

    public static final String TAG = BaseFragment.class.getSimpleName();
    protected DisplayImageOptions mDisplayImageOptions;
    protected Animation animationFadeIn,animationFadeOut;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onUserVisibleChanged(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        if (activity != null && isAdded()) {
            mDisplayImageOptions = new DisplayImageOptions.Builder()
                    .displayer(new FadeInBitmapDisplayer(500))
                    .cacheInMemory(true)
                    .cacheOnDisk(false)
                    .considerExifParams(true)
                    .build();

            animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            animationFadeIn.setDuration(1000);
            animationFadeOut= AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            animationFadeOut.setDuration(1000);
            initUI(savedInstanceState);
        }
    }

    protected void onUserVisibleChanged(boolean visible) {

    }

    public void animateViews(Techniques technique, View[] view)
    {
        int duration=400;
        for (int i = 0; i < view.length; i++) {
            if (view[i]!=null)
                YoYo.with(technique).duration(duration).repeat(0).playOn(view[i]);
            duration+=200;
        }
    }
    @TargetApi(23)
    protected void askPermissions(String[] permissions, int requestCode) {
        requestPermissions(permissions, requestCode);
    }
    protected void initUI(Bundle savedInstanceState){
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
}
