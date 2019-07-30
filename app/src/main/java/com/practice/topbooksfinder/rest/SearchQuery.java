package com.practice.topbooksfinder.rest;

import org.jetbrains.annotations.NotNull;

public enum SearchQuery {

    TITLE("title"),
    AUTHOR("author"),
    ISBN("isbn"),
    PUBLISHER("publisher");

    private String query;

    SearchQuery(String query) {
        this.query = query;
    }

    @NotNull
    @Override
    public String toString() {
        return query;
    }


}
