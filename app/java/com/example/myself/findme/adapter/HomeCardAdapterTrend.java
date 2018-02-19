package com.example.myself.findme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myself.findme.R;
import com.example.myself.findme.model.ProductModel;
import com.example.myself.findme.util.EndPoints;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by GB on 11/8/2015.
 */
public class HomeCardAdapterTrend extends RecyclerView.Adapter<HomeCardAdapterTrend.ViewHolder> {

    List<ProductModel> mItems;
    Context context;

    public HomeCardAdapterTrend(Context context, List<ProductModel> Items) {
        super();
        mItems = Items;
        this.context = context;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_product_list_row, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ProductModel product = mItems.get(i);
        viewHolder.tvFirst.setText(product.getTitle());
        viewHolder.tvSecond.setText(product.getArea());
        viewHolder.tvThird.setText("Rs."+product.getPrice());
        if(product.getImage()!=null)
        Picasso.with(context).load(EndPoints.baseaddress+"/"+product.getImage()).placeholder(R.drawable.placeholder).into(viewHolder.imgThumbnail);
        else
            viewHolder.imgThumbnail.setImageResource(R.drawable.placeholder);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView tvFirst;
        public TextView tvSecond;
        public TextView tvThird;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.imageview);
            tvFirst = (TextView) itemView.findViewById(R.id.tv1);
            tvSecond = (TextView) itemView.findViewById(R.id.tv2);
            tvThird = (TextView) itemView.findViewById(R.id.tv3);

        }
    }
}
