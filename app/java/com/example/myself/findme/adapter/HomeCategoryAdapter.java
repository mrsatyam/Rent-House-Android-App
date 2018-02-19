package com.example.myself.findme.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myself.findme.R;
import com.example.myself.findme.model.ProductCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private final List<ProductCategory> items;
    private final Context context;

    public HomeCategoryAdapter(Context context,ClickListener callback,List<ProductCategory> items) {
        this.items = items;
        this.context = context;
        mCallback=callback;
    }

    public interface ClickListener {
        void onCategoryClick(int index);

        void onCategoryLongClick(int index);
    }

    private final ClickListener mCallback;

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_category_row, parent, false);
        return new ViewHolder(v,mCallback);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ProductCategory item = items.get(position);

        holder.titleView.setText(item.getTitle());
        holder.directorView.setText(item.getDirector());

        Picasso.with(context)
                .load(item.getCoverResourceId())
                .placeholder(R.drawable.placeholder)
                .into(holder.coverView);

        holder.itemView.setTag(item);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        public ClickListener mCallback;
        TextView titleView;
       TextView directorView;
       TextView ratingView;
         ImageView coverView;

        public ViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            this.mCallback=callback;
           titleView=(TextView)itemView.findViewById(R.id.title_tv);
            directorView=(TextView)itemView.findViewById(R.id.director_tv);
            ratingView=(TextView) itemView.findViewById(R.id.rating_tv);
            coverView=(ImageView)itemView.findViewById(R.id.cover_iv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null)
                mCallback.onCategoryClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mCallback != null)
                mCallback.onCategoryLongClick(getAdapterPosition());
            return false;
        }
    }
}