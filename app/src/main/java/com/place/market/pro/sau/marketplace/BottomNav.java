package com.place.market.pro.sau.marketplace;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.place.market.pro.sau.marketplace.Adapters.BottomNav_CategoryList_Adapter;
import com.place.market.pro.sau.marketplace.Adapters.DrawerItemCustomAdapter;
import com.place.market.pro.sau.marketplace.Extra.DrawerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ImageView imageView, bottomnav_menu;
    private TextView mTextMessage;

    //    ListView mDrawerList;
    Toolbar toolbar;
    Handler mHandler;

    ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {

                    case R.id.my_products:

                       /* Intent intent = new Intent(BottomNav.this, History.class);
                        startActivity(intent);*/
//                    fragment = new Tablayout_with_viewpager(1);
                        fragmentTransaction.replace(R.id.viewpager, new History()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.Change_Language:
                        /*Intent i=new Intent(getApplicationContext(),SelectLanguage.class);
                        startActivity(i);*/
                        fragmentTransaction.replace(R.id.viewpager, new SelectLanguage()).commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    case R.id.About_Us:
                        fragmentTransaction.replace(R.id.viewpager, new ComingSoonFragment()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.contract:
                        fragmentTransaction.replace(R.id.viewpager, new ContractFragment()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.Contact_Us:
                        fragmentTransaction.replace(R.id.viewpager, new ComingSoonFragment()).commit();
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.About_KU:
                        fragmentTransaction.replace(R.id.viewpager, new ComingSoonFragment()).commit();
                        mDrawerLayout.closeDrawers();
                        break;

                    default:
                        break;

                }

                return false;
            }
        });

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
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        DrawerModel[] drawerItem = new DrawerModel[5];
        drawerItem[0] = new DrawerModel(/*R.drawable.home, */"My Products");
        drawerItem[1] = new DrawerModel(/*R.drawable.home, */"Change Language");
        drawerItem[2] = new DrawerModel(/*R.drawable.calendar, */"About Us");
        drawerItem[3] = new DrawerModel(/*R.drawable.desiretour, */"Contact Us");
        drawerItem[4] = new DrawerModel(/*R.drawable.paypal, */"About KU");


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setHomeButtonEnabled(true);


        mHandler = new Handler();

        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(mDrawerLayout);
                mDrawerLayout.closeDrawers();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(mDrawerLayout);

            }
        };

       /* String [] strings = new String [] {"1", "2" };
        ArrayList<DrawerModel> stringList = new ArrayList<DrawerModel>(Arrays.<DrawerModel>asList(R.array.menu));

//        @SuppressLint("ResourceType") ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),R.layout.customdrawerlayout,R.array.menu);
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.customdrawerlayout, R.array.menu);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());*/

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        imageView = findViewById(R.id.camera_icon);
        bottomnav_menu = findViewById(R.id.bottomnav_menu);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.viewpager, new SellForm()).commit();*/
                Intent intent = new Intent(BottomNav.this, SellForm.class);
                startActivity(intent);
              /*  FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack("Dashboard");
                ft.replace(R.id.viewpager,new SellForm()).commit();*/
            }
        });

        bottomnav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(BottomNav.this, History.class);
                startActivity(intent);*/


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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

        public void selectItem(int position) {

            Fragment fragment = null;
            switch (position) {
                case 0:

                    Intent intent = new Intent(BottomNav.this, History.class);
                    startActivity(intent);
//                    fragment = new Tablayout_with_viewpager(1);

                    break;
                case 1:
                    Intent i = new Intent(getApplicationContext(), SelectLanguage.class);
                    startActivity(i);
                    break;
                case 2:
                    fragment = new ComingSoonFragment();
                    break;
                case 3:
                    fragment = new ComingSoonFragment();
                    break;
                case 4:
                    fragment = new ComingSoonFragment();
                    break;

                default:
                    break;
            }
            if (fragment != null) {


                final Fragment finalFragment = fragment;
                Runnable mPendingRunnable = new Runnable() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void run() {
                        // update the main content by replacing fragments
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                                android.R.anim.fade_out);
                        fragmentTransaction.replace(R.id.viewpager, finalFragment).commit();

                    }
                };

                if (mPendingRunnable != null) {
                    mHandler.post(mPendingRunnable);
                }

                mDrawerLayout.closeDrawers();

                // refresh toolbar menu
                invalidateOptionsMenu();


            } else {
                Log.e("MainActivity", "Error in creating fragment");
            }
        }
    }
}
