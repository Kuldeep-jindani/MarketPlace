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
    LinearLayout english_layout,hindi_layout,gujarati_layout;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_language_select);
           SharedPreferences langPref=getSharedPreferences("langPref",MODE_PRIVATE);
       SharedPreferences.Editor editor=langPref.edit();
       english_layout=findViewById(R.id.english_layout);
       hindi_layout=findViewById(R.id.hindi_layout);
        gujarati_layout=findViewById(R.id.gujarati_layout);

        english_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","en").apply();
                setLangRecreate("en");
                Configuration config = new Configuration();
                config.locale = Locale.ENGLISH;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Context context = LocaleHelper.setLocale(LanguageSelectActivity.this, "en");
                Intent i=new Intent(context,BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        hindi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","de").apply();
                setLangRecreate("de");
                Configuration config = new Configuration();
                config.locale = Locale.GERMANY;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Context context = LocaleHelper.setLocale(LanguageSelectActivity.this, "de");
                Intent i=new Intent(context,BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        gujarati_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("lang","fr").apply();
                setLangRecreate("fr");
                Configuration config = new Configuration();
                config.locale = Locale.FRANCE;
                getResources().updateConfiguration(config, getResources().getDisplayMetrics());
                Context context = LocaleHelper.setLocale(LanguageSelectActivity.this, "fr");
                Intent i=new Intent(context,BottomNav.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
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
