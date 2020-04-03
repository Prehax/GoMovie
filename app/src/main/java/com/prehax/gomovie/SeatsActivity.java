

package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.prehax.gomovie.ListViewC.showActivity;


public class SeatsActivity extends AppCompatActivity {
    private SeatTable seatTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);
        // 从上一个界面读取传入的信息
        final Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("theaterName");
        Toast.makeText(this, "You chose"+temp, Toast.LENGTH_SHORT);
        System.out.println("目前在选座界面, 电影院名是: "+temp);
        seatTable=findViewById(R.id.seats);
        seatTable.setData(10,15);
        // Find ID
        Button btnConfirm = findViewById(R.id.btn_seats_confirm);
        final EditText etSeatCode = findViewById(R.id.et_seats_code);

        // Button Activity
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(SeatsActivity.this, PaymentActivity.class);
                // Seat Code
                bundle.putString("seatCode", etSeatCode.getText().toString());
                bundle.putInt("numOfTic", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }

}
