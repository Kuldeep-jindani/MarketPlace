package com.place.market.pro.sau.marketplace;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_email,edt_uname,edt_pwd,edt_cnum;
    Button btn_register;
    public int mYear, mMonth, mDay;
    ImageView img_calender;
    TextView txt_setdate;
    private RadioGroup radioGroup,radioGroup1;
    private RadioButton radioButtong,radioButtonc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_uname = findViewById(R.id.register_user);
        edt_email = findViewById(R.id.register_email);
        edt_cnum = findViewById(R.id.register_cnum);
        edt_pwd = findViewById(R.id.register_pwd);
        btn_register = findViewById(R.id.register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup =  findViewById(R.id.gender);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButtong =  findViewById(selectedId);

                /*Toast.makeText(RegisterActivity.this,
                        radioButtong.getText(), Toast.LENGTH_SHORT).show();*/

               /* radioGroup1 =  findViewById(R.id.category);
                int selectedId1 = radioGroup1.getCheckedRadioButtonId();
                radioButtonc =  findViewById(selectedId1);
                Toast.makeText(RegisterActivity.this,
                        radioButtonc.getText(), Toast.LENGTH_SHORT).show();
*/
                if (edt_uname.getText().toString().equals("") ||
                        edt_email.getText().toString().equals("") ||
                        edt_pwd.getText().toString().equals("") ||
                        edt_cnum.getText().toString().equals(""))
                {
                    edt_uname.setError("Enter Name");
                    edt_email.setError("Enter Email");
                    edt_pwd.setError("Enter Password");
                    edt_cnum.setError("Enter Contact Number");
                }else if (edt_pwd.getText().toString().length()<6){
                    edt_pwd.setError("Password must be greater than 6 characters.");
                }
                else if (edt_cnum.getText().toString().length()<10){
                    edt_cnum.setError("Enter valid Contact Number");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(edt_email.getText().toString()).matches()){
                    edt_email.setError("Enter Valid Email");
                }




                else {
                    String url = " http://kisanunnati.com/market_place/registration?" +
                            "name=" +edt_uname.getText().toString()+
                            "&email="+ edt_email.getText().toString()+
                            "&password="+edt_pwd.getText().toString()+
                            "&gender=" + radioButtong.getText()+
                            "&contact=" +edt_cnum.getText().toString()+
                            "&birthdate=" +txt_setdate.getText().toString();
//                            "&category=0";
                    final KProgressHUD hud = KProgressHUD.create(RegisterActivity.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    Log.e("Registration url",url);
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
                                    editor.putString("email", object.getString("email"));
                                    editor.putString("password", edt_pwd.getText().toString());
                                    editor.putString("gender",  object.getString("gender"));
                                    editor.putString("birthdate", object.getString("birthdate"));
                                    editor.putString("contact",object.getString("contact")).apply();
                                    hud.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Register Successfull.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {

                                    hud.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Invalid register", Toast.LENGTH_SHORT).show();
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
        img_calender = findViewById(R.id.img_calender);
        LinearLayout register_dob_layout=findViewById(R.id.register_dob_layout);
        txt_setdate= findViewById(R.id.set_date);
        register_dob_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("ResourceType")
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date dx = new Date(year - 1900, monthOfYear, dayOfMonth);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                String cdate = formatter.format(dx);
                                txt_setdate.setText(cdate);
                                txt_setdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });



    }


    }


