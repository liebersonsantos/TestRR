package com.example.naville.rrtracking_android.network;

import android.content.Context;

import com.example.naville.rrtracking_android.util.Constants;
import com.example.naville.rrtracking_android.util.Preferences;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Retrofit retrofit;

    public static Api getInstanceRecoveryPassWord(){

        if (retrofit == null){
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor()) //dessa forma consigo debugar em tempo de execuçao com o navegador chrome
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL_BASE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return  retrofit.create(Api.class);
    }

    public static Api getInstanceLogin(Context context) {

        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor()) //vejo o que mando e recebo
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        Request request = chain.request();

                        Preferences preferences = new Preferences(context);
                        Request.Builder builder = request.newBuilder()
//                                .addHeader("Accept", "application/json;versions=1")
                                .header("Authorization", Credentials.basic(preferences.getEmail(), preferences.getSenha()));

                        return chain.proceed(builder.build());
                    })
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL_BASE)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }

        return retrofit.create(Api.class);
    }


}
