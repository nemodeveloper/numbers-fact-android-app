package ru.nemodev.number.fact.entity.ads;

public interface AdsBanner
{
    void show();
    void hide();

    interface OnAdsListener
    {
        void onClose();
    }
}
