
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase.helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

import com.androidprojectbase.R;
import com.androidprojectbase.base.BaseFragment;

public class ActivityNavigationHelper extends AsyncTask<Void, Void, Void> {

    private Activity mContext;
    private BaseFragment mContextFragment;
    private Intent mIntent;
    private int mRequestCode;
    private boolean mIsActivityFinish = false;
    private boolean mIsActivityResult = false;
    //private SweetAlertDialog mSweetAlertDialog;


    public ActivityNavigationHelper(Activity context, Intent intent, boolean isFinishActivity) {
        this.mContext = context;
        this.mIntent = intent;
        this.mIsActivityFinish = isFinishActivity;
    }

    public ActivityNavigationHelper(Activity context, Intent intent, boolean isFinishActivity, boolean isHasActivityResult, int requestCode) {
        this.mContext = context;
        this.mIntent = intent;
        this.mIsActivityResult = isHasActivityResult;
        this.mIsActivityFinish = isFinishActivity;
        this.mRequestCode = requestCode;
    }

    public ActivityNavigationHelper(Activity context, Intent intent, boolean isFinishActivity, boolean isHasActivityResult, int requestCode, BaseFragment fragment) {
        this.mContext = context;
        this.mContextFragment = fragment;
        this.mIntent = intent;
        this.mIsActivityResult = isHasActivityResult;
        this.mIsActivityFinish = isFinishActivity;
        this.mRequestCode = requestCode;
    }

    @Override
    protected void onPreExecute() {
        /*
        Handler handler =  new Handler(mContext.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                mSweetAlertDialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
                mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mSweetAlertDialog.setTitleText(mContext.getString(R.string.yukleniyor));
                mSweetAlertDialog.setContentText(mContext.getString(R.string.lutfen_bekleyin));
                mSweetAlertDialog.setCancelable(false);
                mSweetAlertDialog.show();
            }
        });
        */
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //SystemClock.sleep(500);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Handler handler = new Handler(mContext.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                if (mIsActivityResult) {
                    if (mContextFragment != null) {
                        mContextFragment.startActivityForResult(mIntent, mRequestCode);
                    } else {
                        mContext.startActivityForResult(mIntent, mRequestCode);
                    }

                    mContext.overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                } else {
                    mContext.startActivity(mIntent);
                    mContext.overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                }

                if (mIsActivityFinish) {
                    mContext.finish();
                    mContext.overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
                }
            }
        });
    }
}