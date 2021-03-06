package com.puskin.frankenstein.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.events.LoginEvent;
import com.puskin.frankenstein.models.LoginObject;
import com.puskin.frankenstein.models.User;
import com.puskin.frankenstein.network.NetworkHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;

public class LogIn extends AppCompatActivity {

    public static final int RESULT_OK = 200;
    private static final int REQUEST_CODE = 1;

    @Bind(R.id.btnLogin)
    Button buttonLogIn;
    @Bind(R.id.etUserName)
    TextView etUserName;
    @Bind(R.id.etPassword)
    TextView etPassword;
    @Bind(R.id.pbLogin)
    ProgressBar pbLogin;
    @Bind(R.id.tvSignUp)
    TextView tvSignUp;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogIn.setVisibility(View.GONE);
                pbLogin.setVisibility(View.VISIBLE);
                LoginObject loginObject = new LoginObject(etUserName.getText().toString(),
                        etPassword.getText().toString());
                NetworkHelper.doLogin(loginObject);
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, Register.class);
                startActivityForResult(i, REQUEST_CODE);
            }
        });

        realm = Realm.getDefaultInstance();
        User loggedUser = realm.where(User.class).findFirst();

        if(loggedUser != null){
            Intent i = new Intent(this, Home.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent) {
        if (loginEvent.getResponseCode() == 200) {
            Intent i = new Intent(this, Home.class);
            startActivity(i);
            finish();
        } else {
            pbLogin.setVisibility(View.GONE);
            buttonLogIn.setVisibility(View.VISIBLE);
            Toast.makeText(this, loginEvent.getRespondeMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            etUserName.setText(data.getStringExtra("username"));
        }
    }
}
