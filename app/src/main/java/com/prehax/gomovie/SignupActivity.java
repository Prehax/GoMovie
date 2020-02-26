package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText et_rem, et_rpass1, et_rpass2;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // E-mail want to register
        et_rem = findViewById(R.id.et_remail);

        // Password to be set
        et_rpass1 = findViewById(R.id.et_rpass1);

        // Password must be same as above
        et_rpass2 = findViewById(R.id.et_rpass2);

        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A temporary function for the button, I'm learning how to connect it to database
                Toast.makeText(SignupActivity.this, "Next step", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
