package ru.nemodev.numbers.repository;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.nemodev.numbers.entity.NumberInfo;
import ru.nemodev.numbers.entity.NumberType;
import ru.nemodev.numbers.repository.gateway.NumberInfoDto;
import ru.nemodev.numbers.repository.gateway.RetrofitFactory;

public class NumberInfoRepository {

    public Observable<NumberInfo> getRandomFact() {
        return getFact(null);
    }

    public Observable<NumberInfo> getFact(String number) {

        Observable<NumberInfoDto> response;

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("json", "true");
        queryParams.put("default", "Boring number is boring");
        queryParams.put("fragment", "true");

        if (StringUtils.isNotBlank(number))
            response = RetrofitFactory.getNumbersGateway()
                    .getNumberInfo(number, NumberType.getRandomValue(), queryParams);
        else
            response = RetrofitFactory.getNumbersGateway()
                    .getRandomByType(NumberType.getRandomValue(), queryParams);

        return response.subscribeOn(Schedulers.io())
                .map(this::convert)
                .observeOn(AndroidSchedulers.mainThread());
    }

    private NumberInfo convert(NumberInfoDto numberInfoDto)
    {
        NumberInfo numberInfo = new NumberInfo();
        numberInfo.setNumber(numberInfoDto.getNumber());
        numberInfo.setType(numberInfoDto.getType());
        numberInfo.setText(numberInfoDto.getText());

        return numberInfo;
    }

}
