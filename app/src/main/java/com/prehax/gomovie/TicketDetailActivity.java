package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvStatus, tvNumOfPop, tvNumOfCok;
    private Button btnConfirm, btnRefund, btnRate;

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
        btnRefund = findViewById(R.id.btn_td_refund);
        btnRate = findViewById(R.id.btn_td_rate);
        // Get Bundle
        Bundle bundle = getIntent().getExtras();
        // 0: movieName; 1: theatername; 2: showTime; 3: seatCode; 4: status; 5: Amount
        String[] ticInfo = bundle.getStringArray("ticInfo");
        // 0: numOfTic; 1: numOfCok; 2: numOfpop 3: ticID
        final int[] ticNum = bundle.getIntArray("ticNum");
        //tvMovie.setText(...);
        /*
        System.out.println("从上个界面获取的两个数组中的数据为: ");
        for (int i=0; i<6; i++) {
            System.out.println(ticInfo[i]);
        }
        for (int i=0; i<3; i++) {
            System.out.println(ticNum[i]);
        }
         */
        tvMovie.setText(ticInfo[0]);
        tvTheater.setText(ticInfo[1]);
        tvTime.setText(ticInfo[2]);
        tvSeat.setText(ticInfo[3]);
        tvNum.setText(Integer.toString(ticNum[0]));
        tvNumOfCok.setText(Integer.toString(ticNum[1]));
        tvNumOfPop.setText(Integer.toString(ticNum[2]));
        tvStatus.setText(ticInfo[4]);
        tvTAmount.setText(ticInfo[5]);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TicketDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();;
                final DatabaseReference myRef = mFirebaseDatabase.getReference();
                final FirebaseAuth myAuth = FirebaseAuth.getInstance();
                final FirebaseUser user = myAuth.getCurrentUser();
                String userID = user.getUid();

                myRef.child("MovieGoers").child(userID).child("Tickets").child(Integer.toString(ticNum[3])).child("status").setValue("REFUNDED");
                finish();
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
