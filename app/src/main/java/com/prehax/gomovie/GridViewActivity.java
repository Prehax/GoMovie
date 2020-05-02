package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class GridViewActivity extends AppCompatActivity {
    private GridView gvSeatMap;
    private TextView tvSeatmapCode;
    private Button btnConfirm;

    private int theaterID=0, showTimeID=0, numOfSeatSed=0;
    private ArrayList<Integer> seatSed = new ArrayList<Integer>();
    private ArrayList<Integer> record = new ArrayList<Integer>();
    private SeatMapAdapter seatMapAdapter;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Theaters");

    private int numOfRow=9, numOfCol=9;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        //find ID
        gvSeatMap = findViewById(R.id.gv_gv_map);
        tvSeatmapCode = findViewById(R.id.tv_seatmap_code);
        // 从上一个界面读取传入的信息
        final Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("theaterName");
        setTitle(temp+" "+bundle.getString("showTimeName"));
        theaterID = bundle.getInt("theaterID");
        showTimeID = bundle.getInt("showTimeID");
        // Read Database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    numOfCol = dataSnapshot.child(Integer.toString(theaterID)).child("numOfCol").getValue(Integer.class);
                    numOfRow = dataSnapshot.child(Integer.toString(theaterID)).child("numOfRow").getValue(Integer.class);
                    numOfSeatSed = (int) dataSnapshot.child(Integer.toString(theaterID)).child("ShowTimes").child(Integer.toString(showTimeID)).child("seats").getChildrenCount();
                    for (int i = 0; i < numOfSeatSed; i++) {
                        seatSed.add(dataSnapshot.child(Integer.toString(theaterID)).child("ShowTimes").child(Integer.toString(showTimeID)).child("seats").child(Integer.toString(i)).getValue(Integer.class));
                    }
                    // Toast.makeText(GridViewActivity.this, "Col "+numOfCol+"Row"+numOfRow, Toast.LENGTH_LONG).show();
                    // Set number of col
                    gvSeatMap.setNumColumns(numOfCol);
                    // Sort
                    Collections.sort(seatSed);
                    // generate and apply adapter
                    seatMapAdapter = new SeatMapAdapter(numOfCol, numOfRow, seatSed, GridViewActivity.this);
                    gvSeatMap.setAdapter(seatMapAdapter);

                } catch (NullPointerException e) {
                    System.out.println("Can not read data, null pointer");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        gvSeatMap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (seatMapAdapter.setSelected(position, view, parent)) {
                    int col = 0, row = 0;
                    col = position % numOfCol + 1;
                    row = position / numOfCol + 1;
                    String c = String.valueOf(col);
                    String r = String.valueOf(row);
                    if (!record.contains(position)) {
                        record.add(position);
                        tvSeatmapCode.setText("Col:" + c + " Row:" + r + " added");
                    } else {
                        for (int i = 0; i < record.size(); i++) {
                            if (record.get(i) == position) {
                                record.remove(i);
                                i--;
                            }
                        }
                        tvSeatmapCode.setText("Col:" + c + " Row:" + r + " removed");
                    }
                } else {
                    tvSeatmapCode.setText("Seat selection Failed");
                }
            }
        });


        btnConfirm = findViewById(R.id.btn_seats_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(GridViewActivity.this, PaymentActivity.class);
                // Seat Code
                bundle.putIntegerArrayList("seatCode", record);
                bundle.putInt("numOfCol", numOfCol);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}