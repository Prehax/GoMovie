package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class SignupActivity extends AppCompatActivity {

    private EditText et_rem, et_rpass1, et_rpass2;
    private String Email,password1,password2;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Firebase Authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        // E-mail want to register
        et_rem = findViewById(R.id.et_remail);

        // Password to be set
        et_rpass1 = findViewById(R.id.et_rpass1);

        // Password must be same as above
        et_rpass2 = findViewById(R.id.et_rpass2);
        Button btn_register = findViewById(R.id.btn_register);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                password1 = et_rpass1.getText().toString().trim();
                password2 = et_rpass2.getText().toString().trim();
                Email = et_rem.getText().toString().trim();
                if(password1.equals("")||(password2.equals(""))){//check it is empty or not
                    Toast.makeText(SignupActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
                }else if(password1.equals(password2)){

                    //save data
                    mFirebaseAuth.createUserWithEmailAndPassword(Email,password1).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Not Success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

                }else if(Email.isEmpty()){
                    Toast.makeText(SignupActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
