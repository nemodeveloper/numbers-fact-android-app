package ru.nemodev.number.fact.analytic;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.commons.lang3.StringUtils;

import ru.nemodev.number.fact.app.AndroidApplication;

public final class AnalyticUtils {

    public static void searchEvent(SearchType searchType, String number) {
        if (StringUtils.isNotBlank(number)) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, searchType.name());
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, number);
            AndroidApplication.getAnalytics().logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
        }
    }

    public enum SearchType {
        NUMBER_FACT
    }
}
