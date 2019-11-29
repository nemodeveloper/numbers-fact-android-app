package ru.nemodev.number.fact.app;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
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
        AppDataBase.getInstance();
    }


    public static AndroidApplication getInstance()
    {
        return instance;
    }

}