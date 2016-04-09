package com.puskin.frankenstein.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.examples.ExampleHub;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.FrankensteinEndpointInterface;
import com.puskin.frankenstein.network.ToStringConverterFactory;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    public static final String BASE_URL = "http://192.168.0.106/FrankensteinWS/api/";

    @Bind(R.id.textView_label_description)
    TextView labelDescription;
    @Bind(R.id.textView_webservice_result)
    TextView webserviceResult;
    @Bind(R.id.button_launchExperiments)
    Button buttonLaunchExperiments;
    @Bind(R.id.button_Doctors)
    Button buttonDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        buttonLaunchExperiments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, ExampleHub.class);
                startActivity(i);
            }
        });

        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).findFirst();

        labelDescription.setText(user.getFullName() + " " + user.getPerson().getName());


        buttonDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, DoctorList.class);
                startActivity(i);
            }
        });
    }
}
