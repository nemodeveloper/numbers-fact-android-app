package ru.nemodev.number.fact.app;

import ru.nemodev.number.fact.repository.db.room.AppDataBase;


public class AndroidApplication extends android.app.Application
{
    private static AndroidApplication instance;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        AppDataBase.getInstance();
    }


    public static AndroidApplication getInstance()
    {
        return instance;
    }

}