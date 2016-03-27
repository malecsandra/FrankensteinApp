package com.puskin.frankenstein.examples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.puskin.frankenstein.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExampleHub extends AppCompatActivity {

    @Bind(R.id.button_drawerLayout)
    Button buttonDrawerLayout;
    @Bind(R.id.button_exmaple2)
    Button buttonExmaple2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example_hub);
        ButterKnife.bind(this);

        buttonDrawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExampleHub.this, DrawerActivity.class);
                startActivity(i);
            }
        });
    }
}
