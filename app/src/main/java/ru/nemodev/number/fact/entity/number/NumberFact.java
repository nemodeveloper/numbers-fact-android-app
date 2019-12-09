package ru.nemodev.number.fact.entity.number;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


// TODO добавить возможность создавать свои факты + добавить фильтрацию по ним
// TODO добавить лайки
@Entity(tableName = "numbers_fact",
        indices = {
                @Index("number")
        })
public class NumberFact {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "number")
    @NonNull
    private String number;

    @ColumnInfo(name = "text")
    @NonNull
    private String text;

    @ColumnInfo(name = "fact_type")
    @NonNull
    @TypeConverters(FactType.class)
    private FactType factType;

    @ColumnInfo(name = "creation_type")
    @NonNull
    @TypeConverters(CreationType.class)
    private CreationType creationType;

    @ColumnInfo(name = "fact_date")
    private String factDate;

    @ColumnInfo(name = "fact_year")
    private String factYear;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FactType getFactType() {
        return factType;
    }

    public void setFactType(FactType factType) {
        this.factType = factType;
    }

    @NonNull
    public CreationType getCreationType() {
        return creationType;
    }

    public void setCreationType(@NonNull CreationType creationType) {
        this.creationType = creationType;
    }

    public String getFactDate() {
        return factDate;
    }

    public void setFactDate(String factDate) {
        this.factDate = factDate;
    }

    public String getFactYear() {
        return factYear;
    }

    public void setFactYear(String factYear) {
        this.factYear = factYear;
    }
}
