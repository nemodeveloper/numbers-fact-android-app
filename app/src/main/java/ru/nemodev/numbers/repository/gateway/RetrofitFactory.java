package ru.nemodev.numbers.repository.gateway;


import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitFactory {
    private static final int CONNECT_TIMEOUT = 10;
    private static final int WRITE_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 10;

    private static final String BASE_ENDPOINT = "http://numbersapi.com/";

    private static final OkHttpClient OK_HTTP_CLIENT = createHttpClient();

    private static final NumbersGateway numbersGateway = buildNumbersGateway();

    private static NumbersGateway buildNumbersGateway() {
        return buildRetrofit(BASE_ENDPOINT).create(NumbersGateway.class);
    }

    @NonNull
    private static OkHttpClient createHttpClient() {
        try {
            return new OkHttpClient.Builder()
                    .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.HTTP_1_1))
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private static Retrofit buildRetrofit(String endpoint) {
        return new Retrofit.Builder()
                .baseUrl(endpoint)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(buildJsonConvertFactory())
                .client(OK_HTTP_CLIENT)
                .build();
    }

    private static JacksonConverterFactory buildJsonConvertFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return JacksonConverterFactory.create(objectMapper);
    }

    public static NumbersGateway getNumbersGateway() {
        return numbersGateway;
    }
}
