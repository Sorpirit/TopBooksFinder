package com.practice.topbooksfinder.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.fragments.BookListsFragment;
import com.practice.topbooksfinder.fragments.BooksList;
import com.practice.topbooksfinder.utils.Database;

public class MainActivity extends AppCompatActivity implements BookListsFragment.OnListInfoClick {

    //Fragment Controls
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);
        database.open();

        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {
            BookListsFragment bookListsFragment = new BookListsFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.screen, bookListsFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
            fragmentTransaction.commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            fragmentManager.popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        return super.onOptionsItemSelected(item);

    }

    public Database getDatabase() {
        return database;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    @Override
    public void onClick(String listEncodeName) {
        BooksList booksList = new BooksList();
        Bundle bundle = new Bundle();
        bundle.putString(BooksList.KEY_LIST_NAME, listEncodeName);
        booksList.setArguments(bundle);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.screen, booksList);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
