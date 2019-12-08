package ru.nemodev.number.fact.ui.number.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.databinding.NumberFactCardViewBinding;
import ru.nemodev.number.fact.entity.number.NumberFact;


public class NumberFactAdapter extends PagedListAdapter<NumberFact, NumberFactAdapter.NumberFactViewHolder> {

    private static DiffUtil.ItemCallback<NumberFact> DIFF_CALLBACK = new DiffUtil.ItemCallback<NumberFact>() {
        @Override
        public boolean areItemsTheSame(@NonNull NumberFact oldItem, @NonNull NumberFact newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull NumberFact oldItem, @NonNull NumberFact newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    };

    private final Context context;
    private final OnNumberCardActionListener onNumberCardActionListener;

    public NumberFactAdapter(Context context, OnNumberCardActionListener onNumberCardActionListener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.onNumberCardActionListener = onNumberCardActionListener;
    }

    @NonNull
    @Override
    public NumberFactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberFactViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.number_fact_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NumberFactViewHolder holder, int position) {
        holder.binding.setOnNumberCardActionListener(onNumberCardActionListener);
        holder.binding.setNumberFact(getItem(position));
    }

    public static class NumberFactViewHolder extends RecyclerView.ViewHolder {
        private final NumberFactCardViewBinding binding;

        public NumberFactViewHolder(NumberFactCardViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
