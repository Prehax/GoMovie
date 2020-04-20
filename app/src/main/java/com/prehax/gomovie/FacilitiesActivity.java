package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FacilitiesActivity extends AppCompatActivity {
    private static final String TAG = "FacilitiesActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private ArrayList<String> Facname= new ArrayList<String>();
    private ArrayList<String> Facinculde = new ArrayList<String>();
    private ArrayList<String> Facaddress = new ArrayList<String>();
    private ArrayList<Double> Facpay = new ArrayList<Double>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);
        final ListView listView = findViewById(R.id.lv_facilities);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    long numOfFacilities=dataSnapshot.child("Theaters").child("0").child("Facilities").getChildrenCount();
                    Facname.clear();
                    Facinculde.clear();
                    Facaddress.clear();
                    Facpay.clear();
                    for (long i = 0; i<numOfFacilities; i++) {
                        Facilities facilities = dataSnapshot.child("Theaters").child("Facilities").child(Long.toString(i)).getValue(Facilities.class);
                        Facname.add(facilities.getName());
                        Facaddress.add(facilities.getAddress());
                        Facpay.add(Double.valueOf(String.format("%.1f", facilities.getPay())));
                        Facinculde.add(facilities.getInclude());
                        System.out.println("读取数据完成, 读到了以下数据: ");
                        System.out.println(facilities.getName() + " " + facilities.getAddress());
                    }
                    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                    for (int i = 0; i < Facname.size(); i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("name", Facname.get(i));
                        map.put("address", Facaddress.get(i));
                        map.put("pay", "Average pay: "+ Facpay.get(i));
                        map.put("include",Facinculde.get(i));
                        listItem.add(map);
                    }
                    SimpleAdapter listItemAdapter = new SimpleAdapter(FacilitiesActivity.this, listItem,// 数据源
                            R.layout.layout_list_choose,// ListItem的XML实现
                            // 动态数组与Item对应的子项
                            new String[] { "name", "address", "pay" ,"include"},
                            // XML文件里面的3个TextView ID
                            new int[] { R.id.Facname, R.id.Facaddress, R.id.Facpaid,R.id.Facinclude });
                    listView.setAdapter(listItemAdapter);
                }catch(NullPointerException e)
                {
                    Log.w(TAG, "读取数据未完成, 遭遇空指针错误");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
