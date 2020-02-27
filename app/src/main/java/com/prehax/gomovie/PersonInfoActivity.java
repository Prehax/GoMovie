package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PersonInfoActivity extends AppCompatActivity {

    private EditText etFname, etLname, etAddress, etCity, etState, etZip;
    private Button btn_confirm, btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);

        // Bind EditText variable to ID
        etFname = findViewById(R.id.et_pinfo_Fname);
        etLname = findViewById(R.id.et_pinfo_Lname);
        etAddress = findViewById(R.id.et_pinfo_address);
        etCity = findViewById(R.id.et_pinfo_city);
        etState = findViewById(R.id.et_pinfo_state);
        etZip = findViewById(R.id.et_pinfo_zip);
        // Bind Button variable to ID
        btn_confirm = findViewById(R.id.btn_pinfo_confirm);
        btn_cancel = findViewById(R.id.btn_pinfo_cancel);

    }
}
