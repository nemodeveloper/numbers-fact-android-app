package ru.nemodev.numbers.app;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.nemodev.numbers.repository.db.room.AppDataBase;


public class AndroidApplication extends android.app.Application
{
    private static AndroidApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        AppDataBase.getInstance();

        initFabricIO();
    }

    private void initFabricIO()
    {
        Fabric.with(this, new Crashlytics());
    }

    public static AndroidApplication getInstance()
    {
        return instance;
    }

}