package com.place.market.pro.sau.marketplace;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.place.market.pro.sau.marketplace.Extra.Grid_model;

import java.util.ArrayList;

public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder>{
    Context context;
    ArrayList<Grid_model> grid_models;

    public BuyerAdapter(Context context,ArrayList<Grid_model> grid_models) {
        this.context = context;
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

        Glide.with(context).load(grid_model.getImage1()).into(holder.imageView);

    holder.imageView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),PreviewProducts.class);
            intent.putExtra("id",grid_model.getId());
            intent.putExtra("uploader_id",grid_model.getUploader_id());
            intent.putExtra("uploader_name",grid_model.getUploader_name());
            intent.putExtra("product_name",grid_model.getName());
            intent.putExtra("description",grid_model.getDescription());
            intent.putExtra("price",grid_model.getPrice());
            intent.putExtra("image1",grid_model.getImage1());
            intent.putExtra("image2",grid_model.getImage2());
            intent.putExtra("image3",grid_model.getImage3());
            intent.putExtra("image4",grid_model.getImage4());
            intent.putExtra("image5",grid_model.getImage4());
            intent.putExtra("remarks",grid_model.getRemarks());
            intent.putExtra("created_at",grid_model.getTime());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    });

    holder.txt_name.setText(grid_model.getName());
    holder.txt_price.setText(grid_model.getPrice()+" INR");
    }

    @Override
    public int getItemCount() {
        return grid_models.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_name,txt_price;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.buy_img);
            txt_name = itemView.findViewById(R.id.buy_name);
            txt_price = itemView.findViewById(R.id.buy_price);
        }
    }
}
