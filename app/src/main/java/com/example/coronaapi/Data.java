package com.example.coronaapi;

import com.google.gson.annotations.SerializedName;

public class Data {
    private String name;
    private ApiDataToday today;
    @SerializedName("latest_data")
    private LatestData latestData;

    public String getName() {
        return name;
    }

    public ApiDataToday getToday() {
        return today;
    }

    public LatestData getLatestData() {
        return latestData;
    }
}
