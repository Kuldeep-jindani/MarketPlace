package com.place.market.pro.sau.marketplace.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.place.market.pro.sau.marketplace.R;

import java.util.ArrayList;

public class Preview_imageSlider_adapter extends RecyclerView.Adapter<Preview_imageSlider_adapter.ViewHolder> {

    Context context;
    ArrayList<String> pics;


    public Preview_imageSlider_adapter(Context context,ArrayList<String> pics) {
        this.context = context;
        this.pics=pics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.buyitem,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(pics.get(position)).into(holder.buy_img);


    }

    @Override
    public int getItemCount() {
        return pics.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView buy_img;
        public ViewHolder(View itemView) {
            super(itemView);

            buy_img=itemView.findViewById(R.id.buy_img);
        }
    }
}
