
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase.restApi.listeners;

import retrofit2.Response;

public interface IRequestCompletedEventListener<T> {

    void onSuccess(Response<T> response);
}