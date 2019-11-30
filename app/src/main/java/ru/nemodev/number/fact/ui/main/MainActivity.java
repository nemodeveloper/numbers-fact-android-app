package ru.nemodev.number.fact.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.ui.main.viewmodel.UpdateAppViewModel;
import ru.nemodev.number.fact.ui.main.viewmodel.UpdateAppViewModelFactory;
import ru.nemodev.number.fact.ui.number.NumberFactFragment;
import ru.nemodev.number.fact.utils.LogUtils;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private UpdateAppViewModel updateAppViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // TODO интеграция рекламы
        // TODO использовать навигацию

        updateAppViewModel = ViewModelProviders.of(this, new UpdateAppViewModelFactory(this)).get(UpdateAppViewModel.class);
        updateAppViewModel.getUpdateAppEvent().observe(this, installState -> showUpdateDialog());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NumberFactFragment.newInstance())
                    .commitNow();
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().getFragments().get(0);
        boolean needCallSuper = true;
        if (currentFragment instanceof OnBackPressedListener) {
            needCallSuper = !((OnBackPressedListener) currentFragment).onBackPressed();
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
}
