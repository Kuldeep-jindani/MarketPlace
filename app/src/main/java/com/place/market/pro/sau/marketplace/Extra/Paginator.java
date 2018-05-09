package com.place.market.pro.sau.marketplace.Extra;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.BuyerAdapter;
import com.srx.widget.PullCallback;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Saubhagyam on 20/07/2017.
 */

public class Paginator {


    Context c;
    PullToLoadView pullToLoadView;
    RecyclerView rv;
    BuyerAdapter adapter;
    boolean isLoading = false;
    boolean hasLoadAll = false;
    int nextPage;

    String editText = "";

    int prizebit = 0;

    int cat_id;

   /* public Paginator(Context c, PullToLoadView pullToLoadView, int cat_id) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.cat_id = cat_id;
        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new BuyerAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }*/


    public Paginator(Context c, PullToLoadView pullToLoadView) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new BuyerAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }


    public Paginator(Context c, PullToLoadView pullToLoadView, String editText) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.editText = editText;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new BuyerAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }

    public Paginator(Context c, PullToLoadView pullToLoadView, String editText, int prizebit) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.editText = editText;
        this.prizebit = prizebit;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new BuyerAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }

    public Paginator(Context c, PullToLoadView pullToLoadView, int prizebit) {
        this.c = c;
        this.pullToLoadView = pullToLoadView;
        this.prizebit = prizebit;

        rv = pullToLoadView.getRecyclerView();
        rv.setLayoutManager(new GridLayoutManager(c, 2));

        adapter = new BuyerAdapter(c, new ArrayList<Grid_model>());
        rv.setAdapter(adapter);

        initializePagination();
    }


    public void initializePagination() {

        pullToLoadView.isLoadMoreEnabled(true);
        pullToLoadView.setPullCallback(new PullCallback() {
            @Override
            public void onLoadMore() {
                LoadData(nextPage);
            }

            @Override
            public void onRefresh() {
                adapter.clear();
                hasLoadAll = false;
                LoadData(0);
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return hasLoadAll;
            }
        });

        pullToLoadView.initLoad();
    }


    private void LoadData(final int page) {

        isLoading = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(c);
                SharedPreferences pre = c.getSharedPreferences("status", MODE_PRIVATE);

                String URL = "";
                if (editText.equals(""))
                    URL = "http://kisanunnati.com/market_place/Product_list?user_id=" + pre.getString("id", "") + "&category_id=&last_product_id=" + page * 10 + "&price_filter=" + prizebit;
                else URL = "http://kisanunnati.com/market_place/Search?name=" + editText;
                Log.e("Grid service url", URL);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                                JSONObject dataObject = jsonObject.getJSONObject("data");

                            JSONArray array = jsonObject.getJSONArray("data");
                            Log.e("paginator response", response);
                            Log.e("page IN ASYNC TASK ", String.valueOf(page));

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject o = (JSONObject) array.get(i);
//                                if (o.getInt("id")!=315635) {
                                Grid_model grid_model = new Grid_model();
                                grid_model.setId(o.getString("id"));
                                grid_model.setUploader_id(o.getString("uploader_id"));
                                grid_model.setUploader_name(o.getString("uploader_name"));
                                grid_model.setName(o.getString("product_name"));
                                grid_model.setDescription(o.getString("description"));
                                grid_model.setPrice(o.getString("price"));
                                grid_model.setImage1(o.getString("image1"));
                                grid_model.setImage2(o.getString("image2"));
                                grid_model.setImage3(o.getString("image3"));
                                grid_model.setImage4(o.getString("image4"));
                                grid_model.setImage5(o.getString("image5"));
                                grid_model.setRemarks(o.getString("remarks"));
                                grid_model.setTime(o.getString("created_at"));


                                adapter.add(grid_model);
//                                }

                            }
                            pullToLoadView.setComplete();
                            isLoading = false;
                            nextPage = page + 1;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest);
            }
        }, 10);
    }
}
