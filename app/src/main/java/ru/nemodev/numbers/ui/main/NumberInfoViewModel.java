package ru.nemodev.numbers.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.nemodev.numbers.entity.NumberInfo;
import ru.nemodev.numbers.repository.NumberInfoRepository;

public class NumberInfoViewModel extends ViewModel {

    private MutableLiveData<NumberInfo> randomNumberFact;
    private MutableLiveData<NumberInfo> numberFact;

    private NumberInfoRepository numberInfoRepository;

    public void init() {

        if (numberInfoRepository != null)
            return;

        numberInfoRepository = new NumberInfoRepository();
    }

    public LiveData<NumberInfo> getRandomFact() {
        if (randomNumberFact == null) {
            randomNumberFact = new MutableLiveData<>();
        }

        numberInfoRepository.getRandomFact()
                .subscribe(numberInfo -> randomNumberFact.postValue(numberInfo), throwable -> {});

        return randomNumberFact;
    }

    public LiveData<NumberInfo> getFact(String number) {
        if (numberFact == null) {
            numberFact = new MutableLiveData<>();
        }

        numberInfoRepository.getFact(number)
                .subscribe(numberInfo -> randomNumberFact.postValue(numberInfo), throwable -> {});

        return numberFact;
    }
}
