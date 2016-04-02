package com.puskin.frankenstein.network;

import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.puskin.frankenstein.events.LoginEvent;
import com.puskin.frankenstein.models.LoginObject;
import com.puskin.frankenstein.models.User;

import org.greenrobot.eventbus.EventBus;

import okhttp3.internal.http.RetryableSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class NetworkHelper {
    public static final String BASE_URL = "http://192.168.100.13/FrankensteinWS/api/";

    public static boolean doLogin(LoginObject loginObject) {
        Log.d("DBG","Login with user: " + loginObject.getUsername() + " and password: " + loginObject.getPassword());
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<User> call = feInterface.logIn(loginObject);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("dbg", "onResponse: response was successful");
                EventBus.getDefault().post(new LoginEvent(response.code(),response.message()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("dbg", "onResponse: response was failed");
            }
        });

        return false;
    }
}
