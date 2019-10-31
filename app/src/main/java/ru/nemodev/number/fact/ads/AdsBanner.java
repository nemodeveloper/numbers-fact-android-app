package ru.nemodev.number.fact.ads;

public interface AdsBanner
{
    void show();
    void hide();

    interface OnAdsListener
    {
        void onClose();
    }
}
