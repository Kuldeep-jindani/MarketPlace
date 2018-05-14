package com.place.market.pro.sau.marketplace;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.view.View;
import android.widget.LinearLayout;



public class LanguageSelectActivity extends AppCompatActivity {

    LinearLayout english_layout,hindi_layout;


    @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_language_select);

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
               Intent i=new Intent(getApplicationContext(),LoginActivity.class);
               i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(i);
               finish();
           }
       });

   }
    Locale locale;

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

}
