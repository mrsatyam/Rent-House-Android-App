package com.example.myself.findme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myself.findme.R;
import com.example.myself.findme.model.ProductListModel;
import com.example.myself.findme.model.ProductModel;
import com.example.myself.findme.util.EndPoints;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MySelf on 5/9/2016.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private final List<ProductListModel> items;
    private final Context context;

    public ListAdapter(Context context,ClickListener callback,List<ProductListModel> items) {
        this.items = items;
        this.context = context;
        mCallback=callback;
    }

    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
    }

    private final ClickListener mCallback;

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list, parent, false);
        return new ViewHolder(v,mCallback);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ProductListModel item= items.get(position);

        holder.titleView.setText(item.getTitle());
        holder.areaView.setText(item.getArea());
        holder.descriptionView.setText(item.getShort_tag());
        holder.priceView.setText(item.getPrice());
        holder.dateView.setText(item.getCreated_at());

        if(item.getImage()!=null) {
            Picasso.with(context)
                    .load(EndPoints.baseaddress + "/" + item.getImage())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.image)
            ;
        }
        else
        {
           holder.image.setImageResource(R.drawable.placeholder);
        }

            holder.itemView.setTag(item);

    }

    @Override public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        public ClickListener mCallback;
        TextView titleView;
        TextView areaView;
        TextView descriptionView;
        ImageView image;
        TextView priceView;
        TextView dateView;

        public ViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            this.mCallback=callback;
            titleView=(TextView)itemView.findViewById(R.id.title);
            areaView=(TextView)itemView.findViewById(R.id.area);
            descriptionView=(TextView) itemView.findViewById(R.id.description);
            image=(ImageView)itemView.findViewById(R.id.image);
            priceView=(TextView) itemView.findViewById(R.id.price);
            dateView=(TextView) itemView.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null)
                mCallback.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            if (mCallback != null)
                mCallback.onLongClick(getAdapterPosition());
            return false;
        }
    }

}
