package com.example.coronaapi;
import com.example.coronaapi.MainActivity.*;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoronaApi {

    @GET("countries/{country}")
    Call<ApiData> getApiData(
            @Path("country") String country
    );
}
