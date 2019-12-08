package ru.nemodev.number.fact.entity.number;

import androidx.room.TypeConverter;


public enum CreationType {
    EXTERNAL,
    USER;

    @TypeConverter
    public static CreationType toFactType(String creationType) {
        return CreationType.valueOf(creationType.toUpperCase());
    }

    @TypeConverter
    public static String fromFactType(CreationType creationType) {
        return creationType.name().toUpperCase();
    }
}
