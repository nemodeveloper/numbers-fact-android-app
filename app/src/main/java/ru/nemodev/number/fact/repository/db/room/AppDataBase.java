package ru.nemodev.number.fact.repository.db.room;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import ru.nemodev.number.fact.app.AndroidApplication;
import ru.nemodev.number.fact.entity.CreationType;
import ru.nemodev.number.fact.entity.FactType;
import ru.nemodev.number.fact.entity.NumberFact;


@Database(entities = {NumberFact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase
{
    private static final String DATA_BASE_NAME = "numbers-fact.db";

    private static volatile AppDataBase instance;

    public abstract NumberFactRepository getNumberFactRepository();

    public static AppDataBase getInstance()
    {
        if (instance == null)
        {
            synchronized (AppDataBase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(AndroidApplication.getInstance(), AppDataBase.class, DATA_BASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    populate();
                                }
                            })
                            .build();
                }
            }
        }

        return instance;
    }

    private static void populate() {
        Executors.newSingleThreadScheduledExecutor().execute(AppDataBase::fromFile);
    }

    private static void fromFile() {
        try {
            InputStream inputStream = AndroidApplication.getInstance().getAssets().open("numbers.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonData = new String(buffer, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<NumberFact> numberFacts = objectMapper.readValue(jsonData, new TypeReference<List<NumberFact>>() {});
            numberFacts = numberFacts.stream()
                    .filter(numberFact -> StringUtils.isNotEmpty(numberFact.getNumber()))
                    .peek(numberFact -> {
                        numberFact.setId(UUID.randomUUID().toString());
                        numberFact.setCreationType(CreationType.EXTERNAL);
                        if (numberFact.getFactType() == null) {
                            numberFact.setFactType(FactType.TRIVIA);
                        }
                    })
                    .collect(Collectors.toList());

            getInstance().getNumberFactRepository().add(numberFacts);

        } catch (Exception e) {
            Log.e("DataBaseMigration", "error populate db from file", e);
        }
    }
}