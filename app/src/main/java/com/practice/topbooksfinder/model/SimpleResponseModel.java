package com.practice.topbooksfinder.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimpleResponseModel<T> {

    @SerializedName("status")
    private String status = "";
    @SerializedName("num_results")
    private int numResults = 0;

    @SerializedName("results")
    private List<T> results;

    public String getStatus() {
        return status;
    }

    public int getNumResults() {
        return numResults;
    }

    public List<T> getResults() {
        return results;
    }
}
