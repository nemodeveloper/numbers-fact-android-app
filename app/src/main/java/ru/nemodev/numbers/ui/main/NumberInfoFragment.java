package ru.nemodev.numbers.ui.main;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nemodev.numbers.R;
import ru.nemodev.numbers.entity.NumberInfo;
import ru.nemodev.numbers.utils.AndroidUtils;


public class NumberInfoFragment extends Fragment {

    @BindView(R.id.number_info_text)
    TextView numberInfoText;
    @BindView(R.id.number_info_input)
    EditText numberInfoInput;
    @BindView(R.id.next_number_info)
    Button nextNumberInfo;
    @BindView(R.id.contentLoadingProgressBar)
    ProgressBar progressBar;

    private NumberInfoViewModel numberInfoViewModel;

    public static NumberInfoFragment newInstance() {
        return new NumberInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.number_info_fragment, container, false);
        ButterKnife.bind(this, root);

        numberInfoViewModel = ViewModelProviders.of(this).get(NumberInfoViewModel.class);
        numberInfoViewModel.init();

        showLoader();
        numberInfoViewModel.getRandomFact().observe(this, this::showNumberInfo);

        nextNumberInfo.setOnClickListener(v -> {
                showLoader();
                numberInfoViewModel.getFact(numberInfoInput.getText().toString())
                        .observe(this, this::showNumberInfo);
        });

        return root;
    }

    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    public void showNumberInfo(NumberInfo numberInfo) {
        hideLoader();

        String result = numberInfo.getNumber() + " is " + numberInfo.getText();
        this.numberInfoText.setText(AndroidUtils.getColoredString(
                result,
                0, numberInfo.getNumber().length(),
                ContextCompat.getColor(getContext(), R.color.colorPrimary)));
    }

    public void showMessage(String message) {

    }
}
