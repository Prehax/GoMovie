package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.quickstart.database.java.models.Post;

public class ShowInfoActivity extends AppCompatActivity {
    private static final String TAG = "PersonInfoActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    private Button btnModify, btnBack;
    private TextView tvFname, tvLname, tvAddress, tvCity, tvState, tvZip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        btnModify = findViewById(R.id.btn_sinfo_modify);
        btnBack = findViewById(R.id.btn_sinfo_back);

        tvFname = findViewById(R.id.tv_sinfo_Fname);
        tvLname = findViewById(R.id.tv_sinfo_Lname);
        tvAddress = findViewById(R.id.tv_sinfo_address);
        tvCity = findViewById(R.id.tv_sinfo_city);
        tvState = findViewById(R.id.tv_sinfo_state);
        tvZip = findViewById(R.id.tv_sinfo_zip);

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
                finish();
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
                MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
                tvFname.setText(movieGoer.getFname());
                tvLname.setText(movieGoer.getLname());
                tvAddress.setText(movieGoer.getAddress());
                tvCity.setText(movieGoer.getCity());
                tvState.setText(movieGoer.getState());
                tvZip.setText(movieGoer.getZip());
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
