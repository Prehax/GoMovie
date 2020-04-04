package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvStatus, tvNumOfPop, tvNumOfCok;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        // Find ID for TextView
        tvMovie = findViewById(R.id.tv_td_movieName);
        tvTheater = findViewById(R.id.tv_td_theaterName);
        tvTime = findViewById(R.id.tv_td_time);
        tvSeat = findViewById(R.id.tv_td_seatCode);
        tvNum = findViewById(R.id.tv_td_numOfTik);
        tvNumOfCok = findViewById(R.id.tv_td_numOfCok);
        tvNumOfPop = findViewById(R.id.tv_td_numOfPop);
        tvStatus = findViewById(R.id.tv_td_status);
        tvTAmount = findViewById(R.id.tv_td_tAmount);
        // Find ID for Button
        btnConfirm = findViewById(R.id.btn_td_confirm);
        // Get Bundle
        Bundle bundle = getIntent().getExtras();
        //tvMovie.setText(...);
        tvTheater.setText(bundle.getString("theaterName"));
        tvTime.setText(bundle.getString("showTimeName"));
        tvSeat.setText(bundle.getString("seatCode"));
        tvNum.setText(Integer.toString(bundle.getInt("numOfTic")));
        tvNumOfCok.setText(bundle.getString("numOfCok"));
        tvNumOfPop.setText(bundle.getString("numOfPop"));
        //tvStatus.setText("PAID");
        tvTAmount.setText(bundle.getString("totalAmount"));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
