package com.practice.topbooksfinder.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.adapters.BookListsRecyclerViewAdapter;
import com.practice.topbooksfinder.fragments.BookListsFragment;
import com.practice.topbooksfinder.fragments.BooksList;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.model.ListInfoResponse;
import com.practice.topbooksfinder.model.SimpleResponseModel;
import com.practice.topbooksfinder.rest.RestClient;
import com.practice.topbooksfinder.rest.SearchQuery;
import com.practice.topbooksfinder.utils.Consts;
import com.practice.topbooksfinder.utils.Database;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;

    private static final BooksList booksList = new BooksList();
    private static final BookListsFragment lists = new BookListsFragment();
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Database.creatDatabase(this).open();

        progressBar = findViewById(R.id.pb_loader);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.screen, lists);
        fragmentTransaction.commit();



        lists.setListener(new BookListsFragment.OnListInfoClick() {
            @Override
            public void onClick(String encode) {

                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.screen, booksList);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                loadBooks(encode,null);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        loadLists();
    }

    private void loadBooks(String listName, @Nullable String date){
        String tempDate = date==null ? "current" : date;

        progressBar.setVisibility(View.VISIBLE);
        RestClient.getClient().getService().getListByDate(listName,tempDate).enqueue(new Callback<ListInfoResponse>() {
            @Override
            public void onResponse(Call<ListInfoResponse> call, Response<ListInfoResponse> response) {
                if(response.isSuccessful()){

                    booksList.addItems(response.body().getResults().getBooks(),true);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ListInfoResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadLists(){
        progressBar.setVisibility(View.VISIBLE);
        RestClient.getClient().getService().getLists().enqueue(new Callback<SimpleResponseModel<ListInfo>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ListInfo>> call, Response<SimpleResponseModel<ListInfo>> response) {
                if(response.isSuccessful()){
                    for(ListInfo info:response.body().getResults()){
                        Log.i("encode",info.getEncodeName());
                    }
                    lists.addItems(response.body().getResults(),true);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ListInfo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Database.getInstanse().close();
    }
}
