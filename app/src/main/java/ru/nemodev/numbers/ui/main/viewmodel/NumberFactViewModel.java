package ru.nemodev.numbers.ui.main.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import ru.nemodev.numbers.entity.NumberFact;
import ru.nemodev.numbers.repository.db.room.AppDataBase;
import ru.nemodev.numbers.ui.main.viewmodel.source.NumberFactDataSource;
import ru.nemodev.numbers.ui.main.viewmodel.source.NumberFactRandomDataSource;


public class NumberFactViewModel extends ViewModel {

    public LiveData<PagedList<NumberFact>> randomNumberFactList;
    public LiveData<PagedList<NumberFact>> numberFactList;

    public LiveData<PagedList<NumberFact>> getRandomFact() {
        if (randomNumberFactList == null) {

            DataSource.Factory<Integer, NumberFact> factFactory = new DataSource.Factory<Integer, NumberFact>() {
                @NonNull
                @Override
                public DataSource<Integer, NumberFact> create() {
                    return new NumberFactRandomDataSource(AppDataBase.getInstance().getNumberFactRepository());
                }
            };

            randomNumberFactList = new LivePagedListBuilder<>(
                    factFactory,
                    new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .setPrefetchDistance(5)
                        .build())
                .build();
        }

        return randomNumberFactList;
    }

    public LiveData<PagedList<NumberFact>> getFact(final String number) {

        DataSource.Factory<Integer, NumberFact> factFactory = new DataSource.Factory<Integer, NumberFact>() {
            @NonNull
            @Override
            public DataSource<Integer, NumberFact> create() {
                return new NumberFactDataSource(number, AppDataBase.getInstance().getNumberFactRepository());
            }
        };

        numberFactList = new LivePagedListBuilder<>(
                factFactory,
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(10)
                        .setPrefetchDistance(10)
                        .build())
                .build();

        return numberFactList;
    }
}
