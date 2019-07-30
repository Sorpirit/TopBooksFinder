package com.practice.topbooksfinder.model;

import com.google.gson.annotations.SerializedName;

public class ListInfoResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("num_results")
    private int numResults;

    @SerializedName("results")
    private ListInfo results;

    public String getStatus() {
        return status;
    }

    public int getNumResults() {
        return numResults;
    }

    public ListInfo getResults() {
        return results;
    }
}
