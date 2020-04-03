package com.prehax.gomovie.GridView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.prehax.gomovie.PaymentActivity;
import com.prehax.gomovie.R;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        // 从上一个界面读取传入的信息
        final Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("theaterName");
        Toast.makeText(this, "You chose"+temp, Toast.LENGTH_SHORT);
        System.out.println("目前在选座界面, 电影院名是: "+temp);

        mGv=findViewById(R.id.gv);
        mGv.setAdapter(new MyGridViewAdapter(GridViewActivity.this));
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter();
                        myGridViewAdapter.setSeclection(position);
                        myGridViewAdapter.notifyDataSetChanged();
            }
        });

        final EditText etSeatCode = findViewById(R.id.et_seats_code);
        Button btnConfirm = findViewById(R.id.btn_seats_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(GridViewActivity.this, PaymentActivity.class);
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
