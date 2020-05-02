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

import java.util.Calendar;
//import com.google.firebase.quickstart.database.java.models.Post;

public class ShowInfoActivity extends AppCompatActivity {
    private static final String TAG = "PersonInfoActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private Button btnModify, btnBack;
    private TextView tvFname, tvLname, tvAddress, tvCity, tvState, tvZip, tvTime, tvPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        // Find ID
        btnModify = findViewById(R.id.btn_sinfo_modify);
        btnBack = findViewById(R.id.btn_sinfo_back);
        tvFname = findViewById(R.id.tv_sinfo_Fname);
        tvLname = findViewById(R.id.tv_sinfo_Lname);
        tvAddress = findViewById(R.id.tv_sinfo_address);
        tvCity = findViewById(R.id.tv_sinfo_city);
        tvState = findViewById(R.id.tv_sinfo_state);
        tvZip = findViewById(R.id.tv_sinfo_zip);
        tvTime = findViewById(R.id.tv_sinfo_Time);
        tvPref = findViewById(R.id.tv_pref);
        btnModify.setOnClickListener(new View.OnClickListener() {
            // Jump to Editable PersonalInfoActivity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ShowInfoActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef = mFirebaseDatabase.getReference();

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long i = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Sign").getValue();
                        boolean a;
                        a = Check(i);
                        if (a) {
                            Toast.makeText(ShowInfoActivity.this, "Already signed today", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ShowInfoActivity.this, "Sign success", Toast.LENGTH_SHORT).show();
                            Calendar calendar = Calendar.getInstance();
                            int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
                            try {
                                myRef.child("MovieGoers").child(userID).child("Sign").setValue(day);
                                System.out.println("到这里坏没坏1"+day);

                                Long days = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Signeddays").getValue();
                                days = days+1;
                                myRef.child("MovieGoers").child(userID).child("Signeddays").setValue(days);
                                System.out.println("到这里坏没坏2");
                                System.out.println("到这里坏没坏3"+days);
                                if(days+1>=7){
//添加一个coupon
                                }
                            } catch (NullPointerException e) {
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                    private boolean Check(Long i) {
                        Calendar calendar = Calendar.getInstance();
                        System.out.println("我是个日子！！！！！"+i);
                        int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
                        System.out.println("我也是个日子！！！！！"+day);
                        if (i == day) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
//                finish();
            }
        });
        // Database stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID=user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG,"onAuthStateChanged:Signed_in:"+user.getUid());
                    //Toast.makeText(PersonInfoActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else{
                    Log.d(TAG,"onAuthStateChanged:Signed_out");
                    //Toast.makeText(PersonInfoActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
                    tvFname.setText(movieGoer.getFname());
                    tvLname.setText(movieGoer.getLname());
                    tvAddress.setText(movieGoer.getAddress());
                    tvCity.setText(movieGoer.getCity());
                    tvState.setText(movieGoer.getState());
                    tvZip.setText(movieGoer.getZip());
                    tvTime.setText(movieGoer.getTime());
                    tvPref.setText(movieGoer.getPreference());
                } catch (NullPointerException e) {
                    Toast.makeText(ShowInfoActivity.this, "You don't have info saved yet", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
