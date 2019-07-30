package com.practice.topbooksfinder.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class BookInfo {

    @SerializedName("title")
    private String title;

    @SerializedName("author")
    private String author;
    @SerializedName("contributor")
    private String contributor;
    @SerializedName("publisher")
    private String publisher;

    @SerializedName("age_group")
    private String ageGroup;

    @SerializedName("description")
    private String description;

    @SerializedName("rank")
    private int rank;

    @SerializedName("book_image")
    private String bookImageUrl;

    @SerializedName("primary_isbn13")
    private String id13;


    public BookInfo(String title, String author, String contributor, String publisher,
                    String ageGroup, String description, int rank, String bookImageUrl,
                    String id13) {

        this.title = title;
        this.author = author;
        this.contributor = contributor;
        this.publisher = publisher;
        this.ageGroup = ageGroup;
        this.description = description;
        this.rank = rank;
        this.bookImageUrl = bookImageUrl;
        this.id13 = id13;
    }


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    @Nullable
    public String getContributor() {
        return contributor;
    }
    @Nullable
    public String getPublisher() {
        return publisher;
    }
    @Nullable
    public String getAgeGroup() {
        return ageGroup;
    }

    public String getDescription() {
        return description;
    }

    public int getRank() {
        return rank;
    }
    @Nullable
    public String getBookImageUrl() {
        return bookImageUrl;
    }
    @Nullable
    public String getId13() {
        return id13;
    }


    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", contributor='" + contributor + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                ", description='" + description + '\'' +
                ", rank=" + rank +
                ", bookImageUrl='" + bookImageUrl + '\'' +
                ", id13='" + id13 + '\'' +
                '}';
    }
}
