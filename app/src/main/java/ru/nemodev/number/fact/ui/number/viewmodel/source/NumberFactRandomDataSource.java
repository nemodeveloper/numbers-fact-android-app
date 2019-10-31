package ru.nemodev.number.fact.ui.number.viewmodel.source;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import ru.nemodev.number.fact.entity.NumberFact;
import ru.nemodev.number.fact.repository.db.NumberFactRepository;

public class NumberFactRandomDataSource extends PositionalDataSource<NumberFact> {

    private final NumberFactRepository numberFactRepository;

    public NumberFactRandomDataSource(NumberFactRepository numberFactRepository) {
        this.numberFactRepository = numberFactRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<NumberFact> callback) {
        callback.onResult(numberFactRepository.getRandom(params.requestedLoadSize), params.requestedStartPosition);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<NumberFact> callback) {
        callback.onResult(numberFactRepository.getRandom(params.loadSize));
    }
}
