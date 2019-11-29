package ru.nemodev.number.fact.ui.number.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import ru.nemodev.number.fact.R;
import ru.nemodev.number.fact.entity.NumberFact;
import ru.nemodev.number.fact.utils.AndroidUtils;


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

    public NumberFactAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NumberFactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NumberFactViewHolder(
                (CardView)  LayoutInflater.from(context).inflate(
                        R.layout.number_fact_card_view, parent, false), context);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberFactViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class NumberFactViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final TextView numberInfoText;

        public NumberFactViewHolder(@NonNull CardView itemCardView, Context context) {
            super(itemCardView);
            this.context = context;
            this.numberInfoText = itemCardView.findViewById(R.id.number_info_text);
        }

        public void bind(NumberFact numberFact) {

            // TODO добавить кнопку поделиться + добавить отрисовку типа факта

            String result = numberFact.getNumber() + " is " + numberFact.getText();
            numberInfoText.setText(AndroidUtils.getColoredString(
                    result,
                    0, numberFact.getNumber().length(),
                    ContextCompat.getColor(context, R.color.colorPrimary)));
        }
    }
}
