package com.place.market.pro.sau.marketplace;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.Adapters.BottomNav_CategoryList_Adapter;
import com.place.market.pro.sau.marketplace.Extra.Paginator;
import com.srx.widget.PullToLoadView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyActivity extends Fragment {
    PullToLoadView recyclar;
    BuyerAdapter buyerAdapter;
    RecyclerView bottomnav_category_list;

    LinearLayout cate_layout;
    EditText search_edittext;
    ImageView search,filter;

    RelativeLayout pricrfilter;

    LinearLayout lowtohigh,hightolow;

    int searchbit = 0;
    int pricebit= 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_buy, container, false);
        recyclar = view.findViewById(R.id.recyclar);

/*
        recyclar.setLayoutManager(new GridLayoutManager(getContext(),2));
        BuyerAdapter buyerAdapter=new  BuyerAdapter(getContext());
        recyclar.setAdapter(buyerAdapter);*/
        bottomnav_category_list = view.findViewById(R.id.bottomnav_category_list);
        cate_layout = view.findViewById(R.id.cate_layout);
        search_edittext = view.findViewById(R.id.search_edittext);
        search = view.findViewById(R.id.search);
        filter = view.findViewById(R.id.filter);
        pricrfilter= view.findViewById(R.id.pricrfilter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        bottomnav_category_list.setLayoutManager(linearLayoutManager);

        lowtohigh=view.findViewById(R.id.lowtohigh);
        hightolow=view.findViewById(R.id.hightolow);


        lowtohigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Paginator(getContext(), recyclar,0).initializePagination();
                pricebit=0;
                pricrfilter.setVisibility(View.GONE);
            }
        });


        hightolow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Paginator(getContext(), recyclar,1).initializePagination();
                pricebit=1;
                pricrfilter.setVisibility(View.GONE);
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pricrfilter.getVisibility() == View.VISIBLE) {
                    pricrfilter.setVisibility(View.GONE);
                   /* pricrfilter.animate()
                            .translationY(pricrfilter.getHeight())
                            .setListener(null);*/
                } else {
                    pricrfilter.setVisibility(View.VISIBLE);
                  /*  pricrfilter.animate()
                            .translationY(pricrfilter.getHeight())
                            .setListener(null);*/
                }
            }
        });




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (searchbit == 0) {
                    bottomnav_category_list.setVisibility(View.GONE);
                    bottomnav_category_list.animate().alpha(0.0f);
                    search_edittext.animate().alpha(1.0f);
                    search_edittext.setVisibility(View.VISIBLE);
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel));
                    searchbit = 1;
                } else {
                    search_edittext.setText("");
                    new Paginator(getContext(), recyclar).initializePagination();
                    search_edittext.animate().alpha(0.0f);
                    bottomnav_category_list.animate().alpha(1.0f);
                    bottomnav_category_list.setVisibility(View.VISIBLE);
                    search_edittext.setVisibility(View.GONE);
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_search));
                    searchbit = 0;
                }
            }
        });

        search_edittext.setFocusable(true);
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new Paginator(getContext(), recyclar,s.toString(),pricebit).initializePagination();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String url = "http://kisanunnati.com/market_place/getCategoryData";
        Volley.newRequestQueue(getContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObj = new JSONObject(response);


                    if (resObj.getInt("status") == 0) {

                        JSONArray array = resObj.getJSONArray("data");

                        BottomNav_CategoryList_Adapter bottomNav_categoryList_adapter = new BottomNav_CategoryList_Adapter(getContext(), array);
                        bottomnav_category_list.setAdapter(bottomNav_categoryList_adapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


        new Paginator(getContext(), recyclar).initializePagination();

        return view;
    }
}
