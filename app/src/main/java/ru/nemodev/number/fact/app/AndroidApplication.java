package ru.nemodev.number.fact.app;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;
import ru.nemodev.number.fact.app.config.FirebaseConfig;
import ru.nemodev.number.fact.repository.db.room.AppDataBase;


public class AndroidApplication extends android.app.Application
{
    private static AndroidApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;

        Fabric.with(this, new Crashlytics());
        FirebaseConfig.getInstance();
        AppDataBase.getInstance();
    }

    public static AndroidApplication getInstance()
    {
        return instance;
    }

    public static FirebaseAnalytics getAnalytics() {
        return FirebaseAnalytics.getInstance(getInstance());
    }
}