package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {
    private Button btnConfirm, btnCancel;
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount;
    private Spinner spinMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

    }

}
