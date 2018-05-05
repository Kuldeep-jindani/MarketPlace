package com.place.market.pro.sau.marketplace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.Adapters.BottomNav_CategoryList_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ImageView imageView, bottomnav_menu;
    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);


        /*bottomnav_category_list=findViewById(R.id.bottomnav_category_list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        bottomnav_category_list.setLayoutManager(linearLayoutManager);



        String url="http://kisanunnati.com/market_place/getCategoryData";
        Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject resObj=new JSONObject(response);


                    if (resObj.getInt("status")==0){

                        JSONArray array=resObj.getJSONArray("data");

                        BottomNav_CategoryList_Adapter bottomNav_categoryList_adapter=new BottomNav_CategoryList_Adapter(getApplicationContext(),array);
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

*/

        imageView = findViewById(R.id.camera_icon);
        bottomnav_menu = findViewById(R.id.bottomnav_menu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomNav.this, SellForm.class);
                startActivity(intent);
            }
        });

        bottomnav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BottomNav.this, History.class);
                startActivity(intent);
            }
        });
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.Fragment fragment = null;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new BuyActivity();
                fragmentTransaction.replace(R.id.viewpager, new BuyActivity()).commit();
                break;
            case R.id.navigation_dashboard:
                break;

            case R.id.navigation_notifications:
                fragment = new ProfileActivity();
                fragmentTransaction.replace(R.id.viewpager, new ProfileActivity()).commit();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(android.support.v4.app.Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.viewpager, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            final Dialog dialog = new Dialog(BottomNav.this);

            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.exit_dialog, null);
            dialog.setContentView(view);

            dialog.show();

            Button yes = dialog.findViewById(R.id.exit_yes);
            Button no = dialog.findViewById(R.id.exit_no);

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();
                }
            });

            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }
}
