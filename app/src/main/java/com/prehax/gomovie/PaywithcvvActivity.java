package com.prehax.gomovie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class PaywithcvvActivity extends AppCompatActivity {
    private static final String TAG ="Make Payment";
    private EditText et_cvv1;
    private String CVV1,CVV2;
    private FirebaseDatabase md;
    private FirebaseAuth m;
    private FirebaseAuth.AuthStateListener AuthListener;
    private DatabaseReference Ref;
    private String uid;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);
        m= FirebaseAuth.getInstance();
        et_cvv1=findViewById(R.id.et_cvv1);
        Button btn_make = findViewById(R.id.btn_make);
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
                try {
                    MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(uid).getValue(MovieGoer.class);
                    CVV2 = (movieGoer.getCVV());
                }catch (NullPointerException e) {
                    Toast.makeText(PaywithcvvActivity.this, "Please save card details to make payment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btn_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CVV1= et_cvv1.getText().toString().trim();
                if(CVV1.equals("")){
                    Toast.makeText(PaywithcvvActivity.this,"cvv is empty",Toast.LENGTH_SHORT).show();
                }
                else if(CVV1.equals(CVV2)){
                    Toast.makeText(PaywithcvvActivity.this,"cvv is matched",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(PaywithcvvActivity.this, "cvv is not matched,re-enter the cvv ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
