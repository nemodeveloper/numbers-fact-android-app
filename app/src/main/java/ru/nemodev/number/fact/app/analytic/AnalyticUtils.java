package ru.nemodev.number.fact.app.analytic;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.apache.commons.lang3.StringUtils;

import ru.nemodev.number.fact.app.AndroidApplication;
import ru.nemodev.number.fact.entity.purchase.PurchaseItem;

public final class AnalyticUtils {

    public static void searchEvent(SearchType searchType, String number) {
        if (StringUtils.isNotBlank(number)) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, searchType.name());
            bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, number);
            AndroidApplication.getAnalytics().logEvent(FirebaseAnalytics.Event.SEARCH, bundle);
        }
    }

    public static void shareEvent(ShareType shareType, String number) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, shareType.name());
        bundle.putString(FirebaseAnalytics.Param.CONTENT, shareType.getTypeName());
        bundle.putString(FirebaseAnalytics.Param.VALUE, number);

        AndroidApplication.getAnalytics().logEvent(FirebaseAnalytics.Event.SHARE, bundle);
    }

    public static void purchaseEvent(PurchaseItem purchaseItem) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, purchaseItem.getPurchaseType().getSku());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, purchaseItem.getTitle());
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, purchaseItem.getPurchaseType().getItemType());
        bundle.putString(FirebaseAnalytics.Param.PRICE, purchaseItem.getPrice());
        bundle.putString(FirebaseAnalytics.Param.CURRENCY, purchaseItem.getCurrency().toString());

        AndroidApplication.getAnalytics().logEvent(FirebaseAnalytics.Event.ECOMMERCE_PURCHASE, bundle);
    }

    public enum SearchType {
        NUMBER_FACT
    }

    public enum ShareType {
        NUMBER_FACT("Поделиться фактом"),
        NUMBER_FACT_COPY("Копирование факта");

        private final String typeName;

        ShareType(String typeName)
        {
            this.typeName = typeName;
        }

        public String getTypeName()
        {
            return typeName;
        }
    }
}
