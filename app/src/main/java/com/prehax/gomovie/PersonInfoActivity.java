package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonInfoActivity extends AppCompatActivity {

    private static final String TAG = "PersonInfoActivity";
    private EditText etFname, etLname, etAddress, etCity, etState, etZip, etTime, etPref;
    // Objects for Database
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        // Bind EditText variable to ID
        etFname = findViewById(R.id.et_pinfo_Fname);
        etLname = findViewById(R.id.et_pinfo_Lname);
        etAddress = findViewById(R.id.et_pinfo_address);
        etCity = findViewById(R.id.et_pinfo_city);
        etState = findViewById(R.id.et_pinfo_state);
        etZip = findViewById(R.id.et_pinfo_zip);
        etTime = findViewById(R.id.et_time);
        etPref = findViewById(R.id.et_pref);
        // Bind Button variable to ID
        Button btn_confirm = findViewById(R.id.btn_pinfo_confirm);
        Button btn_cancel = findViewById(R.id.btn_pinfo_cancel);

        //database
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        //Authentication

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

        // Read from the data so user just need to modify base on old data instead of input again
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
                    etFname.setText(movieGoer.getFname());
                    etLname.setText(movieGoer.getLname());
                    etAddress.setText(movieGoer.getAddress());
                    etCity.setText(movieGoer.getCity());
                    etState.setText(movieGoer.getState());
                    etZip.setText(movieGoer.getZip());
                    etTime.setText(movieGoer.getTime());
                    etPref.setText(movieGoer.getPreference());
                } catch (NullPointerException e) {}
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"OnClick:Save Informations");
                String Fname = etFname.getText().toString().trim();
                String Lname = etLname.getText().toString().trim();
                String Address = etAddress.getText().toString();
                String City = etCity.getText().toString().trim();
                String State = etState.getText().toString().trim();
                String Zip = etZip.getText().toString().trim();
                String Time = etTime.getText().toString().trim();
                String Pref = etPref.getText().toString().trim();
                FirebaseUser user = mAuth.getCurrentUser();
                String userID  = user.getUid();

                myRef.child("MovieGoers").child(userID).child("Fname").setValue(Fname);
                myRef.child("MovieGoers").child(userID).child("Lname").setValue(Lname);
                myRef.child("MovieGoers").child(userID).child("Address").setValue(Address);
                myRef.child("MovieGoers").child(userID).child("City").setValue(City);
                myRef.child("MovieGoers").child(userID).child("State").setValue(State);
                myRef.child("MovieGoers").child(userID).child("Zip").setValue(Zip);
                myRef.child("MovieGoers").child(userID).child("Time").setValue(Time);
                myRef.child("MovieGoers").child(userID).child("Preference").setValue(Pref);


                Toast.makeText(PersonInfoActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                finish();
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
