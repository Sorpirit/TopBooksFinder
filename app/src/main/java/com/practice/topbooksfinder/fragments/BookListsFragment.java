package com.practice.topbooksfinder.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.adapters.BookListsRecyclerViewAdapter;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.utils.Consts;
import com.practice.topbooksfinder.utils.Database;

import java.util.ArrayList;
import java.util.List;


public class BookListsFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookListsRecyclerViewAdapter adapter;
    private Database database;

    private OnListInfoClick listener;

    public BookListsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_lsits, container, false);

        recyclerView = view.findViewById(R.id.list);
        database = Database.getInstanse();

        adapter = new BookListsRecyclerViewAdapter(view.getContext(),database.getData(Consts.DB_TABLE_BOOK_LISTS));
        recyclerView.setAdapter(adapter);

        adapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null) {
                    int pos = recyclerView.getChildLayoutPosition(v);
                    Cursor cursor = database.getData(Consts.DB_TABLE_BOOK_LISTS);
                    if(cursor.moveToPosition(pos)){
                        int z = cursor.getColumnIndex(Consts.DB_BL_COL_ENCODE_NAME);
                        String z1 = cursor.getString(cursor.getColumnIndex(Consts.DB_BL_COL_ENCODE_NAME));
                        String zzz = adapter.getListInfo(z).getEncodeName();
                        listener.onClick(cursor.getString(cursor.getColumnIndex(Consts.DB_BL_COL_ENCODE_NAME)));
                    }

                }

            }
        });


        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(recyclerView.getContext(), R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);


        return view;
    }

    public void addItems(List<ListInfo> items, boolean clearList){
        if(clearList) database.clearData(Consts.DB_TABLE_BOOK_LISTS);
        database.addBookLists(items);
        adapter.swapCursor(database.getData(Consts.DB_TABLE_BOOK_LISTS));
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    public void setListener(OnListInfoClick listener) {
        this.listener = listener;
    }

    public interface OnListInfoClick{

        void onClick(String encode);
    }
}
