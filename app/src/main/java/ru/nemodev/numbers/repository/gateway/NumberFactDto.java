package ru.nemodev.numbers.repository.gateway;

import ru.nemodev.numbers.entity.FactType;

public class NumberFactDto {

    private String id;
    private String number;
    private String text;
    private FactType factType;
    private String factDate;
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
