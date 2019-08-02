package com.practice.topbooksfinder.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.utils.Consts;

public class BookListsRecyclerViewAdapter extends CursorRecyclerViewAdapter<BookListsRecyclerViewAdapter.ViewHolder> {

    private View.OnClickListener listener;

    public BookListsRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.books_list_view, parent, false);

        view.setOnClickListener(listener);

        return new ViewHolder(view);
    }

    public ListInfo getListInfo(Cursor c) {
        return new ListInfo(c.getString(c.getColumnIndex(Consts.DB_BL_COL_LIST_NAME)),
                c.getString(c.getColumnIndex(Consts.DB_BL_COL_ENCODE_NAME)),
                c.getString(c.getColumnIndex(Consts.DB_BL_COL_NEWEST_Published_Date)),
                c.getString(c.getColumnIndex(Consts.DB_BL_COL_OLDEST_Published_Date)),
                c.getString(c.getColumnIndex(Consts.DB_BL_COL_UPDATE)),
                null, null);
    }

    public ListInfo getListInfo(int position) {
        Cursor c = getCursor();
        if (c.moveToPosition(position)) {

            return getListInfo(c);

        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        ListInfo info = getListInfo(cursor);

        String encode = info.getEncodeName();
        //Log.i("encode",encode);

        holder.tvListName.setText(info.getListName());

        if (info.getUpdate() != null && !info.getUpdate().isEmpty()) {
            holder.tvUpdated.setText(info.getUpdate());
        } else {
            holder.tvUpdated.setText("---");
        }

        if (info.getNewestPublishedDate() != null && !info.getNewestPublishedDate().isEmpty()) {
            holder.tvLatestUpdate.setText(info.getNewestPublishedDate());
        } else {
            holder.tvLatestUpdate.setText("---");
        }


    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView tvListName;
        public final TextView tvUpdated;
        public final TextView tvLatestUpdate;
        public final View itemLayout;

        public ViewHolder(View view) {
            super(view);

            tvListName = view.findViewById(R.id.tv_listName);
            tvUpdated = view.findViewById(R.id.tv_updated);
            tvLatestUpdate = view.findViewById(R.id.tv_latest_update);
            itemLayout = view.findViewById(R.id.ll_item_layout);
        }
    }


}
