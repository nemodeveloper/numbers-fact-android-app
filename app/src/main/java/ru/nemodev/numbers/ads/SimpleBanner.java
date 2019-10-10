package ru.nemodev.numbers.ads;

import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import ru.nemodev.numbers.BuildConfig;
import ru.nemodev.numbers.R;
import ru.nemodev.numbers.utils.AndroidUtils;
import ru.nemodev.numbers.utils.LogUtils;


public class SimpleBanner implements AdsBanner
{
    private static final String LOG_TAG = SimpleBanner.class.getSimpleName();

    private final AdView simpleBanner;

    public SimpleBanner(AdView simpleBanner)
    {
        this.simpleBanner = simpleBanner;
    }

    @Override
    public void show()
    {
        try
        {
            simpleBanner.setVisibility(View.VISIBLE);
            simpleBanner.loadAd(buildAdRequest());
        }
        catch (Exception e)
        {
            LogUtils.error(LOG_TAG, "Ошибка инициализации SimpleBanner!", e);
        }
    }

    @Override
    public void hide()
    {
        simpleBanner.setVisibility(View.GONE);
    }

    private AdRequest buildAdRequest()
    {
        AdRequest.Builder builder = new AdRequest.Builder();
        if (BuildConfig.DEBUG)
        {
            builder.addTestDevice(AndroidUtils.getString(R.string.device_id_test));
        }

        return builder.build();
    }
}
