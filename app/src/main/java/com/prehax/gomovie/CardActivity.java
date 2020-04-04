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

public class CardActivity extends AppCompatActivity {
    private static final String TAG = "Add payment Details";
    private EditText et_cname, et_cnum, et_exp, et_cvv;
    private String CardHoldername,CardNumber,ExpDate,CVV;
    private FirebaseDatabase md;
    private FirebaseAuth m;
    private FirebaseAuth.AuthStateListener AuthListener;
    private DatabaseReference Ref;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        et_cname = findViewById(R.id.et_cname);
        et_cnum = findViewById(R.id.et_cnum);
        et_exp = findViewById(R.id.et_exp);
        et_cvv = findViewById(R.id.et_cvv);
        Button btn_save = findViewById(R.id.btn_card);
        Button btn_cancel = findViewById(R.id.btn_cancel);
        m = FirebaseAuth.getInstance();
        md = FirebaseDatabase.getInstance();
        Ref = md.getReference();

        FirebaseUser user = m.getCurrentUser();
        uid = user.getUid();

        AuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = m.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Sign_in: " + user.getUid());
                } else {
                    Log.d(TAG, "Sign_out: ");
                }
            }
        };
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(uid).getValue(MovieGoer.class);
                    et_cname.setText(movieGoer.getCardHoldername());
                    et_cnum.setText(movieGoer.getCardNumber());
                    et_exp.setText(movieGoer.getExpDate());
                    et_cvv.setText(movieGoer.getCVV());
                } catch (NullPointerException e) {}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick:Add Card");
                CardHoldername = et_cname.getText().toString().trim();
                CardNumber = et_cnum.getText().toString().trim();
                ExpDate = et_exp.getText().toString();
                CVV = et_cvv.getText().toString().trim();
                if (CardHoldername.equals("")) {
                    Toast.makeText(CardActivity.this, "CardHolderName is empty", Toast.LENGTH_SHORT).show();
                } else if (CardNumber.equals("")) {
                    Toast.makeText(CardActivity.this, "CardNumber is empty", Toast.LENGTH_SHORT).show();
                } else if (ExpDate.equals("")) {
                    Toast.makeText(CardActivity.this, "expiry date is empty", Toast.LENGTH_SHORT).show();
                } else if (CVV.equals("")) {
                    Toast.makeText(CardActivity.this, "cvv is empty", Toast.LENGTH_SHORT).show();
                } else {
                    Ref.child("MovieGoers").child(uid).child("CardHoldername").setValue(CardHoldername);
                    Ref.child("MovieGoers").child(uid).child("CardNumber").setValue(CardNumber);
                    Ref.child("MovieGoers").child(uid).child("ExpDate").setValue(ExpDate);
                    Ref.child("MovieGoers").child(uid).child("CVV").setValue(CVV);

                    Toast.makeText(CardActivity.this, "Save Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }


                //}
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
            m.addAuthStateListener(AuthListener);
        }

        @Override
        protected void onStop() {
            super.onStop();
            if(AuthListener != null){
                m.removeAuthStateListener(AuthListener);
            }
        }

}