
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase.restApi.listeners;

import android.content.Context;
import android.os.Handler;

import com.androidprojectbase.R;
import com.androidprojectbase.helpers.Utility;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Response;

public abstract class RequestCompletedEventListener<T> implements IRequestCompletedEventListener<T> {

    private Context mContext;
    private boolean mIsShowErrorMessage = false;
    private TaskCompletedEventListener<T> mTaskCompletedEventListener;

    public RequestCompletedEventListener(Context context, TaskCompletedEventListener listener, boolean isShowErrorMessage) {
        mContext = context;
        mIsShowErrorMessage = isShowErrorMessage;
        mTaskCompletedEventListener = listener;
    }

    public void onUnauthenticated(final Response<?> response) {

        mTaskCompletedEventListener.onTaskError(response.code(), response.message(), SweetAlertDialog.ERROR_TYPE, false, null);

        if (mIsShowErrorMessage) {
            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error), response.message(), SweetAlertDialog.ERROR_TYPE);
                }
            });
        }

        mTaskCompletedEventListener.onTaskFinished(true,response.code());
    }

    public void onClientError(final Response<?> response) {

        mTaskCompletedEventListener.onTaskError(response.code(), response.message(), SweetAlertDialog.ERROR_TYPE, false, null);

        if (mIsShowErrorMessage) {
            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error), response.message(), SweetAlertDialog.ERROR_TYPE);
                }
            });
        }

        mTaskCompletedEventListener.onTaskFinished(true,response.code());
    }

    public void onServerError(final Response<?> response) {

        mTaskCompletedEventListener.onTaskError(response.code(), response.message(), SweetAlertDialog.ERROR_TYPE, false, null);

        if (mIsShowErrorMessage) {
            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error), mContext.getString(R.string.cant_reach_server), SweetAlertDialog.ERROR_TYPE);
                }
            });
        }

        mTaskCompletedEventListener.onTaskFinished(true,response.code());
    }

    public void onNetworkError(final IOException e) {
        mTaskCompletedEventListener.onTaskError(e.hashCode(), e.getLocalizedMessage(), SweetAlertDialog.ERROR_TYPE, false, null);
        if (mIsShowErrorMessage) {

            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error), mContext.getString(R.string.check_your_internet_connection), SweetAlertDialog.ERROR_TYPE);
                }
            });
        }

        mTaskCompletedEventListener.onTaskFinished(true,e.hashCode());
    }

    public void onUnexpectedError(final Throwable t) {

        mTaskCompletedEventListener.onTaskError(t.hashCode(), t.getLocalizedMessage(), SweetAlertDialog.ERROR_TYPE, false, null);

        if (mIsShowErrorMessage) {
            Handler handler = new Handler(mContext.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Utility.getInstance(mContext).alertMessageOnMainThread(mContext.getString(R.string.error),mContext.getString(R.string.unexpected_error), SweetAlertDialog.ERROR_TYPE);
                }
            });
        }

        mTaskCompletedEventListener.onTaskFinished(true,t.hashCode());
    }
}