package ru.nemodev.number.fact.ui.number;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.ui.main.OnBackPressedListener;
import ru.nemodev.number.fact.ui.number.adapter.NumberFactAdapter;
import ru.nemodev.number.fact.ui.number.viewmodel.NumberFactViewModel;
import ru.nemodev.number.fact.utils.AndroidUtils;


public class NumberFactFragment extends Fragment implements OnBackPressedListener {

    @BindView(R.id.rand_number_fact_rv) RecyclerView randomFactRV;
    @BindView(R.id.contentLoadingProgressBar) ProgressBar progressBar;

    @BindView(R.id.pull_view_title) TextView pullViewTitle;
    @BindView(R.id.number_fact_rv) RecyclerView numberFactRV;
    @BindView(R.id.number_info_input) EditText numberInfoInput;
    @BindView(R.id.next_number_info) Button nextNumberInfoButton;

    private SlidingUpPanelLayout root;
    private NumberFactViewModel numberFactViewModel;

    public static NumberFactFragment newInstance() {
        return new NumberFactFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root = (SlidingUpPanelLayout) inflater.inflate(R.layout.random_fact_fragment, container, false);
        ButterKnife.bind(this, root);

        numberFactViewModel = ViewModelProviders.of(getActivity()).get(NumberFactViewModel.class);

        root.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel,
                                            SlidingUpPanelLayout.PanelState previousState,
                                            SlidingUpPanelLayout.PanelState newState) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    pullViewTitle.setText(R.string.pull_down_input_number);
                }
                else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    pullViewTitle.setText(R.string.pull_up_input_number);
                }
            }
        });

        // random
        NumberFactAdapter randNumberFactAdapter = new NumberFactAdapter(this.getContext());
        randomFactRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        randomFactRV.setAdapter(randNumberFactAdapter);
        numberFactViewModel.getRandomFact().observe(this, randNumberFactAdapter::submitList);

        // custom
        NumberFactAdapter numberFactAdapter = new NumberFactAdapter(this.getContext());
        numberFactRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        numberFactRV.setAdapter(numberFactAdapter);

        nextNumberInfoButton.setOnClickListener(v -> {
            if (StringUtils.isNotEmpty(numberInfoInput.getText().toString())) {
                numberFactViewModel.getFact(this, numberInfoInput.getText().toString())
                        .observe(this, numberFactAdapter::submitList);
            }
            else {
                AndroidUtils.showSnackBarMessage(root, R.string.empty_input_number);
            }
        });

        return root;
    }

    @Override
    public boolean onBackPressed() {
        if (root.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED
                || root.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED) {
            root.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return true;
        }
        return false;
    }

}
