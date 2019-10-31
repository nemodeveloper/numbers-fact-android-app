package ru.nemodev.number.fact.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.ui.number.NumberFactFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

//        BannerManager bannerManager = new BannerManager(this,
//                Arrays.asList(
//                    new SimpleBanner(findViewById(R.id.adView))
//                ));

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
}
