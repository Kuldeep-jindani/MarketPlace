package com.place.market.pro.sau.marketplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
Button btn_send_email;
    EditText email;
    TextView login_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.fpwd_email);
        btn_send_email = findViewById(R.id.fpwd_send_email);
        login_reg = findViewById(R.id.login_reg);

        login_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals(""))
                {
                   email.setError("Enter Contact Number");
                }
                else if(email.length()!=10)
                {
                    email.setError("Please enter valid Contact Number.");
                }
                else {
                    final KProgressHUD hud = KProgressHUD.create(ForgotPassword.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    String url = "http://kisanunnati.com/marketplace/ForgotPassword?contact="+email.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            hud.dismiss();
                            Log.e("forgot pass Url",url);
                            Log.e("forgot pass resp",response);

                            try {
                                JSONObject responseObj=new JSONObject(response);
                                if (responseObj.getInt("status")==0){
                                    Toast.makeText(ForgotPassword.this, "Forgot Password link has been sent to your registered mobile number.", Toast.LENGTH_SHORT).show();
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
    }
}
