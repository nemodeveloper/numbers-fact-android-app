package ru.nemodev.number.fact.ui.number;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.databinding.RandomFactFragmentBinding;
import ru.nemodev.number.fact.ui.main.OnBackPressedListener;
import ru.nemodev.number.fact.ui.number.adapter.NumberFactAdapter;
import ru.nemodev.number.fact.ui.number.viewmodel.NumberFactViewModel;
import ru.nemodev.number.fact.utils.AndroidUtils;


public class NumberFactFragment extends Fragment implements OnBackPressedListener {

    private RandomFactFragmentBinding binding;
    private NumberFactViewModel numberFactViewModel;

    public static NumberFactFragment newInstance() {
        return new NumberFactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.random_fact_fragment, container, false);
        numberFactViewModel = ViewModelProviders.of(this).get(NumberFactViewModel.class);

        binding.slidingUpPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    binding.pullViewTitle.setText(R.string.pull_down_input_number);
                    // TODO устаналивать фокус на ввод числа
                }
                else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    binding.pullViewTitle.setText(R.string.pull_up_input_number);
                    // TODO убирать фокус на ввод числа
                }
            }
        });

        // random facts
        NumberFactAdapter randNumberFactAdapter = new NumberFactAdapter(getContext());
        binding.randNumberFactRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.randNumberFactRv.setAdapter(randNumberFactAdapter);
        numberFactViewModel.getRandomFact().observe(this, randNumberFactAdapter::submitList);

        // number facts
        NumberFactAdapter numberFactAdapter = new NumberFactAdapter(this.getContext());
        binding.numberFactRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.numberFactRv.setAdapter(numberFactAdapter);

        // input number
        binding.numberInfoInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isNotEmpty(s.toString())) {
                    numberFactViewModel.getFact(NumberFactFragment.this, s.toString())
                            .observe(NumberFactFragment.this, numberFacts -> {
                                if (CollectionUtils.isEmpty(numberFacts)) {
                                    AndroidUtils.showSnackBarMessage(binding.getRoot(), String.format(
                                            AndroidUtils.getString(R.string.number_facts_not_found),
                                            binding.numberInfoInput.getText().toString()));
                                }
                                numberFactAdapter.submitList(numberFacts);
                            });
                }
                else {
                    AndroidUtils.showSnackBarMessage(binding.getRoot(), R.string.empty_input_number);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        if (binding.slidingUpPanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                || binding.slidingUpPanel.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
            binding.slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        }
        return false;
    }

}
