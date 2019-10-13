package ru.nemodev.numbers.repository.gateway;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface NumberFactGateway {

    @GET("/list/random")
    Observable<List<NumberFactDto>> getRandom();

    @GET("/list/{number}")
    Observable<List<NumberFactDto>> getByNumber(@Path("number") String number);
}
