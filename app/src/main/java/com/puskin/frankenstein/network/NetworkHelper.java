package com.puskin.frankenstein.network;

import android.content.Context;
import android.util.EventLogTags;
import android.util.Log;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.puskin.frankenstein.activities.TreatmentList;
import com.puskin.frankenstein.events.AppointmentEvent;
import com.puskin.frankenstein.events.AppointmentSubmitedEvent;
import com.puskin.frankenstein.events.AvailableDatesEvent;
import com.puskin.frankenstein.events.ClinicEvent;
import com.puskin.frankenstein.events.DoctorEvent;
import com.puskin.frankenstein.events.LoginEvent;
import com.puskin.frankenstein.events.RegisterEvent;
import com.puskin.frankenstein.events.TestListEvent;
import com.puskin.frankenstein.events.TreatmentListEvent;
import com.puskin.frankenstein.models.AppointmentModel;
import com.puskin.frankenstein.models.AppointmentSubmitModel;
import com.puskin.frankenstein.models.AppointmentTestSet;
import com.puskin.frankenstein.models.AppointmentTreatment;
import com.puskin.frankenstein.models.Clinic;
import com.puskin.frankenstein.models.Doctor;
import com.puskin.frankenstein.models.LoginObject;
import com.puskin.frankenstein.models.MedicalTestModel;
import com.puskin.frankenstein.models.ScheduleModel;
import com.puskin.frankenstein.models.User;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class NetworkHelper {
    public static final String BASE_URL = "http://192.168.43.237/FrankensteinWS/api/";

    public static boolean doLogin(LoginObject loginObject) {
        Log.d("DBG", "Login with user: " + loginObject.getUsername() + " and password: " + loginObject.getPassword());
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
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

                if (response.code() == 200) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.clear(User.class);

                    realm.copyToRealmOrUpdate(response.body());

                    realm.commitTransaction();
                }

                EventBus.getDefault().post(new LoginEvent(response.code(), response.message()));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                EventBus.getDefault().post(new LoginEvent(-1, "Request failed"));
                Log.d("dbg", "onResponse: response was failed");
            }
        });

        return false;
    }


    public static void doRegister(final User user) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<Void> call = feInterface.register(user);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DBG", "onResponse: " + response.message() + " " + response.code());
                RegisterEvent registerEvent = new RegisterEvent(response.code(), response.message());
                registerEvent.setUsername(user.getUserName());
                EventBus.getDefault().post(registerEvent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                EventBus.getDefault().post(new RegisterEvent(-1, "Request failed"));

            }
        });
    }

    public static void getDoctors() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
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

        Call<RealmList<Doctor>> call = feInterface.getDoctors();
        call.enqueue(new Callback<RealmList<Doctor>>() {


            @Override
            public void onResponse(Call<RealmList<Doctor>> call, Response<RealmList<Doctor>> response) {
                Log.d("DBG", "onResponse: " + response.code() + ' ' + response.message());
                DoctorEvent doctorEvent = new DoctorEvent(response.code(), response.message(), response.body());
                EventBus.getDefault().post(doctorEvent);
            }

            @Override
            public void onFailure(Call<RealmList<Doctor>> call, Throwable t) {
                Log.d("DBG", "Fail: getDoctors");
            }
        });
    }

    public static void getClinics() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
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

        Call<RealmList<Clinic>> call = feInterface.getClinics();
        call.enqueue(new Callback<RealmList<Clinic>>() {


            @Override
            public void onResponse(Call<RealmList<Clinic>> call, Response<RealmList<Clinic>> response) {
                Log.d("DBG", "onResponse: " + response.code() + ' ' + response.message());
                ClinicEvent clinicEvent = new ClinicEvent(response.code(), response.message(), response.body());
                EventBus.getDefault().post(clinicEvent);
            }

            @Override
            public void onFailure(Call<RealmList<Clinic>> call, Throwable t) {
                Log.d("DBG", "Fail: getDoctors");
            }
        });
    }

    public static void getLabTests(int userID) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<ArrayList<AppointmentTestSet>> call = feInterface.getLaboratoryTests(userID);
        call.enqueue(new Callback<ArrayList<AppointmentTestSet>>() {


            @Override
            public void onResponse(Call<ArrayList<AppointmentTestSet>> call, Response<ArrayList<AppointmentTestSet>> response) {
                Log.d("DBG", "onResponse: " + response.code() + ' ' + response.message());
                if (response.code() == 200) {
                    EventBus.getDefault().post(new TestListEvent(response.body(), response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new TestListEvent(null, response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AppointmentTestSet>> call, Throwable t) {
                Log.d("DBG", "Fail: getLabResults");
                EventBus.getDefault().post(new TestListEvent(null, -1, "Test List Request Failed"));
            }
        });
    }

    public static void getAppointments(int userID) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<ArrayList<AppointmentModel>> call = feInterface.getAppointments(userID);
        call.enqueue(new Callback<ArrayList<AppointmentModel>>() {


            @Override
            public void onResponse(Call<ArrayList<AppointmentModel>> call, Response<ArrayList<AppointmentModel>> response) {
                Log.d("DBG", "onResponse: " + response.code() + ' ' + response.message());
                if (response.code() == 200) {
                    EventBus.getDefault().post(new AppointmentEvent(response.body(), response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new AppointmentEvent(null, response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AppointmentModel>> call, Throwable t) {
                Log.d("DBG", "Fail: getAppointments");
                EventBus.getDefault().post(new AppointmentEvent(null, -1, "Appointment List Request Failed"));
            }
        });
    }

    public static void checkAvailableTimes(final ScheduleModel scheduleModel) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<ArrayList<Date>> call = feInterface.checkTimes(scheduleModel);
        call.enqueue(new Callback<ArrayList<Date>>() {

            @Override
            public void onResponse(Call<ArrayList<Date>> call, Response<ArrayList<Date>> response) {
                Log.d("DBG", "onResponse: " + response.message() + " " + response.code());
                if (response.code() == 200) {
                    EventBus.getDefault().post(new AvailableDatesEvent(response.body(), response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new AvailableDatesEvent(null, response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Date>> call, Throwable t) {
                EventBus.getDefault().post(new AvailableDatesEvent(null, -1, "Request completely failed"));

            }
        });
    }

    public static void createAppointmment(final AppointmentSubmitModel appointmentSubmitModel) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<Void> call = feInterface.addAppointment(appointmentSubmitModel);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DBG", "onResponse: " + response.message() + " " + response.code());
                if (response.code() == 200 || response.code() == 204) {
                    EventBus.getDefault().post(new AppointmentSubmitedEvent(response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new AppointmentSubmitedEvent(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                EventBus.getDefault().post(new AppointmentSubmitedEvent(-1, "Request completely failed"));

            }
        });

    }

    public static void cancelApointment(final AppointmentSubmitModel appointmentSubmitModel) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<Void> call = feInterface.modifyApopintment(appointmentSubmitModel);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("DBG", "onResponse: " + response.message() + " " + response.code());
                if (response.code() == 200 || response.code() == 204) {
                    EventBus.getDefault().post(new AppointmentSubmitedEvent(response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new AppointmentSubmitedEvent(response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                EventBus.getDefault().post(new AppointmentSubmitedEvent(-1, "Request completely failed"));

            }
        });
    }

    public static void getTreatmentList(int userID) {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

        Call<ArrayList<AppointmentTreatment>> call = feInterface.getTreatments(userID);
        call.enqueue(new Callback<ArrayList<AppointmentTreatment>>() {


            @Override
            public void onResponse(Call<ArrayList<AppointmentTreatment>> call, Response<ArrayList<AppointmentTreatment>> response) {
                Log.d("DBG", "onResponse: " + response.code() + ' ' + response.message());
                if (response.code() == 200) {
                    EventBus.getDefault().post(new TreatmentListEvent(response.body(), response.code(), response.message()));
                } else {
                    EventBus.getDefault().post(new TreatmentListEvent(null, response.code(), response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AppointmentTreatment>> call, Throwable t) {
                Log.d("DBG", "Fail: getLabResults");
                EventBus.getDefault().post(new TreatmentListEvent(null, -1, "Test List Request Failed"));
            }
        });
    }
}
