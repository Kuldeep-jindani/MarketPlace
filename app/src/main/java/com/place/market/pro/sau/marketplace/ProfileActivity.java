package com.place.market.pro.sau.marketplace;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.place.market.pro.sau.marketplace.ImageUploading.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ProfileActivity extends Fragment {
    Button img1;
    ImageView edit;
    Button btn_change_pwd;
    EditText edt_name,edt_cnum,edt_email;
    RadioGroup edt_gender;
    RadioButton radioButtong,male,female;
    ImageView profile_pic;

    TextView edt_bdate;
    public int mYear, mMonth, mDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        img1 =view.findViewById(R.id.logout);
        male =view.findViewById(R.id.male);
        female =view.findViewById(R.id.female);

        edit = view.findViewById(R.id.edit);
        edt_name = view.findViewById(R.id.name);
        edt_email = view.findViewById(R.id.email);
        edt_gender = view.findViewById(R.id.gender);
        edt_cnum = view.findViewById(R.id.cnum);
        edt_bdate = view.findViewById(R.id.bdate);
        edt_name.setClickable(false);
        edt_email.setClickable(false);
        edt_cnum.setClickable(false);
        edt_bdate.setClickable(false);
        edt_gender.setClickable(false);


        edt_name.setFocusable(false);
        edt_email.setFocusable(false);
        edt_cnum.setFocusable(false);
        edt_bdate.setFocusable(false);
        edt_gender.setFocusable(false);


        profile_pic=view.findViewById(R.id.profile_pic);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear().apply();
                Intent i = new Intent(getContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });
        SharedPreferences pre=getContext().getSharedPreferences("status", MODE_PRIVATE);
        Log.e("Profile Pic",pre.getString("propic",""));
        Glide.with(getContext()).load(pre.getString("propic","")).into(profile_pic);
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btn_change_pwd = view.findViewById(R.id.change_pwd);
        btn_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ChangePassword.class);
                startActivity(intent);
            }
        });



        edt_name.setText(preferences.getString("name",""));
        edt_cnum.setText(preferences.getString("contact",""));
        edt_bdate.setText(preferences.getString("birthdate",""));

        if (preferences.getString("gender","").equals("Male"))
            male.setChecked(true);
        else female.setChecked(true);


        edt_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                @SuppressLint("ResourceType")
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date dx = new Date(year - 1900, monthOfYear, dayOfMonth);
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String cdate = formatter.format(dx);
                                edt_bdate.setText(cdate);
                                // txt_setdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                edt_bdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth );
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_edit).getConstantState())
                {
                    edt_name.setClickable(true);
                    edt_email.setClickable(false);
                    edt_cnum.setClickable(true);
                    edt_bdate.setClickable(true);
                    edt_gender.setClickable(true);

                    edt_name.setFocusableInTouchMode(true);
                    edt_email.setFocusableInTouchMode(true);
                    edt_cnum.setFocusableInTouchMode(true);
                    edt_bdate.setFocusableInTouchMode(true);
                    edt_gender.setFocusableInTouchMode(true);


                    edt_name.setFocusable(true);
                    edt_email.setFocusable(false);
                    edt_cnum.setFocusable(false);
                    edt_bdate.setFocusable(true);
                    edt_gender.setFocusable(true);
                    edit.setImageDrawable(getResources().getDrawable(R.drawable.ic_save));
                }
                else {
                    edit.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                    int selectedId = edt_gender.getCheckedRadioButtonId();
                    radioButtong =  edt_gender.findViewById(selectedId);

                    edt_name.setFocusable(false);
                    edt_email.setFocusable(false);
                    edt_cnum.setFocusable(false);
                    edt_bdate.setFocusable(false);
                    edt_gender.setFocusable(false);

        String url = "http://kisanunnati.com/market_place/edit_profile?id="+preferences.getString("id","") +
        "&name=" +edt_name.getText().toString()+
        "&email=" +edt_email.getText().toString()+
        "&gender=" +radioButtong.getText().toString()+
        "&contact=" +edt_cnum.getText().toString()+
        "&birthdate="+edt_bdate.getText().toString();
                    StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                Log.e("Update profiole url ",url);
                                Log.e("Update profiole response",response);
                                if (resp.getInt("status") == 0) {
                                    JSONArray data = resp.getJSONArray("data");
                                    JSONObject object = (JSONObject) data.get(0);
                                    SharedPreferences preferences = getContext().getSharedPreferences("status", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", object.getString("id"));
                                    editor.putString("name", edt_name.getText().toString());
                                    editor.putString("email", edt_email.getText().toString());
                                    editor.putString("gender", radioButtong.getText().toString());
                                    editor.putString("birthdate", edt_bdate.getText().toString());
                                    editor.putString("contact", edt_cnum.getText().toString()).apply();
                                    Toast.makeText(getContext(), "Updated Successfull.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), BottomNav.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
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

        return view;
    }

    String userChoosenTask;
    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_REQUEST);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    int CROP_REQUEST = 1003;

    int CAMERA_REQUEST = 1001;
    int GALLERY_REQUEST = 1002;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                onSelectFromGalleryResult(data);
                Bundle extras = data.getExtras();
                assert extras != null;
//                final Bitmap imageBitmap = (Bitmap) extras.get("data");

                Bitmap imageBitmap = null;
                try {
                    imageBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                    uploadBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                convertToString(imageBitmap);
            } else if (requestCode == CAMERA_REQUEST) {
                onCaptureImageResult(data);
                Bundle extras = data.getExtras();
                assert extras != null;
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
//                convertToString(imageBitmap);
                uploadBitmap(imageBitmap);
            } else if (requestCode == CROP_REQUEST) {
                Bundle extras = data.getExtras();
                assert extras != null;
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
            }

        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());

                //uploadImage("data");
                //addimg_uid.getText().toString();
/*

                String uploadURL;
                VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, uploadURL,
                        new Response.Listener<NetworkResponse>() {
                            @Override
                            public void onResponse(NetworkResponse response) {
                                try {
                                    //hud.dismiss();
                                    JSONObject obj = new JSONObject(new String(response.data));

                                    if (obj.getInt("status") == 1) {


                                        Log.e("response", obj.toString());
                                        Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                        if (Aadhar1 == 1) {
                                            addimg_uid.setText(obj.getString("imageurl"));

                                        }
                                        if (Aadhar2 == 1)
                                            addimg_uid2.setText(obj.getString("imageurl"));

                                        if (Landpics == 1)
                                            addimg_uid3.setText(obj.getString("imageurl"));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bitmap bb = getResizedBitmap(bm, 500);
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bb.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();


        assert data != null;

    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext

        //our custom volley request

        String uploadURL = "http://kisanunnati.com/market_place/profilePicUpload";


        if (!uploadURL.equals("")) {
            final KProgressHUD hud = KProgressHUD.create(getContext())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            Log.e("image upload url",uploadURL);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, uploadURL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                hud.dismiss();
                                JSONObject obj = new JSONObject(new String(response.data));

                                Log.e("response", obj.toString());
                                if (obj.getInt("status") == 1) {

                                    SharedPreferences preferences=getContext().getSharedPreferences("status", MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.viewpager, new ProfileActivity()).commit();
                                    editor.putString("propic",obj.getString("imageurl"));

                                    editor.apply();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();


                        params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bb = getResizedBitmap(thumbnail, 500);
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bb.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();


    }
    //Resize of bitmap
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
