package com.prehax.gomovie;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private Button buttonMovie , buttonCoupons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        buttonMovie = (Button) findViewById(R.id.button_MovieStatus);
        buttonCoupons = (Button) findViewById(R.id.button_Coupons);

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

        buttonMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminUpcoimingActivity.class);
                startActivity(intent);
            }
        });

        buttonCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(AdminHomeActivity.this, CouponActivity.class);
                startActivity(intent1);
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_adminhomemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.addShowTime:
                Intent intent2 = new Intent(AdminHomeActivity.this, AddShowTimeActivity.class);
                startActivity(intent2);
                return true;

            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
