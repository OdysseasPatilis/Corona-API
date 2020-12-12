package com.example.coronaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    TextView tvResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResults=findViewById(R.id.tvResult);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://corona-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoronaApi coronaApi = retrofit.create(CoronaApi.class);
        Call<ApiData> call = coronaApi.getApiData();


        call.enqueue(new Callback<ApiData>() {
            @Override
            public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                if(!response.isSuccessful()){
                    tvResults.setText("Code: "+ response.code());
                    return;
                }

                    String content = "";
                    //content += "Date: " + apiDatas.getData().getTimeline().getDate() + "\n";
                    content += "Name: " + response.body().getData().getName() + "\n";
                    content += "Today Confirmed: " + response.body().getData().getToday().getConfirmed() + "\n";
                    content += "Today Deaths: " + response.body().getData().getToday().getDeaths() + "\n";
                    content += "Overall Positives: " + response.body().getData().getLatestData().getConfirmed() + "\n";
                    content += "Overall Recovered: " + response.body().getData().getLatestData().getRecovered() + "\n";
                    content += "Overall Deaths: " + response.body().getData().getLatestData().getDeaths() + "\n";
                    tvResults.setText(content);
            }

            @Override
            public void onFailure(Call<ApiData> call, Throwable t) {
                tvResults.setText(t.getMessage());
            }
        });
    }
}