package com.prehax.gomovie;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCouponActivity extends AppCompatActivity {
    private static final String TAG = "ListCouponActivity";
    private List<String> couponName;
    private List<String> couponId;
    private List<String> couponDiscount;
    private int count, count1, count2;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcoupon);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        listView = findViewById(R.id.CouponList);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);
        listView.setAdapter(arrayAdapter);
        userID = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference("Coupon");
        myRef.addValueEventListener(new ValueEventListener() {
          @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             couponId = (List<String>) dataSnapshot.child("A8bNPGi45tRXkjlCxjzpDCpZPvG2").child("CouponId").getValue();
         //    couponDiscount = (List<String>) dataSnapshot.child(userID).child("CouponDiscount").getValue();
             arrayAdapter.notifyDataSetChanged();
              for(int i=0;i<couponId.size();i++) {
                  arrayAdapter.add(couponId.get(i));
              }
          }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
