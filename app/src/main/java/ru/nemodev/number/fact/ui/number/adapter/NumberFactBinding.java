package ru.nemodev.number.fact.ui.number.adapter;

import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.app.AndroidApplication;
import ru.nemodev.number.fact.entity.number.NumberFact;
import ru.nemodev.number.fact.utils.AndroidUtils;
import ru.nemodev.number.fact.utils.NumberFactUtils;

public final class NumberFactBinding {

    @BindingAdapter({"formatFactText"})
    public static void formatFactText(TextView textView, NumberFact numberFact) {
        textView.setText(AndroidUtils.getColoredString(NumberFactUtils.getVerboseFactText(numberFact),
                0, numberFact.getNumber().length(),
                ContextCompat.getColor(AndroidApplication.getInstance(), R.color.colorPrimary)));
    }

}
