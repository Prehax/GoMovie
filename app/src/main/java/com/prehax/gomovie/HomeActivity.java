package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    /*tested....*/
    private static final String TAG = "HomeActivity";
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference mDatabase;// ...
    private Button btnPayment, btnSeats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG,"onAuthStateChanged:Signed_in:"+user.getUid());
                    //Toast.makeText(HomeActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Button btn_personInfo = findViewById(R.id.btn_personInfo);
        btn_personInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShowInfoActivity.class);
                startActivity(intent);
            }
        });

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btn_card = findViewById(R.id.btn_card);
        btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ShowCardActivity.class);
                startActivity(intent);
            }
        });

        Button btn_jump = findViewById(R.id.btn_jump);
        btn_jump.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(HomeActivity.this, TheaterActivity.class);
                startActivity(intent);
            }
        });
        Button btn_sign = findViewById(R.id.btn_sign);
        btn_sign.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                    myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long i = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Sign").getValue();
                        boolean a;
                        a = Check(i);
                        if(a){
                            Toast.makeText(HomeActivity.this, "Already signed today", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(HomeActivity.this, "sign success", Toast.LENGTH_SHORT).show();
                            Calendar calendar = Calendar.getInstance();
                            int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
                            myRef.child("MovieGoers").child(userID).child("Sign").setValue(day);
                            myRef.child("MovieGoers").child(userID).child("Signeddays").setValue(+1);
                            Long days = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Sign").getValue();
//                            if(days>=7){
//                                //coupon+1
//                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

    }

    private boolean Check(Long i){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
        System.out.println(day);
        if(i == day){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}