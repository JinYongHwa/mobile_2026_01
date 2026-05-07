package kr.ac.mjc.library;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import kr.ac.mjc.library.dto.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private ArrayList<Book> books;

    public BookAdapter(ArrayList<Book> books){
        this.books=books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book=books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailIv;
        TextView titleTv;
        TextView authorTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailIv=itemView.findViewById(R.id.thumnail_iv);
            titleTv=itemView.findViewById(R.id.title_tv);
            authorTv=itemView.findViewById(R.id.author_tv);
        }
        public void bind(Book book){
            titleTv.setText(book.getTitleStatement());
            authorTv.setText(book.getAuthor());

            Glide.with(thumbnailIv.getContext())
                    .load(book.getThumbnailUrl())

                    .into(thumbnailIv);

        }
    }
}
