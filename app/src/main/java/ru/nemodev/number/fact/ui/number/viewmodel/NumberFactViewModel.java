package ru.nemodev.number.fact.ui.number.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import ru.nemodev.number.fact.entity.NumberFact;
import ru.nemodev.number.fact.repository.db.room.AppDataBase;
import ru.nemodev.number.fact.ui.number.viewmodel.source.NumberFactDataSource;
import ru.nemodev.number.fact.ui.number.viewmodel.source.NumberFactRandomDataSource;


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

    public LiveData<PagedList<NumberFact>> getFact(LifecycleOwner lifecycleOwner, final String number) {

        if (numberFactList != null) {
            numberFactList.removeObservers(lifecycleOwner);
        }

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
