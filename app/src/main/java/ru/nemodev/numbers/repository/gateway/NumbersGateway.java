package ru.nemodev.numbers.repository.gateway;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


public interface NumbersGateway {
    @GET("random/{numberType}")
    Observable<NumberInfoDto> getRandomByType(@Path("numberType") String numberType, @QueryMap Map<String, String> queryParams);

    @GET("{number}/{numberType}")
    Observable<NumberInfoDto> getNumberInfo(@Path("number") String number, @Path("numberType") String numberType, @QueryMap Map<String, String> queryParams);
}
