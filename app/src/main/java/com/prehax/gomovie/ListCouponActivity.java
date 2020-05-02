package com.prehax.gomovie;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ListCouponActivity extends AppCompatActivity {
    private static final String TAG = "ListCouponActivity";
    private DatabaseReference myRef;
    private ListView listView;
    private int numOfCop;
    private ArrayList<String> couponNames = new ArrayList<>();
    private ArrayList<String> couponDiscounts = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcoupon);
        listView = findViewById(R.id.CouponList);

        myRef = FirebaseDatabase.getInstance().getReference("Coupons");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                numOfCop = (int) dataSnapshot.getChildrenCount();
                for (int i = 0; i<numOfCop; i++) {
                    couponNames.add(dataSnapshot.child(Integer.toString(i)).child("couponName").getValue(String.class));
                    couponDiscounts.add(dataSnapshot.child(Integer.toString(i)).child("couponDiscount").getValue(String.class));
                }
                // Generate array
                ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                for (int j = 0; j < numOfCop; j++) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("name", couponNames.get(j));
                    map.put("discount", "Help you to save $" + couponDiscounts.get(j)+".00 !");
                    listItem.add(map);
                }
                // Items
                SimpleAdapter listItemAdapter = new SimpleAdapter(ListCouponActivity.this, listItem,
                        R.layout.activity_list_coupon_display,
                        new String[] {"name", "discount"},
                        new int[] { R.id.tv_lcd_cname, R.id.tv_lcd_cdis });
                // Set Adapter
                listView.setAdapter(listItemAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
