package com.practice.topbooksfinder.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practice.topbooksfinder.R;
import com.practice.topbooksfinder.model.BookInfo;
import com.practice.topbooksfinder.model.ListInfo;
import com.practice.topbooksfinder.utils.Consts;

import java.util.List;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class BooksListRecyclerViewAdapter extends CursorRecyclerViewAdapter<BooksListRecyclerViewAdapter.ViewHolder> {

    private Context ctx;


    public BooksListRecyclerViewAdapter(Context ctx,Cursor cursor) {
        super(ctx,cursor);
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);


        return new ViewHolder(view);
    }



    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
        BookInfo info = getBookInfo(cursor);

        holder.tvTitle.setText(info.getTitle());
        holder.tvAuthor.setText("by " + info.getAuthor());
        holder.tvDescription.setText(info.getDescription());
        holder.tvDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        holder.tvPublisher.setText("Publisher: " + info.getPublisher());

        if (info.getBookImageUrl() != null && !info.getBookImageUrl().isEmpty()) {
            Glide.with(holder.imBookImage)
                    .load(info.getBookImageUrl())
                    .override(300,600)
                    .into(holder.imBookImage);


        }

        if(cursor.getPosition() == getItemCount()-1) holder.divider.setVisibility(View.INVISIBLE);
    }

    public BookInfo getBookInfo(Cursor c){
        return new BookInfo(c.getString(c.getColumnIndex(Consts.DB_B_COL_TITLE)),c.getString(c.getColumnIndex(Consts.DB_B_COL_AUTHOR)),
                c.getString(c.getColumnIndex(Consts.DB_B_COL_CONTRIBUTOR)),c.getString(c.getColumnIndex(Consts.DB_B_COL_PUBLISHER)),
                c.getString(c.getColumnIndex(Consts.DB_B_COL_AGE_GROUP)),c.getString(c.getColumnIndex(Consts.DB_B_COL_DESCRIPTION)),
                c.getInt(c.getColumnIndex(Consts.DB_B_COL_RANK)),c.getString(c.getColumnIndex(Consts.DB_B_COL_BOOK_IMAGE_URL)),
                c.getString(c.getColumnIndex(Consts.DB_B_COL_ID13)));
    }
    public BookInfo getBookInfo(int position){
        Cursor c = getCursor();
        if(c.moveToPosition(position)){

            return getBookInfo(c);

        }else {
            return null;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView tvTitle;
        public final TextView tvAuthor;
        public final AppCompatTextView tvDescription;
        public final TextView tvPublisher;
        private final AppCompatImageView imBookImage;
        private final View divider;

        public ViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.tv_title);
            tvAuthor = view.findViewById(R.id.tv_author);
            tvDescription = view.findViewById(R.id.tv_description);
            tvPublisher = view.findViewById(R.id.tv_publisher);
            imBookImage = view.findViewById(R.id.im_book_image);
            divider = view.findViewById(R.id.divider);
        }
    }
}
