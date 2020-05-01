package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class CouponActivity extends AppCompatActivity {
    private static final String TAG = "AddCouponActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        Button btn_list = findViewById(R.id.btn_list);
        Button btn_addCoupon = findViewById(R.id.btn_addCoupon);
        Button btn_delCoupon = findViewById(R.id.btn_delCoupon);

        btn_list.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(CouponActivity.this,ListCouponActivity.class);
                startActivity(intent);
            }
        });

        btn_addCoupon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CouponActivity.this,AddCouponActivity.class);
                startActivity(intent);
            }
        });

        btn_delCoupon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CouponActivity.this,DeleteCouponActivity.class);
                startActivity(intent);
            }
        });

    };

}
