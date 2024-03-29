package ru.nemodev.number.fact.entity.purchase;

import com.android.billingclient.api.SkuDetails;

import org.apache.commons.lang3.StringUtils;

import java.util.Currency;


public class PurchaseItem {
    private final SkuDetails skuDetails;
    private final boolean purchase;
    private final PurchaseType purchaseType;
    private final String title;

    public PurchaseItem(SkuDetails skuDetails, boolean purchase) {
        this.skuDetails = skuDetails;
        this.purchase = purchase;

        this.purchaseType = PurchaseType.getBySkuId(skuDetails.getSku());
        this.title = removeAppDescription(skuDetails.getTitle());
    }

    public SkuDetails getSkuDetails() {
        return skuDetails;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public String getPrice() {
        return skuDetails.getPrice();
    }

    public Currency getCurrency() {
        return Currency.getInstance(skuDetails.getPriceCurrencyCode());
    }

    public String getPriceText() {
        return skuDetails.getPrice();
    }

    public boolean isPurchase() {
        return purchase;
    }

    public String getTitle() {
        return title;
    }

    public String getSku() {
        return skuDetails.getSku();
    }

    private String removeAppDescription(String rawString) {
        String title = rawString;

        if (rawString.contains("(")) {
            title = rawString.substring(0, rawString.indexOf("("));
        }

        return StringUtils.strip(title);
    }
}
