package com.puskin.frankenstein.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Alexandra on 12-Mar-16.
 */
public interface FrankensteinEndpointInterface {
    @GET("users/{user_id}")
    Call<String> getUser(@Path("user_id") int userID);
}
