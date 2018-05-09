package com.place.market.pro.sau.marketplace.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.place.market.pro.sau.marketplace.Extra.Paginator;
import com.place.market.pro.sau.marketplace.Extra.RecyclerViewClickListener;
import com.place.market.pro.sau.marketplace.R;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BottomNav_CategoryList_Adapter extends RecyclerView.Adapter<BottomNav_CategoryList_Adapter.ViewHolder> {

    Context context;
    JSONArray array;
    PullToLoadView recyclar;

    public BottomNav_CategoryList_Adapter(Context context, JSONArray array,RecyclerViewClickListener mListener, PullToLoadView recyclar) {
        this.context = context;
        this.array = array;
        this.mListener=mListener;
        this.recyclar=recyclar;
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


            Glide.with(context).load(/*"http://kisanunnati.com/market_place/public/uploads/"+*/obj.getString("image")).into(holder.cate_img);

            holder.category_id.setText(obj.getString("id"));
            holder.category_name.setText(obj.getString("name"));

            holder.category_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Paginator(context, recyclar,Float.parseFloat(holder.category_id.getText().toString())).initializePagination();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array.length();
    }
    private RecyclerViewClickListener mListener;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView category_id,category_name;
        ImageView cate_img;
        LinearLayout category_root;




        public ViewHolder(View itemView) {
            super(itemView);
            cate_img = itemView.findViewById(R.id.cate_img);
            category_id = itemView.findViewById(R.id.category_id);
            category_name = itemView.findViewById(R.id.category_name);
            category_root= itemView.findViewById(R.id.category_root);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
