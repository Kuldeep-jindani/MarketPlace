package com.place.market.pro.sau.marketplace;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText edt_email,edt_pwd;
    Button btn_login;
   TextView txt_register,txt_forgotpwd;
   TextInputLayout login_email_wrapper,login_pwd_wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email =findViewById(R.id.login_email);
        edt_pwd =findViewById(R.id.login_pwd);
        btn_login =findViewById(R.id.login);
        login_pwd_wrapper =findViewById(R.id.login_pwd_wrapper);
        login_email_wrapper =findViewById(R.id.login_email_wrapper);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().equals("") ){
                    login_email_wrapper.setErrorEnabled(true);
                    edt_email.setError("Please enter Email");

                }else if(edt_pwd.getText().toString().equals("")){
                    login_pwd_wrapper.setErrorEnabled(true);
                    edt_pwd.setError("Please enter Password");
                }
                else if (edt_pwd.getText().toString().length()<6){
                    edt_pwd.setError("Password must be greater than 6 characters.");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()){
                    edt_email.setError("Please enter valid Email");
                }

                else {

                    String url = "http://kisanunnati.com/market_place/userlogin?"+
                             "email=" +edt_email.getText().toString()+
                            "&password="+ edt_pwd.getText().toString();
                    final KProgressHUD hud = KProgressHUD.create(LoginActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    Log.e("login url",url);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                if (resp.getInt("status") == 0){
                                    JSONArray data = resp.getJSONArray("data");
                                    JSONObject object = (JSONObject) data.get(0);
                                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", object.getString("id"));
                                    editor.putString("name", object.getString("name"));
                                    editor.putString("email", edt_email.getText().toString());
                                    editor.putString("password", edt_pwd.getText().toString());
                                    editor.putString("gender",  object.getString("gender"));
                                    editor.putString("birthdate", object.getString("birthdate"));
                                    editor.putString("contact",object.getString("contact")).apply();
                                    hud.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login Successfull.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, BottomNav.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else {

                                    hud.dismiss();
                                    Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
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
                    Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

                }

            }
        });
        txt_register =findViewById(R.id.login_register);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        txt_forgotpwd =findViewById(R.id.login_fpwd);
        txt_forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(intent);
            }
        });


    }
}
