package ru.nemodev.number.fact.ui.main.viewmodel.ads;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;

import ru.nemodev.number.fact.ads.AdsBanner;
import ru.nemodev.number.fact.ads.BannerManager;
import ru.nemodev.number.fact.ads.FullscreenBanner;

public class AdsViewModel extends ViewModel implements AdsBanner.OnAdsListener {
    private final Activity activity;
    private final BannerManager bannerManager;
    private final MutableLiveData<Boolean> onFullscreenBannerCloseEvent;

    public AdsViewModel(Activity activity) {
        this.activity = activity;
        this.onFullscreenBannerCloseEvent = new MutableLiveData<>();

        this.bannerManager = new BannerManager(activity, Arrays.asList(
                new FullscreenBanner(activity, this, 3)
        ));
    }

    @Override
    public void onClose() {
        onFullscreenBannerCloseEvent.postValue(true);
    }

    public LiveData<Boolean> getOnFullscreenBannerCloseEvent() {
        return onFullscreenBannerCloseEvent;
    }
}
