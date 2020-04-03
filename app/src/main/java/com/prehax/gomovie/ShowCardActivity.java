package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowCardActivity extends AppCompatActivity {
    private static final String TAG ="Show Payment Details";
    private Button btn_add,btn_cancel,btn_payment;
    private TextView tv_CardHolderName, tv_CardNumber, tv_ExpDate, tv_CVV;
    private FirebaseDatabase md;
    private FirebaseAuth m;
    private FirebaseAuth.AuthStateListener AuthListener;
    private DatabaseReference Ref;
    private String uid;
    
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        btn_add=findViewById(R.id.btn_add);
        btn_payment=findViewById(R.id.btn_payment);
        btn_cancel=findViewById(R.id.btn_cancel);
        tv_CardHolderName = findViewById(R.id.tv_holdername);
        tv_CardNumber = findViewById(R.id.tv_cardnumber);
        tv_ExpDate = findViewById(R.id.tv_expdate);
        tv_CVV=findViewById(R.id.tv_cvv);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCardActivity.this, CardActivity.class);
                startActivity(intent);
            }
        });
        m = FirebaseAuth.getInstance();
        md = FirebaseDatabase.getInstance();
        Ref = md.getReference();
        FirebaseUser user = m.getCurrentUser();
        uid=user.getUid();
        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = m.getCurrentUser();
                if (user != null){
                    Log.d(TAG,"Signed_in:"+user.getUid());
                } else{
                    Log.d(TAG,"Signed_out");
                }
            }
        };
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(uid).getValue(MovieGoer.class);
                    tv_CardHolderName.setText(movieGoer.getCardHoldername());
                    tv_CardNumber.setText(movieGoer.getCardNumber());
                    tv_ExpDate.setText(movieGoer.getExpDate());
                    tv_CVV.setText(movieGoer.getCVV());
                } catch (NullPointerException e) {
                    Toast.makeText(ShowCardActivity.this, "You don't have info saved yet", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowCardActivity.this, PaywithcvvActivity.class);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        m.addAuthStateListener(AuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(AuthListener != null){
            m.removeAuthStateListener(AuthListener);
        }
    }

}
