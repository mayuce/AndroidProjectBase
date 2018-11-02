/*
 * *
 *  * Created by Muhammed Ali YUCE (@mayuce on github) on 11/2/18 10:01 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 11/2/18 9:31 PM
 *
 */

package com.androidprojectbase.components.imageloader;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

	public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		if (loadedImage != null) {
			ImageView imageView = (ImageView) view;
			boolean firstDisplay = !displayedImages.contains(imageUri);
			if (firstDisplay) {
				FadeInBitmapDisplayer.animate(imageView, 500);
				displayedImages.add(imageUri);
			}
		}
	}
}