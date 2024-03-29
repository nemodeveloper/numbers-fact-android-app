package ru.nemodev.number.fact.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.app.AndroidApplication;


public final class AndroidUtils
{
    private static final String LOG_TAG = AndroidUtils.class.getSimpleName();

    private static final String DIALOG_SHARE_TYPE = "text/plain";

    private AndroidUtils() { }

    public static String getString(int resId)
    {
        return AndroidApplication.getInstance().getResources().getString(resId);
    }

    public static String getString(int resId, Object... params)
    {
        return String.format(getString(resId), params);
    }

    public static int getInteger(int resId)
    {
        return AndroidApplication.getInstance().getResources().getInteger(resId);
    }

    public static List<String> getStringList(int resId)
    {
        return Arrays.asList(AndroidApplication.getInstance().getResources().getStringArray(resId));
    }

    public static void openShareDialog(Context context, String dialogTitle, String shareContent)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType(DIALOG_SHARE_TYPE);
        context.startActivity(Intent.createChooser(sendIntent, dialogTitle));
    }

    public static void sendPlayMarketAppInfo(Context context)
    {
        AndroidUtils.openShareDialog(context,
                AndroidUtils.getString(R.string.tell_about_app_title),
                AndroidUtils.getString(R.string.play_market_app_http_link) + context.getPackageName());
    }

    public static void openAppByURI(Activity activity, String rawURI)
    {
        Uri uri = Uri.parse(rawURI);
        Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

        startActivity(activity, viewIntent);
    }

    public static void openAppPage(Activity activity)
    {
        String packageName = activity.getPackageName();
        try
        {
            openAppByURI(activity, getString(R.string.play_market_app_link) + packageName);
        }
        catch (ActivityNotFoundException e)
        {
            startActivity(activity, new Intent(Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.play_market_app_http_link) + packageName)));
        }
    }

    private static void startActivity(Activity activity, Intent intent)
    {
        activity.startActivity(intent);
    }

    public static void showSnackBarMessage(View whereShow, int textId)
    {
        showSnackBarMessage(whereShow, AndroidUtils.getString(textId));
    }

    public static void showSnackBarMessage(View whereShow, String message)
    {
        try
        {
            Snackbar snackbar = Snackbar.make(whereShow, message, Snackbar.LENGTH_SHORT);

            View snackbarView = snackbar.getView();

            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            // Для показа по размеру текста
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
//            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//            params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
//            snackbarView.setLayoutParams(params);

            snackbar.show();
        }
        catch (Exception e)
        {
            LogUtils.error(LOG_TAG, "Ошибка при показе ShackBar сообщения!", e);
        }
    }

    public static Spannable getColoredString(String text, int startPos, int endPos, int color) {
        Spannable spannable = new SpannableString(text);
        spannable.setSpan(new ForegroundColorSpan(color), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static boolean copyTextToClipBoard(String text) {
        ClipboardManager clipboard = (ClipboardManager) AndroidApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            ClipData clip = ClipData.newPlainText(getString(R.string.app_name), text);
            clipboard.setPrimaryClip(clip);
            return true;
        }

        return false;
    }
}
