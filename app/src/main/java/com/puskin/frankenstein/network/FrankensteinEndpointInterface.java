package com.puskin.frankenstein.network;

import com.puskin.frankenstein.models.AppointmentModel;
import com.puskin.frankenstein.models.AppointmentSubmitModel;
import com.puskin.frankenstein.models.AppointmentTestSet;
import com.puskin.frankenstein.models.Clinic;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.models.LoginObject;
import com.puskin.frankenstein.models.ScheduleModel;
import com.puskin.frankenstein.models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @Headers("Content-Type: application/json")
    @POST("register")
    Call<Void> register(@Body User user);

    @GET("doctors")
    Call<RealmList<Doctor>> getDoctors();

    @GET("clinics")
    Call<RealmList<Clinic>> getClinics();

    @GET("medicaltests/{person_id}")
    Call<ArrayList<AppointmentTestSet>> getLaboratoryTests(@Path("person_id") int personID);

    @GET("appointments/{person_id}")
    Call<ArrayList<AppointmentModel>> getAppointments(@Path("person_id") int personID);


    @Headers("Content-Type: application/json")
    @POST("schedule")
    Call<ArrayList<Date>> checkTimes(@Body ScheduleModel scheduleModel);

    @Headers("Content-Type: application/json")
    @POST("appointments")
    Call<Void> addAppointment(@Body AppointmentSubmitModel appointmentSubmitModel);

    @Headers("Content-Type: application/json")
    @PUT("appointments")
    Call<Void> modifyApopintment(@Body AppointmentSubmitModel appointmentSubmitModel);
}
