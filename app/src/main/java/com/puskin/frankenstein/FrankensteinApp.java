package com.puskin.frankenstein;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class FrankensteinApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config = new RealmConfiguration
                .Builder(this)
                .name("frankenstein.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
