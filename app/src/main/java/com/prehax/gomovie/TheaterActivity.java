package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.xwray.groupie.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TheaterActivity extends AppCompatActivity {
    private static final String TAG = "TheaterActivity";
    // For storing data temporary
    private ArrayList<String> Tname = new ArrayList<String>();
    private ArrayList<String> Taddress = new ArrayList<String>();
    private ArrayList<String> Trate = new ArrayList<String>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    private Bundle bundle = new Bundle();
    private String movieName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater);
        // Find ID 必须在这里, 不能设置为 private member
        final ListView listView = findViewById(R.id.lv_theater);
        //
        bundle = getIntent().getExtras();
//        movieName = ("Bloodshot");
        movieName = bundle.getString("movieName");
        // Read data from databese
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    System.out.println("开始读取数据");
                    long numOfTheaters=dataSnapshot.child("Theaters").getChildrenCount();
                    System.out.println(numOfTheaters);
                    // The following 3 line are prevent it from reading multiple times
                    Tname.clear();
                    Taddress.clear();
                    Trate.clear();
                    for (long i = 0; i<numOfTheaters; i++) {
                        Theater theater = dataSnapshot.child("Theaters").child(Long.toString(i)).getValue(Theater.class);
                        Tname.add(theater.getName());
                        Taddress.add(theater.getAddress());
                        Trate.add(String.format("%.1f", theater.getRate()));
                        System.out.println("读取数据完成, 读到了以下数据: ");
                        System.out.println(theater.getName() + " " + theater.getAddress());
                    }
                    // Generate array
                    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                    for (int i = 0; i < Tname.size(); i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", Tname.get(i));
                        map.put("address", Taddress.get(i));
                        map.put("rate", "Rate: "+ Trate.get(i));
                        listItem.add(map);
                    }
                    // Items
                    SimpleAdapter listItemAdapter = new SimpleAdapter(TheaterActivity.this, listItem,// 数据源
                            R.layout.layout_list_choose,// ListItem的XML实现
                            // 动态数组与Item对应的子项
                            new String[] { "name", "address", "rate" },
                            // XML文件里面的3个TextView ID

                            new int[] { R.id.Lcname, R.id.Lcaddress, R.id.Lcshowtime });

                    // Set Adapter
                    listView.setAdapter(listItemAdapter);
                } catch (NullPointerException e) {
                    Log.w(TAG, "读取数据未完成, 遭遇空指针错误");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // OnClick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // setTitle("OnClick No." + (position+1) + " item");
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(TheaterActivity.this, showActivity.class);

                bundle.putInt("theaterID", position);
                bundle.putString("theaterName", Tname.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
        // OnLong Click
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle("OnLongClick No." + (position+1) + " item");
                Intent intent = new Intent(TheaterActivity.this,FacilitiesActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
    // click on menu's item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        setTitle("On Click the menu " + item.getItemId());
//        if(item.getItemId() == 0){
//            Intent intent = new Intent(TheaterActivity.this, showActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putString("tposition", Tname.get(position));
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }

        return super.onContextItemSelected(item);
    }
}

