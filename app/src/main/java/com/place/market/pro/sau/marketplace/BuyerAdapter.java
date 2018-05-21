package com.place.market.pro.sau.marketplace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.place.market.pro.sau.marketplace.Extra.Grid_model;

import java.util.ArrayList;

import static android.view.View.GONE;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder>{
    Context context;
    ArrayList<Grid_model> grid_models;
FragmentManager fragmentManager;

    public BuyerAdapter(Context context,ArrayList<Grid_model> grid_models,FragmentManager fragmentManager) {
        this.context = context;
        this.fragmentManager=fragmentManager;
        this.grid_models=grid_models;
    }

    public void add(Grid_model grid_model){
        grid_models.add(grid_model);
        notifyDataSetChanged();
    }
    public void clear(){
        grid_models.clear();
    }

    @Override
    public BuyerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.buyitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Grid_model grid_model=grid_models.get(position);
        holder.txt_name.setVisibility(View.VISIBLE);
        Glide.with(context).load(grid_model.getImage1()).into(holder.imageView);

        if (grid_model.getFrom().equalsIgnoreCase("history")){
            holder.buyitem_contacted_holder.setVisibility(View.VISIBLE);
        }
        else {
            holder.buyitem_contacted_holder.setVisibility(GONE);
        }



        holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PreviewProducts newFragment = new PreviewProducts();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.viewpager, newFragment);
            Bundle bundle = new Bundle();
            bundle.putString("id",grid_model.getId());
            bundle.putString("uploader_id",grid_model.getUploader_id());
            bundle.putString("uploader_name",grid_model.getUploader_name());
            bundle.putString("product_name",grid_model.getName());
            bundle.putString("description",grid_model.getDescription());
            bundle.putString("price",grid_model.getPrice());
            bundle.putString("contacted",grid_model.getContacted());
            bundle.putString("image1",grid_model.getImage1());
            bundle.putString("image2",grid_model.getImage2());
            bundle.putString("image3",grid_model.getImage3());
            bundle.putString("image4",grid_model.getImage4());
            bundle.putString("image5",grid_model.getImage4());
            bundle.putString("remarks",grid_model.getRemarks());
            bundle.putString("created_at",grid_model.getTime());



//            Toast.makeText(context, "from "+grid_model.getFrom(), Toast.LENGTH_SHORT).show();
            if (grid_model.getFrom().equalsIgnoreCase("history")){
                bundle.putString("from","history");
            }
            else {
                bundle.putString("from", "dashboard");
                holder.txt_contacted.setVisibility(GONE);
            }
            newFragment.setArguments(bundle);

            transaction.commit();
            }
    });

        holder.txt_name.setText(grid_model.getName());
        holder.txt_price.setText(grid_model.getPrice());
        holder.txt_contacted.setText(/*"Contacted: "+*/grid_model.getContacted());
    }

    @Override
    public int getItemCount() {
        return grid_models.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView txt_contacted,txt_name,txt_price;
        LinearLayout buyitem_contacted_holder;

        ViewHolder(View itemView) {

            super(itemView);
            txt_contacted = itemView.findViewById(R.id.contacted);
            imageView = itemView.findViewById(R.id.buy_img);
            txt_name = itemView.findViewById(R.id.buy_name);
            txt_price = itemView.findViewById(R.id.buy_price);
            buyitem_contacted_holder = itemView.findViewById(R.id.buyitem_contacted_holder);

        }
    }
}
