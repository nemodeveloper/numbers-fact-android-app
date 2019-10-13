package ru.nemodev.numbers.repository.gateway;

import io.reactivex.Observable;
import ru.nemodev.numbers.entity.NumberFact;

public class NumberFactGatewayRepository {

    public Observable<NumberFact> getRandomFact() {
        return getFact(null);
    }

    public Observable<NumberFact> getFact(String number) {

        return null;
    }

    private NumberFact convert(NumberFactDto numberFactDto)
    {
        return null;
    }

}
