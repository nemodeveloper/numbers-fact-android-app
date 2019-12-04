package ru.nemodev.number.fact.ui.number;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
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
import ru.nemodev.number.fact.databinding.NumberFactFragmentBinding;
import ru.nemodev.number.fact.entity.NumberFact;
import ru.nemodev.number.fact.ui.base.BaseFragment;
import ru.nemodev.number.fact.ui.main.OnBackPressedListener;
import ru.nemodev.number.fact.ui.number.adapter.NumberFactAdapter;
import ru.nemodev.number.fact.ui.number.adapter.OnNumberCardActionListener;
import ru.nemodev.number.fact.ui.number.viewmodel.NumberFactViewModel;
import ru.nemodev.number.fact.ui.observable.RxEditTextObservable;
import ru.nemodev.number.fact.utils.AndroidUtils;
import ru.nemodev.number.fact.utils.NumberFactUtils;


public class NumberFactFragment extends BaseFragment implements OnBackPressedListener, OnNumberCardActionListener {

    private NumberFactFragmentBinding binding;
    private NumberFactViewModel numberFactViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.number_fact_fragment, container, false);
        numberFactViewModel = ViewModelProviders.of(this).get(NumberFactViewModel.class);

        showLoader();

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

        // update pull view
        binding.slidingUpPanel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

            private SlidingUpPanelLayout.PanelState lastPanelState = SlidingUpPanelLayout.PanelState.COLLAPSED;

            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (getActivity() != null) {
                    getActivity().getWindow().setStatusBarColor(
                            lastPanelState == SlidingUpPanelLayout.PanelState.COLLAPSED
                                    ? Color.WHITE
                                    : getResources().getColor(R.color.mainBackground, null));
                }
            }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    lastPanelState = newState;
                    binding.pullViewTitle.setText(R.string.pull_down_input_number);
                    binding.numberInfoInput.requestFocus();
                }
                else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    lastPanelState = newState;
                    binding.pullViewTitle.setText(R.string.pull_up_input_number);
                    binding.numberInfoInput.clearFocus();
                }

                if (getActivity() != null) {
                    if (lastPanelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.mainBackground, null));
                    }
                    else if (lastPanelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        getActivity().getWindow().setStatusBarColor(Color.WHITE);
                    }
                }
            }
        });

        // random facts
        NumberFactAdapter randNumberFactAdapter = new NumberFactAdapter(getContext(), this);
        binding.randNumberFactRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.randNumberFactRv.setAdapter(randNumberFactAdapter);
        numberFactViewModel.getRandomFact().observe(this,
                numberFacts -> randNumberFactAdapter.submitList(numberFacts, this::hideLoader));

        // number facts
        NumberFactAdapter numberFactAdapter = new NumberFactAdapter(this.getContext(), this);
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
                        showLoader();
                        numberFactViewModel.getFact(NumberFactFragment.this, inputNumber)
                                .observe(NumberFactFragment.this, numberFacts -> {
                                    AnalyticUtils.searchEvent(AnalyticUtils.SearchType.NUMBER_FACT, inputNumber);
                                    if (CollectionUtils.isEmpty(numberFacts)) {
                                        AndroidUtils.showSnackBarMessage(binding.getRoot(), String.format(
                                                AndroidUtils.getString(R.string.number_facts_not_found),
                                                binding.numberInfoInput.getText().toString()));
                                    }
                                    hideLoader();
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

    @Override
    public void onCopyClick(NumberFact numberFact) {
        AnalyticUtils.shareEvent(AnalyticUtils.ShareType.NUMBER_FACT_COPY, numberFact.getNumber());
        AndroidUtils.copyTextToClipBoard(NumberFactUtils.getVerboseFactText(numberFact));
        AndroidUtils.showSnackBarMessage(binding.getRoot(), R.string.number_fact_action_copy);
    }

    @Override
    public void onShareClick(NumberFact numberFact) {
        if (getActivity() != null) {
            AnalyticUtils.shareEvent(AnalyticUtils.ShareType.NUMBER_FACT, numberFact.getNumber());
            AndroidUtils.openShareDialog(getActivity(),
                    AndroidUtils.getString(R.string.share_number_fact_dialog_title),
                    NumberFactUtils.getVerboseFactText(numberFact));
        }
    }
}
