package com.place.market.pro.sau.marketplace;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class SelectLanguage extends Fragment {

    LinearLayout english_layout,hindi_layout,gujarati_layout;

    private BroadcastReceiver mLangaugeChangedReceiver;


    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_select_language,container,false);

        SharedPreferences langPref=getContext().getSharedPreferences("langPref",MODE_PRIVATE);

        SharedPreferences.Editor editor=langPref.edit();

        english_layout=view.findViewById(R.id.english_layout);
        hindi_layout=view.findViewById(R.id.hindi_layout);
        gujarati_layout=view.findViewById(R.id.gujarati_layout);




        english_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","en").apply();

                setLangRecreate("en");

                Context context = LocaleHelper.setLocale(getContext(), "en");
                Intent i=new Intent(getContext(),BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        hindi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","hi").apply();

                setLangRecreate("hi");

                Context context = LocaleHelper.setLocale(getContext(), "hi");
                Intent i=new Intent(getContext(),BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        gujarati_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","fr").apply();

                setLangRecreate("fr");

                Context context = LocaleHelper.setLocale(getContext(), "fr");
                Intent i=new Intent(getContext(),BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return view;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        SharedPreferences langPref=getSharedPreferences("langPref",MODE_PRIVATE);

        SharedPreferences.Editor editor=langPref.edit();

        english_layout=findViewById(R.id.english_layout);
        hindi_layout=findViewById(R.id.hindi_layout);




        english_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","en").apply();

                setLangRecreate("en");

                Context context = LocaleHelper.setLocale(getApplicationContext(), "en");
                Intent i=new Intent(getApplicationContext(),BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
               finish();
            }
        });

        hindi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","hi").apply();

                setLangRecreate("hi");

                Context context = LocaleHelper.setLocale(getApplicationContext(), "hi");
                Intent i=new Intent(getApplicationContext(),BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

    }*/
    Locale locale;

    public void setLangRecreate(String langval) {
        Configuration config = getContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
        getActivity().recreate();
    }

}
