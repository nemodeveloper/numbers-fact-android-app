package ru.nemodev.number.fact.ui.number.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import ru.nemodev.number.fact.entity.NumberFact;
import ru.nemodev.number.fact.repository.db.room.AppDataBase;


public class NumberFactViewModel extends ViewModel {

    private LiveData<PagedList<NumberFact>> randomNumberFactList;
    private LiveData<PagedList<NumberFact>> numberFactList;

    public LiveData<PagedList<NumberFact>> getRandomFact() {
        if (randomNumberFactList == null) {
            randomNumberFactList = new LivePagedListBuilder<>(
                    AppDataBase.getInstance().getNumberFactRepository().getRandom(),
                    new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(50)
                        .setPrefetchDistance(25)
                        .build())
                .build();
        }

        return randomNumberFactList;
    }

    public LiveData<PagedList<NumberFact>> getFact(LifecycleOwner lifecycleOwner, final String number) {

        if (numberFactList != null) {
            numberFactList.removeObservers(lifecycleOwner);
        }

        numberFactList = new LivePagedListBuilder<>(
                AppDataBase.getInstance().getNumberFactRepository().getByNumber(number),
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(50)
                        .setPrefetchDistance(25)
                        .build())
                .build();

        return numberFactList;
    }
}
