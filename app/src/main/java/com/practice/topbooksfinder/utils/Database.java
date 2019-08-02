package com.practice.topbooksfinder.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.practice.topbooksfinder.model.BookInfo;
import com.practice.topbooksfinder.model.ListInfo;

import java.util.List;

public class Database {

    private Context ctx;

    private DBHelper dbHelper;

    private SQLiteDatabase mDB;

    public Database(Context ctx) {
        this.ctx = ctx;
    }


    public void open() {
        dbHelper = new DBHelper(ctx, Consts.DB_NAME, null, Consts.DB_VERSION);
        mDB = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor getData(String tableName) {
        return mDB.query(tableName, null, null, null, null, null, Consts.DB_COL_ID_PRIMARY + " DESC");
    }

    public void clearData(String tableName) {
        mDB.delete(tableName, null, null);
    }

    public void clearAllData() {
        mDB.delete(Consts.DB_TABLE_BOOKS, null, null);
        mDB.delete(Consts.DB_TABLE_BOOK_LISTS, null, null);
    }

    private void addBook(BookInfo item) {
        ContentValues cv = new ContentValues();
        cv.put(Consts.DB_B_COL_DESCRIPTION, item.getDescription());
        cv.put(Consts.DB_B_COL_TITLE, item.getTitle());
        cv.put(Consts.DB_B_COL_AUTHOR, item.getAuthor());
        cv.put(Consts.DB_B_COL_CONTRIBUTOR, item.getContributor());
        cv.put(Consts.DB_B_COL_AGE_GROUP, item.getAgeGroup());
        cv.put(Consts.DB_B_COL_RANK, item.getRank());
        cv.put(Consts.DB_B_COL_BOOK_IMAGE_URL, item.getBookImageUrl());
        cv.put(Consts.DB_B_COL_ID13, item.getId13());
        cv.put(Consts.DB_B_COL_PUBLISHER, item.getPublisher());
        cv.put(Consts.DB_B_COL_AMAZON_LINK, item.getAmazonLink());

        mDB.insert(Consts.DB_TABLE_BOOKS, null, cv);
    }

    private void addBookList(ListInfo item) {
        ContentValues cv = new ContentValues();
        cv.put(Consts.DB_BL_COL_ENCODE_NAME, item.getEncodeName());
        cv.put(Consts.DB_BL_COL_LIST_NAME, item.getListName());
        cv.put(Consts.DB_BL_COL_NEWEST_Published_Date, item.getNewestPublishedDate());
        cv.put(Consts.DB_BL_COL_OLDEST_Published_Date, item.getOldestPublishedDate());
        cv.put(Consts.DB_BL_COL_UPDATE, item.getUpdate());

        mDB.insert(Consts.DB_TABLE_BOOK_LISTS, null, cv);
    }

    public void addBooks(List<BookInfo> items) {
        if (items.size() != 0) {
            for (int i = items.size() - 1; i >= 0; i--) {
                addBook(items.get(i));
            }
        }
    }

    public void addBookLists(List<ListInfo> items) {
        if (items.size() != 0) {
            for (int i = items.size() - 1; i >= 0; i--) {
                addBookList(items.get(i));
            }
        }
    }


    /**
     * Subclass of {@link android.database.sqlite.SQLiteOpenHelper} which provides custom database helper.
     */
    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Consts.DB_CREATE_TABLE_B);
            db.execSQL(Consts.DB_CREATE_TABLE_BL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Consts.DB_DELETE_ENTRIES_B);
            db.execSQL(Consts.DB_DELETE_ENTRIES_BL);
            onCreate(db);
        }
    }
}
