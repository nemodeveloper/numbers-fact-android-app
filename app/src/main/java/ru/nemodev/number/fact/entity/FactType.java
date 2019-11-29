package ru.nemodev.number.fact.entity;

import androidx.room.TypeConverter;

public enum FactType
{
    TRIVIA,
    YEAR,
    DATE,
    MATH;

    @TypeConverter
    public static FactType toFactType(String factType) {
        return FactType.valueOf(factType.toUpperCase());
    }

    @TypeConverter
    public static String fromFactType(FactType factType) {
        return factType.name().toUpperCase();
    }
}
