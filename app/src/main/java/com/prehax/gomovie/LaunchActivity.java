package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class LaunchActivity extends AppCompatActivity {
    private final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();;
    private final DatabaseReference myRef = mFirebaseDatabase.getReference();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private String pref, userID=null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        System.out.println("草"+user);
        if (user != null) {
            userID = user.getUid();
            System.out.println("我特么拿出ID");
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    System.out.println("进try了么");
                    Timer timer = new Timer();
                    if(user!=null) {
                        MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
                        if (movieGoer != null) {
                            System.out.println("我特么拿偏好" + userID);
                            pref = movieGoer.getPreference();

                            if (pref.equals("Trending")) {
                                System.out.println(pref);
                                System.out.println("1");
                                timer.schedule(timerTask1, 2000);
                            } else if (pref.equals("Upcoming")) {
                                System.out.println(pref);
                                System.out.println("2");
                                timer.schedule(timerTask2, 2000);
                            } else {
                                System.out.println(pref);
                                System.out.println("3");
                                timer.schedule(timerTask, 2000);
                            }
                        }
                        System.out.println(pref);

                    }else {
                        System.out.println(pref);
                        System.out.println("3");
                        timer.schedule(timerTask, 2000);
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            if (userID!=null) {
                startActivity(new Intent(LaunchActivity.this, HomeActivity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
            finish();
        }
    };

    TimerTask timerTask1 = new TimerTask() {
        @Override
        public void run() {

            if (userID!=null) {
                startActivity(new Intent(LaunchActivity.this, Trending_Activity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
            finish();
        }
    };

    TimerTask timerTask2 = new TimerTask() {
        @Override
        public void run() {

            if (userID!=null) {
                startActivity(new Intent(LaunchActivity.this, Upcoming_Activity.class));
            } else {
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
            }
            finish();
        }
    };
}