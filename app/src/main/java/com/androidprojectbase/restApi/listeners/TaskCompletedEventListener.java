
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 15:13
 *
 */

package com.androidprojectbase.restApi.listeners;

import android.content.Context;
import android.os.Handler;

import com.androidprojectbase.R;
import com.androidprojectbase.helpers.Utility;
import com.facebook.login.LoginManager;

import cn.pedant.SweetAlert.SweetAlertDialog;


public abstract class TaskCompletedEventListener<T> implements ITaskCompletedEventListener<T> {

    private Context mContext;

    public TaskCompletedEventListener(Context context) {
        mContext = context;
    }

    private void logoutUserAndGetLoginWindow() {
        LoginManager.getInstance().logOut();
    }

    private void onTaskError(final Integer errorCode, final String errorMessage, final int errorType, final boolean isShowErrorMessage) {
        if (errorCode == 4) { // Token Expired
            logoutUserAndGetLoginWindow();
        } else {
            if (isShowErrorMessage) {
                Handler handler = new Handler(mContext.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        String errorMessageDescription;
                        if (errorType == SweetAlertDialog.ERROR_TYPE) {
                            errorMessageDescription = errorMessage;
                            Utility.getInstance(mContext).alertMessageOnMainThread("", errorMessageDescription, SweetAlertDialog.ERROR_TYPE);
                        } else if (errorType == SweetAlertDialog.WARNING_TYPE) {
                            errorMessageDescription = errorMessage;
                            Utility.getInstance(mContext).alertMessageOnMainThread("", errorMessageDescription, SweetAlertDialog.WARNING_TYPE);
                        } else {
                            errorMessageDescription = errorMessage;
                            Utility.getInstance(mContext).alertMessageOnMainThread("", errorMessageDescription, SweetAlertDialog.ERROR_TYPE);
                        }
                    }
                });
            }
        }
    }

    private void onTaskError(final Integer errorCode, final String errorMessage, final boolean isShowErrorMessage) {
            if (isShowErrorMessage) {
                Handler handler = new Handler(mContext.getMainLooper());
                handler.post(new Runnable() {
                    public void run() {
                        String errorMessageDescription;
                            errorMessageDescription = errorMessage;
                            Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error), errorMessageDescription, SweetAlertDialog.ERROR_TYPE);
                    }
                });
            }
    }

    public void onTaskError(final Integer errorCode, final String errorMessage, final int errorType, final boolean isShowErrorMessage, final ITaskRecursiveEventListener listener) {
        onTaskError(errorCode, errorMessage, errorType, isShowErrorMessage);
    }
    public void onTaskError(final Integer errorCode, final String errorMessage, final boolean isShowErrorMessage, final ITaskRecursiveEventListener listener) {
        onTaskError(errorCode, errorMessage, isShowErrorMessage);
    }
}
