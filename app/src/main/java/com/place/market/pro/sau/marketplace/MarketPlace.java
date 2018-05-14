package com.place.market.pro.sau.marketplace;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class MarketPlace extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/bauhaus.ttf");

    }


   /* @Override
    protected void attachBaseContext(Context base) {
        SharedPreferences langPref = base.getSharedPreferences("langPref", MODE_PRIVATE);

      *//*  if (langPref.getString("lang", "").equals("english"))
            super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        else if (langPref.getString("lang", "").equals("hindi"))
            super.attachBaseContext(LocaleHelper.onAttach(base, "hi"));
        else {
            super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        }*//*


//        SharedPreferences langPref=getSharedPreferences("langPref",MODE_PRIVATE);
        Configuration config = getBaseContext().getResources().getConfiguration();

        String lang = langPref.getString("lang", "");
        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang)) {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
    }*/

}
