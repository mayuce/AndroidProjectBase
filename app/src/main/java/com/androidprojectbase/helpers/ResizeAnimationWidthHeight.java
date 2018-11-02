/*
 * *
 *  * Created by Muhammed Ali YUCE (@mayuce on github) on 11/2/18 10:01 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 11/2/18 9:37 PM
 *
 */

package com.androidprojectbase.helpers;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by maliy on 23/01/2018.
 */

public class ResizeAnimationWidthHeight extends Animation {
    final int targetHeight,targetWidth;
    View view;
    int startHeight,startWidth;
    boolean isReverse=false;

    public ResizeAnimationWidthHeight(View view, int targetHeight, int startHeight,
                                      int targetWidth, int startWidth) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.startHeight = startHeight;
        this.targetWidth = targetWidth;
        this.startWidth = startWidth;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight;
        int newWidth;
        newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);
        newWidth = (int) (startWidth + (targetWidth - startWidth) * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.getLayoutParams().width = newWidth;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
