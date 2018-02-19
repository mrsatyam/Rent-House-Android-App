package com.example.myself.findme.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myself.findme.R;
import com.example.myself.findme.model.ProductModel;

import java.util.List;

/**
 * Created by GB on 11/8/2015.
 */
public class HomeRecyclerAdapterTrend extends RecyclerView.Adapter<HomeRecyclerAdapterTrend.ViewHolder> {

    List<List<ProductModel>> mItems;

    Context c;

    public HomeRecyclerAdapterTrend(Context context,List<List<ProductModel>> mItems) {
        super();
        this.mItems = mItems;

        c=context;



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_product_card_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


     LinearLayoutManager   llm=new LinearLayoutManager(c);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);

        if(i==0)
        viewHolder.tvtitle.setText("Hostel & PG");
       else if(i==1)
            viewHolder.tvtitle.setText("House");
       else if(i==2)
            viewHolder.tvtitle.setText("Project");
       else if(i==3)
            viewHolder.tvtitle.setText("Rent");


        HomeCardAdapterTrend ca=new HomeCardAdapterTrend(c,mItems.get(i));

        viewHolder.rv.setLayoutManager(llm);
        viewHolder.rv.setAdapter(ca);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView rv;
        TextView tvtitle;

        public ViewHolder(View itemView) {
            super(itemView);

            rv=(RecyclerView)itemView.findViewById(R.id.recycler_view);
            tvtitle=(TextView)itemView.findViewById(R.id.Title);

        }
    }
}
