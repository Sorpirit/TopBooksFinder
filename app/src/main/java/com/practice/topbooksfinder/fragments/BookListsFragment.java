package com.practice.topbooksfinder.fragments;

import android.content.Context;
import android.database.Cursor;
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
import com.practice.topbooksfinder.activity.MainActivity;
import com.practice.topbooksfinder.adapters.BookListsRecyclerViewAdapter;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.model.SimpleResponseModel;
import com.practice.topbooksfinder.rest.RestClient;
import com.practice.topbooksfinder.utils.Consts;
import com.practice.topbooksfinder.utils.Database;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookListsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private BookListsRecyclerViewAdapter adapter;

    private Database database;

    private OnListInfoClick listener;

    public BookListsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_lsits, container, false);

        try {
            listener = (MainActivity) getActivity();
            database = ((MainActivity) getActivity()).getDatabase();
        } catch (ClassCastException ex) {
            throw new ClassCastException(getActivity().toString() + " must implement OnListInfoClick + Database getter");
        }

        recyclerView = view.findViewById(R.id.list);
        progressBar = view.findViewById(R.id.pb_loader);


        adapter = new BookListsRecyclerViewAdapter(view.getContext(), null);
        recyclerView.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int pos = recyclerView.getChildLayoutPosition(v);
                    Cursor cursor = database.getData(Consts.DB_TABLE_BOOK_LISTS);
                    if (cursor.moveToPosition(pos)) {
                        listener.onClick(cursor.getString(cursor.getColumnIndex(Consts.DB_BL_COL_ENCODE_NAME)));
                    }

                }

            }
        });


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);

        if (savedInstanceState == null) {
            loadLists();
        } else {
            loadFromDatabase();
        }

        return view;
    }

    public void addItems(List<ListInfo> items, boolean clearList) {
        if (clearList) database.clearData(Consts.DB_TABLE_BOOK_LISTS);
        database.addBookLists(items);
        loadFromDatabase();
    }

    public void loadFromDatabase() {
        adapter.swapCursor(database.getData(Consts.DB_TABLE_BOOK_LISTS));
        recyclerView.scheduleLayoutAnimation();
    }

    private void loadLists() {
        progressBar.setVisibility(View.VISIBLE);
        RestClient.getClient().getService().getLists().enqueue(new Callback<SimpleResponseModel<ListInfo>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ListInfo>> call, Response<SimpleResponseModel<ListInfo>> response) {
                if (response.isSuccessful()) {
                    addItems(response.body().getResults(), true);
                } else {
                    loadFromDatabase();
                }


                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ListInfo>> call, Throwable t) {
                loadFromDatabase();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    public interface OnListInfoClick {

        void onClick(String encode);
    }
}
