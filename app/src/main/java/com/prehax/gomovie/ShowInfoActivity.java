package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowInfoActivity extends AppCompatActivity {

    private Button btnModify, btnBack;
    private TextView tvFname, tvLname, tvAddress, tvCity, tvState, tvZip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        btnModify = findViewById(R.id.btn_sinfo_modify);
        btnBack = findViewById(R.id.btn_sinfo_back);

        tvFname = findViewById(R.id.tv_sinfo_Fname);
        tvLname = findViewById(R.id.tv_sinfo_Lname);
        tvAddress = findViewById(R.id.tv_sinfo_address);
        tvCity = findViewById(R.id.tv_sinfo_city);
        tvState = findViewById(R.id.tv_sinfo_state);
        tvZip = findViewById(R.id.tv_sinfo_zip);

        btnModify.setOnClickListener(new View.OnClickListener() {
            // Jump to Editable PersonalInfoActivity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ShowInfoActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
