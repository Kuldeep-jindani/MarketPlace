package com.place.market.pro.sau.marketplace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.Adapters.Preview_imageSlider_adapter;
import com.place.market.pro.sau.marketplace.Extra.Grid_model;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.OffsetTime;
import java.util.ArrayList;

public class PreviewProducts extends AppCompatActivity {
    Button contact_buy,ok;
    RecyclerView preview_imagescroll;
    TextView txt_name, txt_desc, txt_price, sell_name, sell_date, preview_tags, preview_remark;
    RelativeLayout ln;
    PinEntryEditText pinEntry;
    TextView contact_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_products);
        contact_buy = findViewById(R.id.buy_now);
        preview_imagescroll = findViewById(R.id.preview_imagescroll);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        preview_imagescroll.setLayoutManager(linearLayoutManager);

        Preview_imageSlider_adapter preview_imageSlider_adapter = new Preview_imageSlider_adapter(getApplicationContext());

        preview_imagescroll.setAdapter(preview_imageSlider_adapter);

        contact_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln = findViewById(R.id.ln_opt);
                ln.setVisibility(View.VISIBLE);
                pinEntry = findViewById(R.id.txt_pin_entry);

                String url = "http://192.168.1.200/market/Send_otp?user_id=1";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            final String otp = resp.getString("otp");

                            if (pinEntry != null) {
                                pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                                    @Override
                                    public void onPinEntered(CharSequence str) {
                                        if (str.toString().equals(otp)) {
                                            Toast.makeText(PreviewProducts.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                                            ln.setVisibility(View.INVISIBLE);
                                            str.toString().equals("");

                                            final Dialog dialog = new Dialog(PreviewProducts.this);
                                            dialog.setContentView(R.layout.contact);
                                            contact_number =  dialog.findViewById(R.id.contact_number);
                                            String url1 = "http://192.168.1.200/market/Contact?otp="+otp +
                                                    "&user_id=1&uploader_id=1";
                                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject resp = new JSONObject(response);
                                                       String number =  resp.getString("uploader_phone_number");
                                                        contact_number.setText(number);

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {

                                                }
                                            });

                                            Volley.newRequestQueue(getApplicationContext()).add(stringRequest1);


                                            ok = dialog.findViewById(R.id.btn_ok);
                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();

                                        } else {
                                            Toast.makeText(PreviewProducts.this, "FAIL", Toast.LENGTH_SHORT).show();
                                            pinEntry.setText(null);
                                        }
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(getApplicationContext()).add(stringRequest);


            }
        });
        txt_desc =

                findViewById(R.id.preview_desc);

        txt_name =

                findViewById(R.id.preview_name);

        txt_price =

                findViewById(R.id.preview_price);

        sell_name =

                findViewById(R.id.sell_name);

        sell_date =

                findViewById(R.id.sell_date);

        preview_tags =

                findViewById(R.id.preview_tags);

        preview_remark =

                findViewById(R.id.preview_remark);

        sell_name.setText(

                getIntent().getStringExtra("uploader_name"));
        txt_name.setText(

                getIntent().getStringExtra("product_name"));
        txt_desc.setText(

                getIntent().

                        getStringExtra("description"));
        txt_price.setText(

                getIntent().

                        getStringExtra("price"));
        preview_remark.setText(

                getIntent().

                        getStringExtra("remarks"));
        sell_date.setText(getIntent().getStringExtra("created_at"));


    }
}
