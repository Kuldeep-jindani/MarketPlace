package com.place.market.pro.sau.marketplace;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.place.market.pro.sau.marketplace.Extra.Paginator;
import com.place.market.pro.sau.marketplace.Extra.Paginator_history;
import com.srx.widget.PullToLoadView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class History extends  Fragment{

    PullToLoadView recyclar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_history,container,false);

        recyclar=view.findViewById(R.id.recyclar);
        new Paginator_history(getContext(),recyclar,getFragmentManager());

        return view;
    }

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclar=findViewById(R.id.recyclar);
        new Paginator_history(getApplication(),recyclar,getSupportFragmentManager());

    }*/
}
