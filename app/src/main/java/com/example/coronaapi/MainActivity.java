package com.example.coronaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  {

    Button btnSearch;
    TextView tvResults;
    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ccp=findViewById(R.id.ccp);
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ccpCode=ccp.getSelectedCountryNameCode();
                tvResults=findViewById(R.id.tvResult);
                System.out.println("Hello World");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://corona-api.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                CoronaApi coronaApi = retrofit.create(CoronaApi.class);
                Call<ApiData> call = coronaApi.getApiData(ccpCode);


                call.enqueue(new Callback<ApiData>() {
                    @Override
                    public void onResponse(Call<ApiData> call, Response<ApiData> response) {
                        if(!response.isSuccessful()){
                            tvResults.setText("Code: "+ response.code());
                            return;
                        }

                        String content = "";
                        content += "Name: " + response.body().getData().getName() + "\n";
                        if(response.body().getData().getToday().getConfirmed() == 0 && response.body().getData().getToday().getDeaths() == 0){
                            content += "Today's confirmed cases are not announced yet \n";
                            content += "Today's confirmed deaths are not announced yet \n";
                        }else{
                            content += "Today Confirmed: " + response.body().getData().getToday().getConfirmed() + "\n";
                            content += "Today Deaths: " + response.body().getData().getToday().getDeaths() + "\n";
                        }
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
        });

    }
}