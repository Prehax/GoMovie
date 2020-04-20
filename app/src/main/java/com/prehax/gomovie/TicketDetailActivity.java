package com.prehax.gomovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketDetailActivity extends AppCompatActivity {
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvStatus, tvNumOfPop, tvNumOfCok;
    private Button btnConfirm, btnRefund, btnRate;
    private final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();;
    private final DatabaseReference myRef = mFirebaseDatabase.getReference();
    private final FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private final FirebaseUser user = myAuth.getCurrentUser();
    private String userID = user.getUid();

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
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Integer.toString(ticNum[3])).child("status").setValue("REFUNDED");
                finish();
            }
        });
    }

    public void rateDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rating");
        final View view = getLayoutInflater().inflate(R.layout.rating_layout,null);
        builder.setView(view);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RatingBar rbStars = view.findViewById(R.id.rb_stars);
                float numOfStars = rbStars.getRating();
                // 读取当前电影院评价人数n与当前评分a, n*a+numOfStars / n+1, n++;
                DataSnapshot dataSnapshot;
                //myRef.child("Theaters").child("1").child("rateNum").
                Toast.makeText(TicketDetailActivity.this, Float.toString(numOfStars), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
