package com.example.coronaapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoronaApi {
    @GET("countries/GR")
    Call<ApiData> getApiData();
}
