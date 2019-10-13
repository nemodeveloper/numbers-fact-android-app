package ru.nemodev.numbers.entity;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum FactType
{
    TRIVIA,
    YEAR,
    DATE,
    MATH;

    @TypeConverter
    @JsonCreator
    public static FactType toFactType(String factType) {
        return FactType.valueOf(factType.toUpperCase());
    }

    @TypeConverter
    public static String fromFactType(FactType factType) {
        return factType.name().toUpperCase();
    }
}
