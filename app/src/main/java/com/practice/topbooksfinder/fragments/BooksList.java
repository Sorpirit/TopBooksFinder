package com.practice.topbooksfinder.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.activity.MainActivity;
import com.practice.topbooksfinder.adapters.BooksListRecyclerViewAdapter;
import com.practice.topbooksfinder.model.BookInfo;
import com.practice.topbooksfinder.model.ListInfoResponse;
import com.practice.topbooksfinder.rest.RestClient;
import com.practice.topbooksfinder.utils.Consts;
import com.practice.topbooksfinder.utils.Database;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksList extends Fragment {

    public static final String KEY_LIST_NAME = "list_encode_name";
    public static final String KEY_DATA = "data";

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private BooksListRecyclerViewAdapter adapter;

    private Database database;


    public BooksList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books_list, container, false);

        try {
            database = ((MainActivity) getActivity()).getDatabase();
        } catch (ClassCastException ex) {
            throw new ClassCastException(getActivity().toString() + " must have Database getter");
        }

        recyclerView = v.findViewById(R.id.rv_list);
        progressBar = v.findViewById(R.id.pb_loader);


        adapter = new BooksListRecyclerViewAdapter(v.getContext(), null);
        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildLayoutPosition(v);
                Cursor cursor = database.getData(Consts.DB_TABLE_BOOKS);
                if (cursor.moveToPosition(pos)) {
                    String amazonLink = cursor.getString(cursor.getColumnIndex(Consts.DB_B_COL_AMAZON_LINK));
                    if (amazonLink != null && !amazonLink.isEmpty()) {
                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(amazonLink)));
                    }
                }
            }
        });

        recyclerView.setAdapter(adapter);

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        if (savedInstanceState == null) {
            Bundle arg = getArguments();
            if (arg != null && arg.containsKey(KEY_LIST_NAME)) {

                String encodeName = arg.getString(KEY_LIST_NAME);
                String data = arg.containsKey(KEY_DATA) ? arg.getString(KEY_DATA) : null;

                loadBooks(encodeName, data);
            }
        } else {
            loadFromDatabase();
        }

        return v;
    }

    public void addItems(List<BookInfo> items, boolean clearList) {
        if (clearList) {
            database.clearData(Consts.DB_TABLE_BOOKS);
        }
        database.addBooks(items);
        loadFromDatabase();
    }

    public void loadFromDatabase() {
        adapter.swapCursor(database.getData(Consts.DB_TABLE_BOOKS));
        recyclerView.scheduleLayoutAnimation();
    }

    private void loadBooks(String listName, @Nullable String date) {
        String tempDate = date == null ? "current" : date;

        progressBar.setVisibility(View.VISIBLE);
        RestClient.getClient().getService().getListByDate(listName, tempDate).enqueue(new Callback<ListInfoResponse>() {
            @Override
            public void onResponse(@NotNull Call<ListInfoResponse> call, @NotNull Response<ListInfoResponse> response) {
                if (response.isSuccessful()) {

                    addItems(response.body().getResults().getBooks(), true);
                }

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(@NotNull Call<ListInfoResponse> call, @NotNull Throwable t) {
                t.printStackTrace();

                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
