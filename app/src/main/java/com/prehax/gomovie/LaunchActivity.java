package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        mAuth = FirebaseAuth.getInstance();
        Timer timer = new Timer();
        timer.schedule(timerTask, 2000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            FirebaseUser user = mAuth.getCurrentUser();
            if (user!=null) {
                startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
            finish();
        }
    };
    /*
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    */
}


/*
        mAuthListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Timer timer = new Timer();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null){
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
                            finish();
                        }
                    }, 2000);
                    Toast.makeText(LaunchActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else{
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 2000);
                    Toast.makeText(LaunchActivity.this, "用户尚未登录.", Toast.LENGTH_SHORT).show();
                }
            }
        };

         */

/*
    mAuthListener = new FirebaseAuth.AuthStateListener() {

        @Override
        public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
            Toast.makeText(LaunchActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            Toast.makeText(LaunchActivity.this, "用户尚未登录.", Toast.LENGTH_SHORT).show();
        }
    }
    };
     */