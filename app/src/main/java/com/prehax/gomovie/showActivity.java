package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class showActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private ListView show;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Theaters");
    private int theaterID;
    private String theaterName;
    private String[] showTimes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        // get data from activity before from bundle
        final Bundle bundle = getIntent().getExtras();
        theaterName = bundle.getString("theaterName");
        theaterID = bundle.getInt("theaterID");
//        theaterName = ("Nuart");
//        theaterID = 0;

        setTitle(theaterName);
        // Find Id for the listView
        show = findViewById(R.id.lv_show);
        // Read from database once
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    long numOfShow = dataSnapshot.child(Integer.toString(theaterID)).child("ShowTimes").getChildrenCount();
                    showTimes = new String[(int) numOfShow];
                    String showTime = "";
                    for (int i = 0; i < numOfShow; i++) {
                        showTime = dataSnapshot.child(Integer.toString(theaterID)).child("ShowTimes").child(Long.toString(i)).child("time").getValue(String.class);
                        showTimes[i] = (showTime);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(showActivity.this, android.R.layout.simple_list_item_1,showTimes);
                    show.setAdapter(adapter);
                } catch (NullPointerException e) {
                    System.out.println("Can not read, no data is there");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Can not read data");
            }
        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(showActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();

                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(showActivity.this, GridViewActivity.class);
                // ShowTime Information
                bundle.putInt("showTimeID", position);
                bundle.putString("showTimeName", showTimes[position]);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}
