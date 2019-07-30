package com.practice.topbooksfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.adapters.BooksListRecyclerViewAdapter;
import com.practice.topbooksfinder.model.BookInfo;
import com.practice.topbooksfinder.utils.Consts;
import com.practice.topbooksfinder.utils.Database;

import java.util.ArrayList;
import java.util.List;

public class BooksList extends Fragment {

    private RecyclerView recyclerView;
    private BooksListRecyclerViewAdapter adapter;
    private Database database;


    public BooksList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_books_list, container, false);

        recyclerView = v.findViewById(R.id.rv_list);

        database = Database.getInstanse();

        adapter = new BooksListRecyclerViewAdapter(v.getContext(),database.getData(Consts.DB_TABLE_BOOKS));

        recyclerView.setAdapter(adapter);

        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);

        return v;
    }

    public void addItems(List<BookInfo> items, boolean clearList){
        if(clearList){
            database.clearData(Consts.DB_TABLE_BOOKS);
        }
        database.addBooks(items);
        adapter.swapCursor(database.getData(Consts.DB_TABLE_BOOKS));
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
