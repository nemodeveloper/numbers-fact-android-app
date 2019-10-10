package ru.nemodev.numbers.ads;

public interface AdsBanner
{
    void show();
    void hide();

    interface OnAdsListener
    {
        void onClose();
    }
}
