package ru.nemodev.number.fact.ui.number.adapter;

import ru.nemodev.number.fact.entity.number.NumberFact;


public interface OnNumberCardActionListener {
    void onCopyClick(NumberFact numberFact);
    void onShareClick(NumberFact numberFact);
}
