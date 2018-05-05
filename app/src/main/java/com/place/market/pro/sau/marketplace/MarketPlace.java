package com.place.market.pro.sau.marketplace;

import android.app.Application;

public class MarketPlace extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/bauhaus.ttf");

    }

}
