package com.place.market.pro.sau.marketplace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.place.market.pro.sau.marketplace.Adapters.BottomNav_CategoryList_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellDetails extends AppCompatActivity {
    EditText edt_name, edt_decr,edt_price;
    Button img_next;
    TextView txt_cat;
    Spinner category;
    int cate_id=-1;
    ListView lv;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_details);
        edt_name = findViewById(R.id.sell_product_name);
        edt_decr = findViewById(R.id.sell_product_desc);
        edt_price = findViewById(R.id.sell_product_price);
        txt_cat = findViewById(R.id.select_category);


        dialog = new Dialog(SellDetails.this);
        dialog.setContentView(R.layout.list);
        lv = dialog.findViewById(R.id.lv);
        dialog.setCancelable(true);

        txt_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });

        String url="http://kisanunnati.com/market_place/getCategoryData";
        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObj=new JSONObject(response);


                    if (resObj.getInt("status")==0){

                        final JSONArray array=resObj.getJSONArray("data");

                        final ArrayList<String> ary=new ArrayList<>();
                        final ArrayList<Integer> aryint=new ArrayList<>();
                        for (int i=0;i<array.length();i++){

                            JSONObject jsonObject=array.getJSONObject(i);
                            ary.add(jsonObject.getString("name"));
                            aryint.add(jsonObject.getInt("id"));

                        }
                        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),R.layout.spinner_text,ary);
                        lv.setAdapter(arrayAdapter);


                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                               cate_id = aryint.get(i);
                               txt_cat.setText(ary.get(i));
                                dialog.dismiss();
                            }
                        });
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

  img_next = findViewById(R.id.next_sell_details);
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edt_name.getText().toString().equals("")){
                    edt_name.setError("Fill Something");

                }
                else if (edt_decr.getText().toString().equals("")){
                    edt_decr.setError("Fill Something");

                }

                else if (edt_price.getText().toString().equals("")){
                    edt_price.setError("Fill Something");

                }else if (cate_id==-1){
                    Toast.makeText(SellDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                }else {
                    SharedPreferences preferences=getSharedPreferences("image",MODE_PRIVATE);
                    SharedPreferences preference=getSharedPreferences("status", MODE_PRIVATE);
                    final String url="http://kisanunnati.com/market_place/Product_Upload?uploader_id="+preference.getString("id","") +
                            "&categorymanagement_id="+cate_id +
                            "&name="+edt_name.getText().toString().replace(" ","%20") +
                            "&description="+edt_decr.getText().toString() .replace(" ","%20")+
                            "&price="+edt_price.getText().toString().replace(" ","%20") +
                            "&image1="+preferences.getString("image1","") +
                            "&image2="+preferences.getString("image2","") +
                            "&image3="+preferences.getString("image3","") +
                            "&image4="+preferences.getString("image4","") +
                            "&image5="+preferences.getString("image5","");
                    final KProgressHUD hud = KProgressHUD.create(SellDetails.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(SellDetails.this, "response "+response, Toast.LENGTH_SHORT).show();
                            Log.e("url   ",url);
                            Log.e("responmse ",response);
                            hud.dismiss();
                            Intent i=new Intent(getApplicationContext(),BottomNav.class);
                            startActivity(i);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));
                }

            }
        });
    }
}
