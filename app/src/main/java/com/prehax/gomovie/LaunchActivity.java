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
}