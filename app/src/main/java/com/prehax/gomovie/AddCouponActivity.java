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
    private DatabaseReference myRef;
    private int numOfCop;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcoupon);

        etcouponName = findViewById(R.id.et_couponname);
        etcouponId = findViewById(R.id.et_couponid);
        etcouponDiscount = findViewById(R.id.et_coupondiscount);

        myRef = FirebaseDatabase.getInstance().getReference();

        Button btn_save = findViewById(R.id.btn_addcoupon1);
        myRef.child("Coupons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 numOfCop = (int)dataSnapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddCouponActivity",etcouponName.getText().toString());
                myRef.child("Coupons").child(String.valueOf(numOfCop)).child("couponName").setValue(etcouponName.getText().toString());
                myRef.child("Coupons").child(String.valueOf(numOfCop)).child("couponId").setValue(etcouponId.getText().toString());
                myRef.child("Coupons").child(String.valueOf(numOfCop)).child("couponDiscount").setValue(etcouponDiscount.getText().toString());
                //System.out.println("count:"+count+"count1:"+count1+"count2:"+count2);
                Toast.makeText(AddCouponActivity.this,"Saved",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
