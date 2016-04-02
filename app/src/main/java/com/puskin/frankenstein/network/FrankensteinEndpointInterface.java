package com.puskin.frankenstein.network;

import com.puskin.frankenstein.models.LoginObject;
import com.puskin.frankenstein.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Alexandra on 12-Mar-16.
 */
public interface FrankensteinEndpointInterface {
    @GET("users/{user_id}")
    Call<String> getUser(@Path("user_id") int userID);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<User> logIn(@Body LoginObject loginObject);
}
