package ru.nemodev.numbers.ui.number.viewmodel.source;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;

import ru.nemodev.numbers.entity.NumberFact;
import ru.nemodev.numbers.repository.db.NumberFactRepository;

public class NumberFactDataSource extends PositionalDataSource<NumberFact> {

    private final NumberFactRepository numberFactRepository;
    private final String number;

    public NumberFactDataSource(String number, NumberFactRepository numberFactRepository) {
        this.number = number;
        this.numberFactRepository = numberFactRepository;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<NumberFact> callback) {

        List<NumberFact> numberFactList = numberFactRepository.getByNumber(number);

        if (CollectionUtils.isEmpty(numberFactList)) {
            callback.onResult(Collections.emptyList(), 0);
        }
        else {
            callback.onResult(numberFactList, params.requestedStartPosition);
        }
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<NumberFact> callback) {
        callback.onResult(Collections.emptyList());
    }
}
