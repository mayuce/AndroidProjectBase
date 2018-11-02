/*
 * *
 *  * Created by Muhammed Ali YUCE (@mayuce on github) on 11/2/18 10:01 PM
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 11/2/18 9:31 PM
 *
 */

package com.androidprojectbase.components.jazzyviewpager;

import android.content.res.Resources;
import android.util.TypedValue;

public class Util {

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

}
