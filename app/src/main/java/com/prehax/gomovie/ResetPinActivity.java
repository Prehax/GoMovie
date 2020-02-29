package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPinActivity extends AppCompatActivity {

    private Button btnConfirm, btnCancel;
    private EditText etResetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);

        btnConfirm = findViewById(R.id.btn_resetConfirm);
        btnCancel = findViewById(R.id.btn_resetCancel);

        etResetEmail=findViewById(R.id.et_resetEmail);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPasswordReset();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sendPasswordReset() {
        // [START send_password_reset]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //String emailAddress = "prehax@outlook.com";
        String emailAddress = etResetEmail.getText().toString().trim();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("MovieGoer: ", "Email sent.");
                        }
                    }
                });
        // [END send_password_reset]
    }

}
