package com.prehax.gomovie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class AddCouponActivity extends AppCompatActivity {
    private static final String TAG = "AddCouponActivity";
    private EditText etcouponName, etcouponId, etcouponDiscount;
    private String userID;
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


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddCouponActivity",userID);
                Log.d("AddCouponActivity",etcouponName.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponName").setValue(etcouponName.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponId").setValue(etcouponId.getText().toString());
                myRef.child("Coupon").child(userID).child("CouponDiscount").setValue(etcouponDiscount.getText().toString());
                
            }
        });


       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    Coupon coupon = dataSnapshot.child("Coupon").child(userID).getValue(Coupon.class);
                    etcouponName.setText(coupon.getcouponName());
                    etcouponId.setText(coupon.getcouponId());
                    etcouponDiscount.setText((int) coupon.getcouponDiscount());
                }
                catch (NullPointerException e) {
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });*/


    }
}
