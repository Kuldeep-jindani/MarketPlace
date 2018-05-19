package com.place.market.pro.sau.marketplace;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.Adapters.SlidingImage_Adapter;
import com.place.market.pro.sau.marketplace.Extra.Grid_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class PreviewProducts extends Fragment {
    Button contact_buy, ok;
    RecyclerView similarProd;
    ViewFlipper preview_imagescroll;
    TextView txt_name, txt_desc, txt_price, sell_name, sell_date, preview_tags, preview_remark,txt_contacted;
    RelativeLayout ln;
    PinEntryEditText pinEntry;
    TextView contact_number;
    private GestureDetector mGestureDetector;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_preview_products, container, false);
        Bundle bundle = getArguments();
        txt_contacted = view.findViewById(R.id.contacted);
        contact_buy = view.findViewById(R.id.buy_now);

        if (bundle.getString("from").equals("history"))
            contact_buy.setVisibility(View.GONE);
//        preview_imagescroll = view.findViewById(R.id.preview_imagescroll);
//        preview_imagescroll.setInAnimation(this, android.R.anim.fade_in);
//        preview_imagescroll.setOutAnimation(this, android.R.anim.fade_out);

        /*CustomGestureDetector customGestureDetector = new CustomGestureDetector();
        mGestureDetector = new GestureDetector(this, customGestureDetector);*/

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        preview_imagescroll.setLayoutManager(linearLayoutManager);
        similarProd = view.findViewById(R.id.similarProd);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

//        preview_imagescroll.setLayoutManager(linearLayoutManager);
        similarProd.setLayoutManager(linearLayoutManager1);


        String similarProdUrl = "http://kisanunnati.com/market_place/similar_product?prod_id=" + bundle.getString("id");
        Volley.newRequestQueue(getContext()).add(new StringRequest(Request.Method.POST, similarProdUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("similar prod resp", response);

                ArrayList<Grid_model> grid_models = new ArrayList<Grid_model>();

                try {
                    JSONObject jsonObject = new JSONObject(response);
//                                JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray array = jsonObject.getJSONArray("data");


                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
//                                if (o.getInt("id")!=315635) {
                        Grid_model grid_model = new Grid_model();
                        grid_model.setId(o.getString("id"));
                        grid_model.setUploader_id(o.getString("uploader_id"));
//                        grid_model.setUploader_name(o.getString("name"));
                        grid_model.setName(o.getString("name"));
                        grid_model.setDescription(o.getString("description"));
                        grid_model.setPrice(o.getString("price"));
                        grid_model.setContacted(o.getString("contacted"));
                        grid_model.setImage1(o.getString("image1"));
                        grid_model.setImage2(o.getString("image2"));
                        grid_model.setImage3(o.getString("image3"));
                        grid_model.setImage4(o.getString("image4"));
                        grid_model.setImage5(o.getString("image5"));
                        grid_model.setRemarks(o.getString("remarks"));
                        Log.e("date", o.getString("created_at"));
                        grid_model.setTime(o.getString("created_at"));
                        txt_contacted.setText("Contacted: " + o.getString("contacted"));
                        grid_model.setFrom("dashboard");
                        grid_models.add(grid_model);
//                                }

                    }

                    BuyerAdapter adapter = new BuyerAdapter(getContext(), grid_models);
                    similarProd.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));


        ArrayList<String> pics = new ArrayList<>();

        pics.add(bundle.getString("image1"));
        if (!bundle.getString("image2").equals(""))
            pics.add(bundle.getString("image2"));
        if (!bundle.getString("image3").equals(""))
            pics.add(bundle.getString("image3"));
        if (!bundle.getString("image4").equals(""))
            pics.add(bundle.getString("image4"));
        if (!bundle.getString("image5").equals(""))
            pics.add(bundle.getString("image5"));


        TextView total_pics = view.findViewById(R.id.total_pics);
        total_pics.setText(String.valueOf(pics.size())+" photos");

      /*  for (int i=0;i<pics.size();i++){
            ImageView img=new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(pics.get(i)).into(img);
            preview_imagescroll.addView(img);
        }*/

//        preview_imagescroll.setAdapter(preview_imageSlider_adapter);
        contact_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln = view.findViewById(R.id.ln_opt);
                ln.setVisibility(View.VISIBLE);
                pinEntry = view.findViewById(R.id.txt_pin_entry);

                String url = "http://kisanunnati.com/market_place/Send_otp?user_id=1";
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
                                            Toast.makeText(getContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                                            ln.setVisibility(View.INVISIBLE);
                                            str.toString().equals("");

                                            final Dialog dialog = new Dialog(getContext());
                                            dialog.setContentView(R.layout.contact);
                                            contact_number = dialog.findViewById(R.id.contact_number);
                                            String url1 = "http://kisanunnati.com/market_place/Contact?otp=" + otp +
                                                    "&user_id=" + getActivity().getSharedPreferences("status", MODE_PRIVATE).getString("id", "") +
                                                    "&uploader_id=" + bundle.getString("id");
                                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject resp = new JSONObject(response);
                                                        String number = resp.getString("uploader_phone_number");
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

                                            Volley.newRequestQueue(getContext()).add(stringRequest1);

                                            ok = dialog.findViewById(R.id.btn_ok);
                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            dialog.show();

                                        } else {
                                            Toast.makeText(getContext(), "FAIL", Toast.LENGTH_SHORT).show();
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
                Volley.newRequestQueue(getContext()).add(stringRequest);
            }
        });
        txt_desc = view.findViewById(R.id.preview_desc);
        txt_name = view.findViewById(R.id.preview_name);
        txt_price = view.findViewById(R.id.preview_price);
        sell_name = view.findViewById(R.id.sell_name);
        sell_date = view.findViewById(R.id.sell_date);
        preview_tags = view.findViewById(R.id.preview_tags);
        preview_remark = view.findViewById(R.id.preview_remark);
        sell_name.setText(bundle.getString("uploader_name"));
        txt_name.setText(bundle.getString("product_name"));
        txt_desc.setText(bundle.getString("description"));
        String price = bundle.getString("price") + " INR";
        txt_price.setText(price);
        preview_remark.setText(bundle.getString("remarks"));


        String d = bundle.getString("created_at");
   /*     SimpleDateFormat dt = new SimpleDateFormat("dd-MMM-yyyy");
        String stringdate = null;
        try {
            stringdate = String.valueOf(dt.parse(d));
            sell_date.setText(stringdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


            sell_date.setText(d);

        ViewPager mPager = (ViewPager) view.findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(getContext(), pics));
return  view;

    }
    /*class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            // Swipe left (next)
            if (e1.getX() > e2.getX()) {
                preview_imagescroll.showNext();
            }

            // Swipe right (previous)
            if (e1.getX() < e2.getX()) {
                preview_imagescroll.showPrevious();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }*/
}
