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
import com.github.ivbaranov.mli.MaterialLetterIcon;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by MySelf on 5/11/2016.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    List<MessageModel> mItems;
    Context context;
    int[] mMaterialColors;


    public MessageAdapter(Context context,ClickListener callback, List<MessageModel> Items) {
        super();
        mItems = Items;
        this.context = context;
        mMaterialColors = context.getResources().getIntArray(R.array.colors);
        mCallback=callback;


    }

    public interface ClickListener {
        void onClick(int index);

        void onLongClick(int index);
    }

    private final ClickListener mCallback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_inbox, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v,mCallback);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MessageModel product = mItems.get(i);
        Random random = new Random();
        viewHolder.materialLetterIcon.setShapeColor(mMaterialColors[random.nextInt(mMaterialColors.length)]);
        viewHolder.materialLetterIcon.setLetter(String.valueOf(product.getName().toUpperCase().charAt(0)));
        viewHolder.tvFrom.setText(product.getName());
        viewHolder.tvMessage.setText(product.getMessage());
        viewHolder.tvDate.setText(product.getCreatedat());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public ClickListener mCallback;

        public MaterialLetterIcon materialLetterIcon;
        public TextView tvFrom;
        public TextView tvMessage;
        public TextView tvDate;

        public ViewHolder(View itemView, ClickListener callback) {
            super(itemView);
            this.mCallback=callback;
            materialLetterIcon = (MaterialLetterIcon) itemView.findViewById(R.id.Icon);
            tvFrom = (TextView) itemView.findViewById(R.id.From);
            tvMessage = (TextView) itemView.findViewById(R.id.Message);
            tvDate = (TextView) itemView.findViewById(R.id.Date);
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

