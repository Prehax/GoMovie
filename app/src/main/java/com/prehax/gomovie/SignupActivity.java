package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText et_rem, et_rpass1, et_rpass2;
    private TextView tv_show,tv_show1,tv_show2;
    private Button btn_register;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String Email,password1,password2;


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

        tv_show = findViewById(R.id.tv_show);
        tv_show1 = findViewById(R.id.tv_show1);
        tv_show2 = findViewById(R.id.tv_show2);

        btn_register = findViewById(R.id.btn_register);
        mSharedPreferences = this.getSharedPreferences("PersonalInformation",MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // A temporary function for the button, I'm learning how to connect it to database
                password1 = et_rpass1.getText().toString().trim();
                password2 = et_rpass2.getText().toString().trim();
                Email = et_rem.getText().toString().trim();
                if(password1.equals("")||(password2.equals(""))){//check it is empty or not
                    Toast.makeText(SignupActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
                }else if(password1.equals(password2)){
                    Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    mEditor.putString("Email",Email);
                    mEditor.putString("password",password1);
                    mEditor.apply();
                }else {
                    Toast.makeText(SignupActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(SignupActivity.this, "Next step", Toast.LENGTH_SHORT).show();
                tv_show.setText(mSharedPreferences.getString("Email",""));
                tv_show1.setText(mSharedPreferences.getString("password",""));
            }
        });


    }

}
