package ru.nemodev.numbers.ads;

import android.content.Context;

import com.google.android.gms.ads.MobileAds;

import java.util.List;

import ru.nemodev.numbers.R;
import ru.nemodev.numbers.utils.AndroidUtils;


public class BannerManager
{
    private final List<AdsBanner> adsBannerList;

    public BannerManager(Context context, List<AdsBanner> adsBannerList)
    {
        MobileAds.initialize(context, AndroidUtils.getString(R.string.ads_app_id));

        this.adsBannerList = adsBannerList;
        for (AdsBanner adsBanner : adsBannerList)
        {
            adsBanner.show();
        }
    }

    public void hideAds()
    {
        for (AdsBanner adsBanner : adsBannerList)
        {
            adsBanner.hide();
        }
    }
}
