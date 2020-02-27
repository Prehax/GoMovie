package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_logIn;
    private TextView tv_signUp, tv_reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logIn = (Button) findViewById(R.id.btn_logIn);
        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Login successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        tv_signUp = findViewById(R.id.tv_signUp);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Now let's signUp!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        tv_reset = findViewById(R.id.tv_reset);
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResetPinActivity.class);
                startActivity(intent);
            }
        });
    }
}
