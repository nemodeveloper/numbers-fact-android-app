package ru.nemodev.numbers.entity;

public enum NumberType
{
    TRIVIA("trivia"),
    YEAR("year"),
    DATE("date"),
    MATH("math");

    String value;

    NumberType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public static String getRandomValue()
    {
        return TRIVIA.getValue();
    }
}
