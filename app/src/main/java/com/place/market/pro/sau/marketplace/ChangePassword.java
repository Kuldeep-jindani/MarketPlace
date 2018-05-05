package com.place.market.pro.sau.marketplace;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ChangePassword extends AppCompatActivity {


    EditText curr_pwd,new_pwd,confirm_pwd;
    Button changepwd_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        curr_pwd=findViewById(R.id.curr_pwd);
        new_pwd=findViewById(R.id.new_pwd);
        confirm_pwd=findViewById(R.id.confirm_pwd);
        changepwd_submit=findViewById(R.id.changepwd_submit);



        changepwd_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_pwd.getText().toString().equals(confirm_pwd.getText().toString())){

                    Toast.makeText(ChangePassword.this, "Password mismatch.", Toast.LENGTH_SHORT).show();

                }  else if (!Patterns.EMAIL_ADDRESS.matcher(new_pwd.getText().toString()).matches()){
                    new_pwd.setError("Enter Valid Email");
                }else if (!Patterns.EMAIL_ADDRESS.matcher(confirm_pwd.getText().toString()).matches()){
                    confirm_pwd.setError("Enter Valid Email");
                }
                else {
                    SharedPreferences preference=getSharedPreferences("status", MODE_PRIVATE);
                    String url="http://kisanunnati.com/market_place/change_password?id="+preference.getString("id","")
                            +"&current_password="+curr_pwd.getText().toString()+"&new_password="+new_pwd.getText().toString();

                    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

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
