package com.practice.topbooksfinder.utils;

public class Consts {

    public final static int LOADER_ID = 0;

    // Configuration of a database

    public final static String DB_NAME = "top_books_search_app";
    public final static int DB_VERSION = 6;
    public final static String DB_COL_ID_PRIMARY = "_id";

    public final static String DB_TABLE_BOOK_LISTS = "book_lists";
    public final static String DB_BL_COL_ENCODE_NAME = "encode_name";
    public final static String DB_BL_COL_LIST_NAME = "name";
    public final static String DB_BL_COL_UPDATE = "updated_";
    public final static String DB_BL_COL_NEWEST_Published_Date = "newestPublishedDate";
    public final static String DB_BL_COL_OLDEST_Published_Date = "oldestPublishedDate";


    public final static String DB_TABLE_BOOKS = "books";
    public final static String DB_B_COL_DESCRIPTION = "description";
    public final static String DB_B_COL_TITLE = "title";
    public final static String DB_B_COL_AUTHOR = "author";
    public final static String DB_B_COL_CONTRIBUTOR = "contributor";
    public final static String DB_B_COL_PUBLISHER = "publisher";
    public final static String DB_B_COL_BOOK_IMAGE_URL = "book_image_url";
    public final static String DB_B_COL_ID13 = "isbn_13";
    public final static String DB_B_COL_RANK = "rank";
    public final static String DB_B_COL_AGE_GROUP = "age_group";

    /*

    "DROP TABLE IF EXISTS " + DB_TABLE_BOOK_LISTS +
     */


    public static final String DB_CREATE_TABLE_B =

            "create table " + DB_TABLE_BOOKS + "(" +
                    DB_COL_ID_PRIMARY + " integer primary key autoincrement, " +
                    DB_B_COL_DESCRIPTION + " text, " +
                    DB_B_COL_TITLE + " text," +
                    DB_B_COL_AUTHOR + " text," +
                    DB_B_COL_CONTRIBUTOR + " text," +
                    DB_B_COL_PUBLISHER + " text," +
                    DB_B_COL_BOOK_IMAGE_URL + " integer, " +
                    DB_B_COL_ID13 + " text," +
                    DB_B_COL_RANK + " integer," +
                    DB_B_COL_AGE_GROUP + " text," +
                    " UNIQUE ( " + DB_B_COL_ID13 + " ) ON CONFLICT IGNORE" +
                    ");";

    public static final String DB_CREATE_TABLE_BL =

            "create table " + DB_TABLE_BOOK_LISTS + "(" +
                    DB_COL_ID_PRIMARY + " integer primary key autoincrement, " +
                    DB_BL_COL_LIST_NAME + " text, " +
                    DB_BL_COL_ENCODE_NAME + " text, " +
                    DB_BL_COL_NEWEST_Published_Date + " text," +
                    DB_BL_COL_OLDEST_Published_Date + " text," +
                    DB_BL_COL_UPDATE + " text," +
                    " UNIQUE ( " + DB_BL_COL_LIST_NAME + " ) ON CONFLICT IGNORE" +
                    ");";

    public static final String DB_DELETE_ENTRIES_B =
                    " DROP TABLE IF EXISTS " + DB_TABLE_BOOKS;
    public static final String DB_DELETE_ENTRIES_BL =
                    " DROP TABLE IF EXISTS " + DB_TABLE_BOOK_LISTS;
}
