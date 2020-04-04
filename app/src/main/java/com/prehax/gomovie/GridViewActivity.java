package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGv;
    private TextView tvSeatmapCode;
    private ArrayList<Integer> record = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        // 从上一个界面读取传入的信息
        final Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("theaterName");
        tvSeatmapCode = findViewById(R.id.tv_seatmap_code);
        setTitle(temp+" "+bundle.getString("showTimeName"));

        mGv=findViewById(R.id.gv);
        mGv.setAdapter(new MyGridViewAdapter(GridViewActivity.this));
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter();
                        myGridViewAdapter.setSeclection(position);
                        int col = 0,row = 0;
                        if(position>10){
                            col = position%10;
                            row = position/10;
                        }else{
                            col = position;
                            row = 1;
                        }

                        String c = String.valueOf(col);
                        String r = String.valueOf(row);
                        if(!record.contains(position)){
                            record.add(position);
                            tvSeatmapCode.setText("Col:"+c+"Rows:"+r+"added");
                        }else{
                            for (int i = 0;i<record.size();i++){
                                if(record.get(i)==position){
                                    record.remove(i);
                                    i--;
                                }
                            }

                            tvSeatmapCode.setText("Col:"+c+"Rows:"+r+"removed");
                        }

                        myGridViewAdapter.notifyDataSetChanged();
            }
        });




        Button btnConfirm = findViewById(R.id.btn_seats_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(GridViewActivity.this, PaymentActivity.class);
                // Seat Code
                bundle.putString("seatCode", tvSeatmapCode.getText().toString());
                bundle.putInt("numOfTic", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}
