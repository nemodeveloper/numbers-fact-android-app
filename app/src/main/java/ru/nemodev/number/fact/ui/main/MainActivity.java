package ru.nemodev.number.fact.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.databinding.MainActivityBinding;
import ru.nemodev.number.fact.entity.purchase.PurchaseType;
import ru.nemodev.number.fact.ui.main.viewmodel.ads.AdsViewModel;
import ru.nemodev.number.fact.ui.main.viewmodel.ads.AdsViewModelFactory;
import ru.nemodev.number.fact.ui.main.viewmodel.update_app.UpdateAppViewModel;
import ru.nemodev.number.fact.ui.main.viewmodel.update_app.UpdateAppViewModelFactory;
import ru.nemodev.number.fact.ui.purchase.viewmodel.PurchaseViewModel;
import ru.nemodev.number.fact.ui.purchase.viewmodel.PurchaseViewModelFactory;
import ru.nemodev.number.fact.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MainActivityBinding binding;
    private UpdateAppViewModel updateAppViewModel;
    private AdsViewModel adsViewModel;
    private PurchaseViewModel purchaseViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        adsViewModel = ViewModelProviders.of(this, new AdsViewModelFactory(this)).get(AdsViewModel.class);
        adsViewModel.getOnFullscreenBannerCloseEvent().observe(this, aBoolean -> showDisableAdsDialog());

        purchaseViewModel = ViewModelProviders.of(this, new PurchaseViewModelFactory(this)).get(PurchaseViewModel.class);
        purchaseViewModel.purchaseList.observe(this, purchaseItems -> purchaseViewModel.checkPurchase());
        purchaseViewModel.onAdsByEvent.observe(this, aBoolean -> adsViewModel.onAdsBuyEvent(aBoolean));
        purchaseViewModel.onPurchaseEvent.observe(this, purchase -> adsViewModel.onBuyEvent(purchase));

        updateAppViewModel = ViewModelProviders.of(this, new UpdateAppViewModelFactory(this)).get(UpdateAppViewModel.class);
        updateAppViewModel.getUpdateAppEvent().observe(this, installState -> showUpdateDialog());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(0);
        boolean needCallSuper = true;
        if (currentFragment instanceof NavHostFragment) {
            NavHostFragment navHostFragment = (NavHostFragment) currentFragment;
            List<Fragment> currentFragments = navHostFragment.getChildFragmentManager().getFragments();
            if (CollectionUtils.isNotEmpty(currentFragments)) {
                Fragment fragment = currentFragments.get(0);
                if (fragment instanceof OnBackPressedListener) {
                    needCallSuper = !((OnBackPressedListener) fragment).onBackPressed();
                }
            }
        }

        if (needCallSuper) {
            super.onBackPressed();
        }
    }

    private void showUpdateDialog() {
        if (!isFinishing()) {
            try {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.app_update_ready_title)
                        .setPositiveButton(R.string.app_update_yes, (dialog, which) -> updateAppViewModel.appUpdate())
                        .setNeutralButton(R.string.app_update_no, (dialog, which) -> {})
                        .setCancelable(true)
                        .create()
                        .show();
            }
            catch (Exception e) {
                LogUtils.error(LOG_TAG, "Ошибка показа диалога обновления приложения!", e);
            }
        }
    }

    private void showDisableAdsDialog() {
        if (isFinishing())
            return;

        try {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.adb_disable_dialog_title)
                    .setPositiveButton(R.string.adb_disable_dialog_positive, (dialog, which) -> purchaseViewModel.buy(PurchaseType.ADS))
                    .setNeutralButton(R.string.adb_disable_dialog_neutral, (dialog, which) -> {})
                    .setCancelable(true)
                    .create()
                    .show();
        }
        catch (Exception e) {
            LogUtils.error(LOG_TAG, "Ошибка показа диалога отключения рекламы!", e);
        }
    }
}
