package ru.nemodev.numbers.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nemodev.numbers.R;
import ru.nemodev.numbers.ui.main.adapter.NumberFactAdapter;
import ru.nemodev.numbers.ui.main.viewmodel.NumberFactViewModel;


public class NumberFactFragment extends Fragment {

    @BindView(R.id.number_fact_rv)
    RecyclerView numberFactRV;
    @BindView(R.id.number_info_input)
    EditText numberInfoInput;
    @BindView(R.id.next_number_info)
    Button nextNumberInfo;
    @BindView(R.id.contentLoadingProgressBar)
    ProgressBar progressBar;

    private NumberFactViewModel numberFactViewModel;

    public static NumberFactFragment newInstance() {
        return new NumberFactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.number_fact_fragment, container, false);
        ButterKnife.bind(this, root);

        numberFactViewModel = ViewModelProviders.of(this).get(NumberFactViewModel.class);

        NumberFactAdapter numberFactAdapter = new NumberFactAdapter(this.getContext());
        numberFactRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        numberFactRV.setAdapter(numberFactAdapter);
        numberFactViewModel.getRandomFact().observe(this, numberFactAdapter::submitList);

        nextNumberInfo.setOnClickListener(v -> {
            if (StringUtils.isNotEmpty(numberInfoInput.getText().toString())) {
                numberFactViewModel.getFact(numberInfoInput.getText().toString())
                        .observe(this, numberFactAdapter::submitList);
            }
        });

        return root;
    }

    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        progressBar.setVisibility(View.GONE);
    }

    public void showMessage(String message) {

    }
}
