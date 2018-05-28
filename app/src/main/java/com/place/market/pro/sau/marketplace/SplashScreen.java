package com.place.market.pro.sau.marketplace;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SplashScreen extends AppCompatActivity {
    private Runnable mRunnable;
    private Handler mHandler = new Handler();
    private static int SPLASH_TIME_OUT = 3000;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);




        imageView = findViewById(R.id.img_logo);

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
//                            Toast.makeText(SplashScreen.this, "granted", Toast.LENGTH_SHORT).show();

                            SharedPreferences langPref = getSharedPreferences("langPref", MODE_PRIVATE);
//                            Toast.makeText(SplashScreen.this, "lang? " + langPref.contains("lang"), Toast.LENGTH_SHORT).show();
                            if (!langPref.contains("lang")) {
                                Intent intent = new Intent(SplashScreen.this, LanguageSelectActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                SharedPreferences preferences = SplashScreen.this.getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                if (preferences.contains("email") && preferences.contains("password")) {
                                    String url = "http://kisanunnati.com/marketplace/userlogin?contact=" + preferences.getString("contact", "") + "&password=" + preferences.getString("password", "");
                                    final KProgressHUD hud = KProgressHUD.create(SplashScreen.this)
                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                            .setCancellable(false)
                                            .setAnimationSpeed(2)
                                            .setDimAmount(0.5f)
                                            .show();
                                    Log.e("login url", url);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.e("fgsdgb", response);
                                            try {
                                                JSONObject resp = new JSONObject(response);
                                                if (resp.getInt("status") == 0) {
                                                    JSONArray data = resp.getJSONArray("data");
                                                    JSONObject object = (JSONObject) data.get(0);

                                                    hud.dismiss();
                                                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    preferences.getString("email", "email");
                                                    preferences.getString("password", "password");
                                                    editor.apply();
                                                    Intent intent = new Intent(SplashScreen.this, BottomNav.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(intent);

                                                } else {
                                                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    hud.dismiss();
                                                    Toast.makeText(SplashScreen.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });

                                    Volley.newRequestQueue(SplashScreen.this).add(stringRequest);

                                } else {
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }, SPLASH_TIME_OUT);
                                }
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            Toast.makeText(SplashScreen.this, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }
    @Override
    protected void onDestroy ()
    {
        if (mHandler != null && mRunnable != null)
        {
            mHandler.removeCallbacks(mRunnable);
        }
        super.onDestroy();
    }


}
