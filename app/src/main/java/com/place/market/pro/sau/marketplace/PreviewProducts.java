package com.place.market.pro.sau.marketplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.place.market.pro.sau.marketplace.Adapters.Preview_imageSlider_adapter;
import com.place.market.pro.sau.marketplace.Extra.Grid_model;

import java.time.OffsetTime;
import java.util.ArrayList;

public class PreviewProducts extends AppCompatActivity {
    Button contact_buy;
    RecyclerView preview_imagescroll;
    TextView txt_name,txt_desc,txt_price,sell_name,sell_date,preview_tags,preview_remark;
    RelativeLayout ln;
    PinEntryEditText pinEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_products);
        contact_buy = findViewById(R.id.buy_now);
        preview_imagescroll = findViewById(R.id.preview_imagescroll);






        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);

preview_imagescroll.setLayoutManager(linearLayoutManager);

        Preview_imageSlider_adapter preview_imageSlider_adapter=new Preview_imageSlider_adapter(getApplicationContext());

        preview_imagescroll.setAdapter(preview_imageSlider_adapter);

        contact_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ln= findViewById(R.id.ln_opt);
                ln.setVisibility(View.VISIBLE);
                pinEntry = findViewById(R.id.txt_pin_entry);
                if (pinEntry != null) {
                    pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                        @Override
                        public void onPinEntered(CharSequence str) {
                            if (str.toString().equals("123456")) {
                                Toast.makeText(PreviewProducts.this, "SUCCESS", Toast.LENGTH_SHORT).show();

                                ln.setVisibility(View.INVISIBLE);
                            } else {
                                Toast.makeText(PreviewProducts.this, "FAIL", Toast.LENGTH_SHORT).show();
                                pinEntry.setText(null);
                            }
                        }
                    });
                }

            }
        });
        txt_desc=findViewById(R.id.preview_desc);
        txt_name=findViewById(R.id.preview_name);
        txt_price=findViewById(R.id.preview_price);
        sell_name=findViewById(R.id.sell_name);
        sell_date=findViewById(R.id.sell_date);
        preview_tags=findViewById(R.id.preview_tags);
        preview_remark=findViewById(R.id.preview_remark);

sell_name.setText(getIntent().getStringExtra("uploader_name"));
txt_name.setText(getIntent().getStringExtra("product_name"));
txt_desc.setText(getIntent().getStringExtra("description"));
txt_price.setText(getIntent().getStringExtra("price"));
preview_remark.setText(getIntent().getStringExtra("remarks"));
sell_date.setText(getIntent().getStringExtra("created_at"));


    }
}
