package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminHomeActivity extends AppCompatActivity {
    private static final String TAG = "AdminHomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:Signed_in:" + user.getUid());
                    //Toast.makeText(HomeActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminHomeActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_adminhomemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.status:
                Intent intent = new Intent(AdminHomeActivity.this, Upcoming_Activity.class);
                startActivity(intent);
                return true;

            case R.id.coupons:
                Intent intent1 = new Intent(AdminHomeActivity.this, CouponActivity.class);
                startActivity(intent1);
                return true;

            case R.id.logout:
                mAuth.signOut();
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
