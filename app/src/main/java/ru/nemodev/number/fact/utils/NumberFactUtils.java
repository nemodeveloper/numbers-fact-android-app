package ru.nemodev.number.fact.utils;

import org.apache.commons.lang3.StringUtils;

import ru.nemodev.number.fact.entity.NumberFact;

public final class NumberFactUtils {

    private NumberFactUtils() {
        throw new IllegalStateException("Запрещено создавать утилитный класс - " + NumberFactUtils.class.getName());
    }

    public static String getVerboseFactText(NumberFact numberFact) {
        return numberFact.getNumber() + " is " + numberFact.getText();
    }

    public static String getVerboseType(NumberFact numberFact) {
        StringBuilder verboseType = new StringBuilder(numberFact.getFactType().name());
        if (StringUtils.isNotEmpty(numberFact.getFactDate())) {
            verboseType.append(" ").append(numberFact.getFactDate());
        }
        if (StringUtils.isNotEmpty(numberFact.getFactYear())) {
            verboseType.append(" ").append(numberFact.getFactYear());
        }

        return verboseType.toString();
    }

}
