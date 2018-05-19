package com.place.market.pro.sau.marketplace;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ContractFragment extends Fragment {
    EditText edt_name,edt_address,edt_pnum,edt_cnum,edt_survey_no,
            edt_area_of_farm_in_hactor,edt_name_of_comodity,edt_expectation_price_for_hector;
    TextView txt_district, txt_taluka, txt_village,txt_villageid,txt_id1,txt_id;
    Button btn_submit;
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_contract, container, false);
        edt_name = view.findViewById(R.id.edt_name);
        txt_id = view.findViewById(R.id.dist_id);
        txt_id1 = view.findViewById(R.id.taluka_id);
        txt_villageid = view.findViewById(R.id.village_id);
        edt_address = view.findViewById(R.id.edt_address);
        edt_pnum = view.findViewById(R.id.edt_contact_number);
        edt_cnum = view.findViewById(R.id.edt_mobile_number);
        edt_survey_no = view.findViewById(R.id.edt_survey_no);
        edt_area_of_farm_in_hactor = view.findViewById(R.id.edt_area_of_farm_in_hactor);
        edt_name_of_comodity = view.findViewById(R.id.edt_name_of_comodity);
        edt_expectation_price_for_hector = view.findViewById(R.id.edt_expectation_price_for_hector);
        txt_district = view.findViewById(R.id.district);
        txt_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final KProgressHUD hud = KProgressHUD.create(getContext())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.list);
                lv = dialog.findViewById(R.id.lv);
                dialog.setCancelable(true);
                String url = "http://kisanunnati.com/market_place/district_list";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Dialog Responce", response);
                            try {
                                JSONObject resp = new JSONObject(response);
                                SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                if (resp.getInt("status") == 0) {
                                    JSONArray data = resp.getJSONArray("data");

                                    hud.dismiss();
                                    final String[] stringArray = new String[data.length()];
                                    final int[] intArray = new int[data.length()];
                                    for (int i = 0, count = data.length(); i < count; i++) {
                                        try {
                                            JSONObject object = (JSONObject) data.get(i);
                                            String jsonString = object.getString("districts_name");
                                            stringArray[i] = jsonString;
                                            int id = object.getInt("id");
                                            intArray[i] = id;

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, stringArray);
                                    lv.setAdapter(adapter);
                                    dialog.show();
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            TextView textView = (TextView) view;
                                            txt_district.setText(stringArray[i]);
                                            txt_id.setText(String.valueOf(intArray[i]));
                                            dialog.dismiss();
                                        }
                                    });

                                } else {
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return new HashMap<>();
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(stringRequest);

            }
        });
        txt_taluka = view.findViewById(R.id.taluka);

        txt_taluka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_id.getText().toString().equals("0"))
                {
                    Toast.makeText(getContext(), "Select District First.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final KProgressHUD hud = KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.list);
                    lv = dialog.findViewById(R.id.lv);
                    dialog.setCancelable(true);
                    String url = "http://kisanunnati.com/market_place/taluka_list?districts_id=" + txt_id.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Taluka URL", url);
                            Log.e("Taluka Responce", response);
                            try {
                                JSONObject resp = new JSONObject(response);
                                SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                if (resp.getInt("status") == 0) {
                                    JSONArray data = resp.getJSONArray("talukas");

                                    hud.dismiss();
                                    final String[] stringArray = new String[data.length()];
                                    final int[] intArray = new int[data.length()];
                                    for (int i = 0, count = data.length(); i < count; i++) {
                                        try {
                                            JSONObject object = (JSONObject) data.get(i);
                                            String jsonString = object.getString("talukas_name");
                                            stringArray[i] = jsonString;
                                            int id = object.getInt("id");
                                            intArray[i] = id;

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, stringArray);
                                    lv.setAdapter(adapter);
                                    dialog.show();
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            TextView textView = (TextView) view;
                                            txt_taluka.setText(stringArray[i]);
                                            txt_id1.setText(String.valueOf(intArray[i]));
                                            dialog.dismiss();
                                        }
                                    });

                                } else {
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return new HashMap<>();
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(stringRequest);
                }
            }
        });
        txt_village = view.findViewById(R.id.village);

        txt_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_id1.getText().toString().equals("0"))
                {
                    Toast.makeText(getContext(), "Select Taluka First.", Toast.LENGTH_SHORT).show();
                }
                else {
                    final KProgressHUD hud = KProgressHUD.create(getContext())
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    final Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.list);
                    lv = dialog.findViewById(R.id.lv);
                    dialog.setCancelable(true);
                    String url = "http://kisanunnati.com/market_place/village_list?talukas_id=" + txt_id1.getText().toString();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Village URL", url);
                            Log.e("Village Responce", response);
                            try {
                                JSONObject resp = new JSONObject(response);
                                SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                if (resp.getInt("status") == 0) {
                                    JSONArray data = resp.getJSONArray("village");

                                    hud.dismiss();
                                    final String[] stringArray = new String[data.length()];
                                    final int[] intArray = new int[data.length()];
                                    for (int i = 0, count = data.length(); i < count; i++) {
                                        try {
                                            JSONObject object = (JSONObject) data.get(i);
                                            String jsonString = object.getString("village_name");
                                            stringArray[i] = jsonString;
                                            int id = object.getInt("id");
                                            intArray[i] = id;

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, stringArray);
                                    lv.setAdapter(adapter);
                                    dialog.show();
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            TextView textView = (TextView) view;
                                            txt_village.setText(stringArray[i]);
                                            txt_villageid.setText(String.valueOf(intArray[i]));
                                            dialog.dismiss();
                                        }
                                    });

                                } else {
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return new HashMap<>();
                        }
                    };
                    Volley.newRequestQueue(getContext()).add(stringRequest);
                }
            }
        });
        btn_submit = view.findViewById(R.id.contract_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (edt_name.getText().toString().equals("")
                    || edt_address.getText().toString().equals("")
                    || edt_pnum.getText().toString().equals("")
                     || edt_cnum.getText().toString().equals("")
                    || txt_village.getText().toString().equals("")
                    || txt_district.getText().toString().equals("")
                    || txt_taluka.getText().toString().equals("")
                        || edt_survey_no.getText().toString().equals("")
                        || edt_area_of_farm_in_hactor.getText().toString().equals("")
                        || edt_name_of_comodity.getText().toString().equals("")
                        || edt_expectation_price_for_hector.getText().toString().equals(""))
            {
                Toast.makeText(getContext(), R.string.toast_contract, Toast.LENGTH_SHORT).show();
            }
            else {
                String url = "http://kisanunnati.com/market_place/form_contract?"+
                "&name="+edt_name.getText().toString()+
                        "&address="+edt_address.getText().toString().replace(" ","%20")+
                        "&phone_number="+edt_pnum.getText().toString()+
                        "&mobile_number="+edt_cnum.getText().toString()+
                        "&village="+txt_village.getText().toString().replace(" ","%20")+
                        "&survey_number="+edt_survey_no.getText().toString()+
                                "&area_of_farm_in_hactor="+edt_area_of_farm_in_hactor.getText().toString()+
                                "&district="+txt_district.getText().toString().replace(" ","%20")+
                                "&taluka="+txt_taluka.getText().toString().replace(" ","%20")+
                                        "&name_of_comodity="+edt_name_of_comodity.getText().toString().replace(" ","%20")+
                                                "&expectation_price_for_hector="+edt_expectation_price_for_hector.getText().toString();
                final KProgressHUD hud = KProgressHUD.create(getContext())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject resp = new JSONObject(response);
                            if (resp.getInt("status") == 0){
                                JSONArray data = resp.getJSONArray("data");
                                JSONObject object = (JSONObject) data.get(0);
                                SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id", object.getString("id"));
                                editor.putString("name", object.getString("name"));
                                editor.putString("address", object.getString("address"));
                                editor.putString("phone_number", object.getString("phone_number"));
                                editor.putString("mobile_number",  object.getString("mobile_number"));
                                editor.putString("village", object.getString("village"));
                                editor.putString("area_of_farm_in_hactor", object.getString("area_of_farm_in_hactor"));
                                editor.putString("district", object.getString("district"));
                                editor.putString("taluka", object.getString("taluka"));
                                editor.putString("name_of_comodity", object.getString("name_of_comodity"));
                                editor.putString("expectation_price_for_hector", object.getString("expectation_price_for_hector"));
                                editor.putString("survey_number",object.getString("survey_number")).apply();
                                hud.dismiss();
                                Toast.makeText(getContext(), "Contract Successfull.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), BottomNav.class);
                                startActivity(intent);
                            }
                            else {
                                hud.dismiss();
                                Toast.makeText(getContext(), "Invalid Contract", Toast.LENGTH_SHORT).show();
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
                Volley.newRequestQueue(getContext()).add(stringRequest);
            }
            }
        });

        return  view;
    }

}

