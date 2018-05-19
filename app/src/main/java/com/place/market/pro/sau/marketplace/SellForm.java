package com.place.market.pro.sau.marketplace;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.place.market.pro.sau.marketplace.ImageUploading.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class SellForm extends Fragment {
    ImageView  img_preview1,img_preview2,img_preview3,img_preview4,img_preview5;
    String userChoosenTask;
    int CAMERA_REQUEST = 1001;
    int GALLERY_REQUEST = 1002;
    Button img_next;
    TextView addimg_uid, addimg_uid2, addimg_uid3;


    int pic1 = 0;
    int pic2 = 0;
    int pic3 = 0;
    int pic4 = 0;
    int pic5 = 0;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sell_form, container, false);

        preferences= Objects.requireNonNull(getContext()).getSharedPreferences("image",MODE_PRIVATE);
        editor=preferences.edit();

        SharedPreferences langPref=getActivity().getSharedPreferences("langPref",MODE_PRIVATE);
        LocaleHelper.setLocale(getContext(), langPref.getString("lang","en"));

editor.clear().apply();


//        SharedPreferences langPref=getSharedPreferences("langPref",MODE_PRIVATE);
        Configuration config = getContext().getResources().getConfiguration();

        String lang = langPref.getString("lang", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
        }


        img_preview1 = view.findViewById(R.id.preview_img1);
        img_preview2 = view.findViewById(R.id.preview_img2);
        img_preview3 = view.findViewById(R.id.preview_img3);
        img_preview4 = view.findViewById(R.id.preview_img4);
        img_preview5 = view.findViewById(R.id.preview_img5);
        img_next = view.findViewById(R.id.next);
        addimg_uid =view.findViewById(R.id.addimg_uid);
        addimg_uid2 =view.findViewById(R.id.addimg_uid2);
        addimg_uid3 = view.findViewById(R.id.addimg_uid3);
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    if (!preferences.contains("image1") &&
                        !preferences.contains("image2") &&
                        !preferences.contains("image3") &&
                        !preferences.contains("image4") &&
                        !preferences.contains("image5") ){
                    Toast.makeText(getContext(), R.string.image_toast, Toast.LENGTH_SHORT).show();
                }else {
                  /*  Intent intent = new Intent(getContext(), SellDetails.class);
                    startActivity(intent);*/

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.viewpager, new SellDetails()).commit();
                }
            }
        });


        img_preview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic1 = 1;
                pic2 = 0;
                pic3 = 0;
                pic4 = 0;
                pic5 = 0;
                selectImage();
            }
        });  img_preview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic1 = 0;
                pic2 = 1;
                pic3 = 0;
                pic4 = 0;
                pic5 = 0;
                selectImage();
            }
        });  img_preview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic1 = 0;
                pic2 = 0;
                pic3 = 1;
                pic4 = 0;
                pic5 = 0;
                selectImage();
            }
        });  img_preview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic1 = 0;
                pic2 = 0;
                pic3 = 0;
                pic4 = 1;
                pic5 = 0;
                selectImage();
            }
        });  img_preview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pic1 = 0;
                pic2 = 0;
                pic3 = 0;
                pic4 = 0;
                pic5 = 1;
                selectImage();
            }
        });
return  view;
    }

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
                bm = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(getContext()).getContentResolver(), data.getData());

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

        String uploadURL = "";
        if (pic1 == 1) {
            uploadURL = Config.FILE_UPLOAD_URL1;
        }if (pic2 == 1) {
            uploadURL = Config.FILE_UPLOAD_URL2;
        }if (pic3 == 1) {
            uploadURL = Config.FILE_UPLOAD_URL3;
        }if (pic4 == 1) {
            uploadURL = Config.FILE_UPLOAD_URL4;
        }if (pic5 == 1) {
            uploadURL = Config.FILE_UPLOAD_URL5;
        }


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



                                    if (pic1 == 1) {
                                        editor.putString("image1",obj.getString("imageurl"));
//                                        addimg_uid.setText(obj.getString("imageurl"));
//                                        btn_addimg1.setText("Done/Edit");
//                                        btn_addimg1.setTextColor(getContext().getResources().getColor(R.color.green));

                                        Glide.with(getContext()).load(obj.getString("imageurl")).into(img_preview1);
                                    } if (pic2 == 1) {
                                        editor.putString("image2",obj.getString("imageurl"));
//                                        addimg_uid.setText(obj.getString("imageurl"));
//                                        btn_addimg1.setText("Done/Edit");
//                                        btn_addimg1.setTextColor(getContext().getResources().getColor(R.color.green));
                                        Glide.with(getContext()).load(obj.getString("imageurl")).into(img_preview2);
                                    } if (pic3 == 1) {
                                        editor.putString("image3",obj.getString("imageurl"));
//                                        addimg_uid.setText(obj.getString("imageurl"));
//                                        btn_addimg1.setText("Done/Edit");
//                                        btn_addimg1.setTextColor(getContext().getResources().getColor(R.color.green));
                                        Glide.with(getContext()).load(obj.getString("imageurl")).into(img_preview3);
                                    } if (pic4 == 1) {
                                        editor.putString("image4",obj.getString("imageurl"));
//                                        addimg_uid.setText(obj.getString("imageurl"));
//                                        btn_addimg1.setText("Done/Edit");
//                                        btn_addimg1.setTextColor(getContext().getResources().getColor(R.color.green));
                                        Glide.with(getContext()).load(obj.getString("imageurl")).into(img_preview4);
                                    } if (pic5 == 1) {
                                        editor.putString("image5",obj.getString("imageurl"));
//                                        addimg_uid.setText(obj.getString("imageurl"));
//                                        btn_addimg1.setText("Done/Edit");
//                                        btn_addimg1.setTextColor(getContext().getResources().getColor(R.color.green));
                                        Glide.with(getContext()).load(obj.getString("imageurl")).into(img_preview5);
                                    }
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

                    if (pic1 == 1) {
                        params.put("image1", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    } if (pic2 == 1) {
                        params.put("image2", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    } if (pic3 == 1) {
                        params.put("image3", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    } if (pic4 == 1) {
                        params.put("image4", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    } if (pic5 == 1) {
                        params.put("image5", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    }
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
