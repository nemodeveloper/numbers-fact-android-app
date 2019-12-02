package ru.nemodev.number.fact.utils;

import ru.nemodev.number.fact.entity.NumberFact;

public final class NumberFactUtils {

    private NumberFactUtils() {
        throw new IllegalStateException("Запрещено создавать утилитный класс - " + NumberFactUtils.class.getName());
    }

    public static String getVerboseFactText(NumberFact numberFact) {
        return numberFact.getNumber() + " is " + numberFact.getText();
    }

}
