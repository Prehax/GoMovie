package com.prehax.gomovie;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCouponActivity extends AppCompatActivity {
    private static final String TAG = "Available Coupons";
    private List<String> couponName;
    private List<String> couponId;
    private List<String> couponDiscount;
    private int count, count1, count2;
    private String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listcoupon);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final ListView listView = findViewById(R.id.lst_vw);
        userID = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Coupon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               couponName = (List<String>) dataSnapshot.child(userID).child("CouponName").getValue();
                couponId = (List<String>) dataSnapshot.child(userID).child("CouponId").getValue();
                couponDiscount = (List<String>) dataSnapshot.child(userID).child("CouponDiscount").getValue();
                if (couponName != null) {
                    count = couponName.size();
                    count1 = couponId.size();
                    count2 = couponDiscount.size();
                }
               /* try{
                    long n = dataSnapshot.child(userID).child("CouponName").getChildrenCount();
                    for(long i =0;i<n;i++){
                        Coupon coupon = dataSnapshot.child("Coupon").child(Long.toString(i)).getValue(Coupon.class);
                        couponName.add(coupon.getcouponName());
                        couponId.add(coupon.getcouponId());
                        couponDiscount.add(coupon.getcouponDiscount());

                    }
                    ArrayList<HashMap<String,Object>> list = new ArrayList<>();
                    for (int i = 0; i < couponName.size(); i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("coupon name", couponName.get(i));
                        map.put("coupon code", couponId.get(i));
                        map.put("Discount", couponDiscount.get(i));
                        list.add(map);
                    }

                    SimpleAdapter listItemAdapter = new SimpleAdapter(ListCouponActivity.this, list, R.layout.activity_list_coupon_display,
                            new String[] { "coupon name", "coupon code", "Discount" },
                            new int[] { R.id.lst_couponname, R.id.lst_couponid, R.id.lst_coupondiscount });

                    listView.setAdapter(listItemAdapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
