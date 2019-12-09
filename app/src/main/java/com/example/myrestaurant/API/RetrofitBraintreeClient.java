package com.example.myrestaurant.API;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBraintreeClient {

    private static Retrofit instanceRetrofit;

    private static Retrofit getInstance() {

        if (instanceRetrofit == null) {
            //TODO Create HttpLoggingInterceptor
            HttpLoggingInterceptor httpLoggingInterceptor
                    = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.e("api", message);
                }
            });

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .retryOnConnectionFailure(true)
                    .build();

            instanceRetrofit = new Retrofit.Builder()
                    .baseUrl("http://6.6.6.31:3001/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instanceRetrofit;
    }
    //TODO Create Interfac getApi
    public static IBraintreeAPI getApi() {
        return getInstance()
                .create(IBraintreeAPI.class);
    }
}


