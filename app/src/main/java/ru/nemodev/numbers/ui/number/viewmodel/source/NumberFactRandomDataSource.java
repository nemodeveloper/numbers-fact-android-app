package ru.nemodev.numbers.ui.number.viewmodel.source;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import ru.nemodev.numbers.entity.NumberFact;
import ru.nemodev.numbers.repository.db.NumberFactRepository;

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
