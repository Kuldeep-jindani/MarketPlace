package com.place.market.pro.sau.marketplace.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.place.market.pro.sau.marketplace.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BottomNav_CategoryList_Adapter extends RecyclerView.Adapter<BottomNav_CategoryList_Adapter.ViewHolder> {

    Context context;
    JSONArray array;

    public BottomNav_CategoryList_Adapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_image_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            JSONObject obj=array.getJSONObject(position);


            Glide.with(context).load("http://kisanunnati.com/market_place/public/uploads/"+obj.getString("image")).into(holder.cate_img);

            holder.category_id.setText(obj.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView category_id;
        ImageView cate_img;

        public ViewHolder(View itemView) {
            super(itemView);
            cate_img = itemView.findViewById(R.id.cate_img);
            category_id = itemView.findViewById(R.id.category_id);

        }
    }
}
