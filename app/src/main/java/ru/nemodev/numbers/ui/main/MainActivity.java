package ru.nemodev.numbers.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.nemodev.numbers.R;
import ru.nemodev.numbers.ui.number.NumberFactFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NumberFactFragment.newInstance())
                    .commitNow();
        }
    }
}
