package com.puskin.frankenstein.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.network.FrankensteinEndpointInterface;
import com.puskin.frankenstein.network.ToStringConverterFactory;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    public static final String BASE_URL = "http://192.168.0.106/FrankensteinWS/api/";

    @Bind(R.id.textView_label_description)
    TextView labelDescription;
    @Bind(R.id.textView_webservice_result)
    TextView webserviceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result;
                Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {

                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaredClass().equals(String.class);
                            }

                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(new ToStringConverterFactory() )
                        .build();

                FrankensteinEndpointInterface feInterface = retrofit.create(FrankensteinEndpointInterface.class);

                Call<String> call = feInterface.getUser(3);

                result = "Ups";
                try {
                    result = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                webserviceResult.setText(result);

                labelDescription.setText("Potato");
            }
        }.execute();


    }
}
