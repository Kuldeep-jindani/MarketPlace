package com.place.market.pro.sau.marketplace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.place.market.pro.sau.marketplace.Extra.Paginator;
import com.place.market.pro.sau.marketplace.Extra.Paginator_history;
import com.srx.widget.PullToLoadView;

public class History extends AppCompatActivity {

    PullToLoadView recyclar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclar=findViewById(R.id.recyclar);
        new Paginator_history(getApplication(),recyclar);

    }
}
