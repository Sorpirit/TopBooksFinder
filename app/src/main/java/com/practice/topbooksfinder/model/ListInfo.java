package com.practice.topbooksfinder.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListInfo {

    @SerializedName("display_name")
    private String listName;
    @SerializedName("list_name_encoded")
    private String encodeName;

    @SerializedName("newest_published_date")
    private String newestPublishedDate;
    @SerializedName("oldest_published_date")
    private String oldestPublishedDate;
    @SerializedName("updated")
    private String update;

    //Костыль для api
    @SerializedName("book_details")
    private List<BookInfo> booksD;
    @SerializedName("books")
    private List<BookInfo> books;

    public ListInfo(String listName,String encodeName, String newestPublishedDate, String oldestPublishedDate, String update, List<BookInfo> booksD, List<BookInfo> books) {
        this.listName = listName;
        this.encodeName = encodeName;
        this.newestPublishedDate = newestPublishedDate;
        this.oldestPublishedDate = oldestPublishedDate;
        this.update = update;
        this.booksD = booksD;
        this.books = books;
    }

    public List<BookInfo> getBooks() {
        return books == null ? booksD : books;
    }

    public String getListName() {
        return listName;
    }
    @Nullable
    public String getNewestPublishedDate() {
        return newestPublishedDate;
    }
    @Nullable
    public String getOldestPublishedDate() {
        return oldestPublishedDate;
    }
    @Nullable
    public String getUpdate() {
        return update;
    }

    public String getEncodeName() {
        return encodeName;
    }

    @Override
    public String toString() {
        return "ListInfo{" +
                "listName='" + listName + '\'' +
                ", newestPublishedDate='" + newestPublishedDate + '\'' +
                ", oldestPublishedDate='" + oldestPublishedDate + '\'' +
                ", update='" + update + '\'' +
                ", books=" + books +
                '}';
    }
}
