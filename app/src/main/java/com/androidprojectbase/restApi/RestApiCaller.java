
/*
 * *
 *  * Created by Muhammet Ali YÜCE on 12.02.2018 11:25
 *  * Copyright (c) 2018 . All rights reserved.
 *  * Last modified 31.01.2018 11:26
 *
 */

package com.androidprojectbase.restApi;

import com.androidprojectbase.restApi.listeners.RequestCompletedEventListener;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RestApiCaller {

    public interface Api<T> {

        void cancel();

        void enqueue(RequestCompletedEventListener<T> callback);

        Api<T> clone();
    }

    public static class ErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
        @Override
        public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            if (getRawType(returnType) != Api.class) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException("MyCall must have generic type (e.g., MyCall<ResponseBody>)");
            }
            Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            Executor callbackExecutor = retrofit.callbackExecutor();
            return new ErrorHandlingCallAdapter<>(responseType, callbackExecutor);
        }

        private static final class ErrorHandlingCallAdapter<R> implements CallAdapter<R, Api<R>> {
            private final Type responseType;
            private final Executor callbackExecutor;

            ErrorHandlingCallAdapter(Type responseType, Executor callbackExecutor) {
                this.responseType = responseType;
                this.callbackExecutor = callbackExecutor;
            }

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public Api<R> adapt(Call<R> call) {
                return new RestApiCallAdapter<>(call, callbackExecutor);
            }
        }
    }

    static class RestApiCallAdapter<T> implements Api<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        RestApiCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public void enqueue(final RequestCompletedEventListener<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    // TODO if 'callbackExecutor' is not null, the 'callback' methods should be executed
                    // on that executor by submitting a Runnable. This is left as an exercise for the reader.
                    int code = response.code();
                    if (code >= 200 && code < 300) {
                        callback.onSuccess(response);
                    } else if (code == 401) {
                        callback.onUnauthenticated(response);
                    } else if (code >= 400 && code < 500) {
                        callback.onClientError(response);
                    } else if (code >= 500 && code < 600) {
                        callback.onServerError(response);
                    } else {
                        callback.onUnexpectedError(new RuntimeException("Unexpected response " + response));
                    }
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    // TODO if 'callbackExecutor' is not null, the 'callback' methods should be executed
                    // on that executor by submitting a Runnable. This is left as an exercise for the reader.

                    if (t instanceof IOException) {
                        callback.onNetworkError((IOException) t);
                    } else {
                        callback.onUnexpectedError(t);
                    }
                }
            });
        }

        @Override
        public Api<T> clone() {
            return new RestApiCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}