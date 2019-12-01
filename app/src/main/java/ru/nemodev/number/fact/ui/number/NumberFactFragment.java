package ru.nemodev.number.fact.ui.number;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.analytic.AnalyticUtils;
import ru.nemodev.number.fact.databinding.RandomFactFragmentBinding;
import ru.nemodev.number.fact.ui.main.OnBackPressedListener;
import ru.nemodev.number.fact.ui.number.adapter.NumberFactAdapter;
import ru.nemodev.number.fact.ui.number.viewmodel.NumberFactViewModel;
import ru.nemodev.number.fact.ui.observable.RxEditTextObservable;
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

        // show/hide keyboard for input
        binding.numberInfoInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (getActivity() != null && !getActivity().isFinishing()) {
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyboard != null) {
                    if (hasFocus)
                        keyboard.showSoftInput(binding.numberInfoInput, 0);
                    else
                        keyboard.hideSoftInputFromWindow(binding.numberInfoInput.getWindowToken(), 0);
                }
            }
        });

        // update pull title
        binding.slidingUpPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    binding.pullViewTitle.setText(R.string.pull_down_input_number);
                    binding.numberInfoInput.requestFocus();
                }
                else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    binding.pullViewTitle.setText(R.string.pull_up_input_number);
                    binding.numberInfoInput.clearFocus();
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

        // input number event
        LiveDataReactiveStreams.fromPublisher(
                RxEditTextObservable.fromView(binding.numberInfoInput)
                        .debounce(600, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .toFlowable(BackpressureStrategy.BUFFER))
                .observe(this, inputNumber -> {
                    if (StringUtils.isNotEmpty(inputNumber)) {
                        numberFactViewModel.getFact(NumberFactFragment.this, inputNumber)
                                .observe(NumberFactFragment.this, numberFacts -> {
                                    AnalyticUtils.searchEvent(AnalyticUtils.SearchType.NUMBER_FACT, inputNumber);
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
