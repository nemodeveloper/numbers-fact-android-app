package ru.nemodev.number.fact.ui.main.viewmodel.ads;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.billingclient.api.Purchase;

import java.util.Collections;

import ru.nemodev.number.fact.app.config.AdsConfig;
import ru.nemodev.number.fact.app.config.FirebaseConfig;
import ru.nemodev.number.fact.entity.ads.AdsBanner;
import ru.nemodev.number.fact.entity.ads.BannerManager;
import ru.nemodev.number.fact.entity.ads.FullscreenBanner;
import ru.nemodev.number.fact.entity.purchase.PurchaseType;


public class AdsViewModel extends ViewModel implements AdsBanner.OnAdsListener {
    private final Activity activity;
    private BannerManager bannerManager;
    private final MutableLiveData<Boolean> onFullscreenBannerCloseEvent;

    public AdsViewModel(Activity activity) {
        this.activity = activity;
        this.onFullscreenBannerCloseEvent = new MutableLiveData<>();
    }

    @Override
    public void onClose() {
        onFullscreenBannerCloseEvent.postValue(true);
    }

    public LiveData<Boolean> getOnFullscreenBannerCloseEvent() {
        return onFullscreenBannerCloseEvent;
    }

    public void onBuyEvent(Purchase purchase) {
        if (PurchaseType.ADS.getSku().equals(purchase.getSku())) {
            onAdsBuyEvent(true);
        }
    }

    public void onAdsBuyEvent(boolean isBuy) {
        if (isBuy) {
            if (bannerManager != null) {
                bannerManager.hideAds();
            }
        }
        else {
            bannerManager = new BannerManager(activity, Collections.singletonList(
                    new FullscreenBanner(activity, this,
                            FirebaseConfig.getInteger(AdsConfig.FULLSCREEN_BANNER_SHOW_PERIOD_SEC))
            ));
        }
    }
}
