
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 07.02.2018 13:11
 *
 */

package com.androidprojectbase.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.androidprojectbase.Constants;
import com.androidprojectbase.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.ByteArrayOutputStream;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import pl.aprilapps.switcher.Switcher;

public class Utility {

    private static Context mContext;
    private static Utility ourInstance = new Utility();
    private SweetAlertDialog mDialog;
    private Utility() {

    }

    public static Utility getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    public void copyToClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Kopyala", text);
        clipboard.setPrimaryClip(clip);
    }

    public void alertMessageOnMainThread(final String title, final String description, final int errorType) {
        try
        {
            hideLoading();
            mDialog=new SweetAlertDialog((Activity) mContext,errorType);
            mDialog.setCancelable(true);
            mDialog.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public float convertPixelsToDp(int px) {
        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public void showFullScreenImage(final Context activity, final String imageURL) {
        if (imageURL != null) {
            if (!imageURL.equals("")) {
                Intent fullScreenImage=new Intent(mContext, FullScreenImageActivity.class);
                fullScreenImage.putExtra(Constants.IMAGE_TYPE,0);
                fullScreenImage.putExtra(Constants.URL,imageURL);
                new ActivityNavigationHelper((Activity) mContext,fullScreenImage,false).execute();
            } else {
                alertMessageOnMainThread(activity.getString(R.string.app_name), activity.getString(R.string.error), SweetAlertDialog.ERROR_TYPE);
            }
        } else
            alertMessageOnMainThread(activity.getString(R.string.app_name), activity.getString(R.string.error), SweetAlertDialog.ERROR_TYPE);
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

    public void showFullScreenImage(final Context activity, final String imageURL,View view) {
        try {
            if (imageURL != null) {
                if (!imageURL.equals("")) {
                    Intent postView = new Intent(mContext, FullScreenImageActivity.class);
                    postView.putExtra(Constants.IMAGE_TYPE, 0);
                    postView.putExtra(Constants.URL, imageURL);
                    String transitionName = mContext.getString(R.string.transition_string_image);
                    ActivityOptionsCompat options =
                            ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                                    view,
                                    transitionName
                            );
                    ActivityCompat.startActivity(mContext, postView, options.toBundle());
                } else {
                    alertMessageOnMainThread(activity.getString(R.string.app_name), activity.getString(R.string.error), SweetAlertDialog.ERROR_TYPE);
                }
            } else
                alertMessageOnMainThread(activity.getString(R.string.app_name), activity.getString(R.string.error), SweetAlertDialog.ERROR_TYPE);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void showLoading(@Nullable String title, @Nullable String description)
    {
        if (mContext!=null)
        {
            Activity activity= (Activity) mContext;
            try {
                if (mDialog==null)
                {
                    mDialog=new SweetAlertDialog(activity,SweetAlertDialog.PROGRESS_TYPE);
                    mDialog.setTitleText(title);
                    mDialog.setContentText(description);
                    mDialog.setCancelable(true);
                    mDialog.show();
                }
                else
                {
                    mDialog.show();
                }
            }catch (Exception e){}
        }
    }

    public void showLoading()
    {
        if (mContext!=null)
        {
            Activity activity= (Activity) mContext;
            try {
                if (mDialog==null)
                {
                    mDialog=new SweetAlertDialog(activity,SweetAlertDialog.PROGRESS_TYPE);
                    mDialog.setTitleText(mContext.getString(R.string.loading));
                    mDialog.setContentText(mContext.getString(R.string.please_wait));
                    mDialog.setCancelable(true);
                    mDialog.show();
                }
                else
                {
                    mDialog.show();
                }
            }catch (Exception e){}
        }
    }

    public void hideLoading()
    {
        if (mContext!=null) {
            Activity activity = (Activity) mContext;
            try {
                if (mDialog != null) {
                    mDialog.dismiss();
                    mDialog=null;
                }
            }catch (Exception e){}
        }
    }
    public void showKeyboard() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 100);
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String formatDateTime(String dateTime) {
        try {
            dateTime = "." + dateTime;
            String year, month, day;
            StringTokenizer tokenizer = new StringTokenizer(dateTime, ".");
            day=tokenizer.nextToken();
            month=tokenizer.nextToken();
            year=tokenizer.nextToken();
            if (day.length()==1)
            {
                day="0"+day;
            }
            if (month.length()==1)
            {
                month="0"+month;
            }
            return day+"."+month+"."+year;
        }catch (Exception e)
        {
            return "01.01.2000";
        }
    }

    public String getApplicationVersionName() {

        String returnValue = "";
        PackageInfo pInfo = null;

        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (pInfo != null && !TextUtils.isEmpty(pInfo.versionName)) {
            returnValue = pInfo.versionName;
        }

        return returnValue;
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        byte[] byteFormat = stream.toByteArray();
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    public void disableAllViews(View v) {
        v.setEnabled(false);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View view = ((ViewGroup) v).getChildAt(i);
                disableAllViews(view);
            }
        }
    }

    public void enableAllViews(View v) {
        v.setEnabled(true);
        if (v instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View view = ((ViewGroup) v).getChildAt(i);
                enableAllViews(view);
            }
        }
    }

    public int convertDpToPixel(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public DisplayMetrics getScreenSize () {
        return Resources.getSystem().getDisplayMetrics();
    }

    public int calculateViewHeight(int totalRecordCount, float rowInCellCount, int cellHeight) {
        int totalHeight = (int) (Math.ceil(((float) totalRecordCount) / rowInCellCount) * convertDpToPixel(cellHeight));
        return totalHeight;
    }

    public boolean hasPermission(Context context, String permission) {

        int res = context.checkCallingOrSelfPermission(permission);

        return res == PackageManager.PERMISSION_GRANTED;
    }

    public boolean hasPermissions(Context context, String... permissions) {

        boolean hasAllPermissions = true;

        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                hasAllPermissions = false;
            }
        }

        return hasAllPermissions;

    }

    public void checkContentView(Switcher switcher, Integer recordCount) {

        if (switcher == null || recordCount == null) {
            return;
        }

        if (recordCount == 0) {
            switcher.showEmptyView();
        } else {
            switcher.showContentView();
        }
    }

    public void transparentStatusBar(Activity activity)
    {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    public void checkContentView(Switcher switcher, Integer recordCount, SwipeRefreshLayout swipeRefreshLayout) {

        if (switcher == null || recordCount == null) {
            return;
        }

        if (recordCount == 0) {
            switcher.showEmptyView();
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setEnabled(true);
            }
        } else {
            switcher.showContentView();
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setEnabled(false);
            }
        }
    }
}
