package com.prehax.gomovie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AddCouponActivity extends AppCompatActivity {
    private static final String TAG = "AddCouponActivity";
    private EditText etcouponName, etcouponId, etcouponDiscount;
    private String userID;
    private int count,count1,count2;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcoupon);

        etcouponName = findViewById(R.id.et_couponname);
        etcouponId = findViewById(R.id.et_couponid);
        etcouponDiscount = findViewById(R.id.et_coupondiscount);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference();

        Button btn_save = findViewById(R.id.btn_addcoupon1);
        myRef.child("Coupon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> couponName = (List<String>) dataSnapshot.child(userID).child("CouponName").getValue();
                List<String> couponId = (List<String>) dataSnapshot.child(userID).child("CouponId").getValue();
                List<String> couponDiscount = (List<String>) dataSnapshot.child(userID).child("CouponDiscount").getValue();
                 if(couponName!=null) {
                     count = couponName.size();
                     count1 = couponId.size();
                     count2 = couponDiscount.size();
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddCouponActivity",userID);
                Log.d("AddCouponActivity",etcouponName.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponName").child(String.valueOf(count)).setValue(etcouponName.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponId").child(String.valueOf(count1)).setValue(etcouponId.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponDiscount").child(String.valueOf(count2)).setValue(etcouponDiscount.getText().toString());
                //System.out.println("count:"+count+"count1:"+count1+"count2:"+count2);
                Toast.makeText(AddCouponActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
