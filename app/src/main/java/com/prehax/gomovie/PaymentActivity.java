package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    private Button btnConfirm, btnCancel, btnMpop, btnApop, btnMcok, btnAcok;
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvNumOfPop, tvNumOfCok;
    private Spinner spinMethod;
    private int numOfPop=0, numOfCok=0, rateNum;
    private double tAmount=0, rate;
    private double cokeprice=0,popprice=0;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private String cardNumber, notiMsg, seatPosition="", movieName;
    private long numOfRecord=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        // Button Find ID
        btnConfirm=findViewById(R.id.btn_pay_Confirm);
        btnCancel=findViewById(R.id.btn_pay_Cancel);
        btnAcok=findViewById(R.id.btn_pay_acok);
        btnApop=findViewById(R.id.btn_pay_apop);
        btnMcok=findViewById(R.id.btn_pay_mcok);
        btnMpop=findViewById(R.id.btn_pay_mpop);
        // Text Find ID
        tvMovie = findViewById(R.id.tv_pay_movieName);
        tvTheater = findViewById(R.id.tv_pay_theaterName);
        tvTime = findViewById(R.id.tv_pay_time);
        tvSeat = findViewById(R.id.tv_pay_seatCode);
        tvNum = findViewById(R.id.tv_pay_numOfTik);
        tvTAmount = findViewById(R.id.tv_pay_tAmount);
        // Edit Find ID
        tvNumOfCok = findViewById(R.id.tv_pay_numCok);
        tvNumOfPop = findViewById(R.id.tv_pay_numPop);
        // Spinner Find ID
        spinMethod = findViewById(R.id.spin_pay_method);
        // Ticket Information
        final Bundle bundle = getIntent().getExtras();
        movieName = bundle.getString("movieName");
        tvMovie.setText(movieName);
        tvTheater.setText(bundle.getString("theaterName"));
        tvTime.setText(bundle.getString("showTimeName"));


//        tvSeat.setText(bundle.getString("seatCode"));

        ArrayList<Integer> record = bundle.getIntegerArrayList("seatCode");
        final int numOfTic = record.size();
        int col, row;
        for (int i = 0; i<numOfTic; i++) {
            if (record.get(i)>10) {
                col = record.get(i)%10 + 1;
                row = record.get(i)/10 + 1;
            } else {
                col = record.get(i) + 1;
                row = 1;
            }
            seatPosition += "Col:"+col+" Row:"+row+"; ";
        }

        // show the seat information in the software
        tvSeat.setText(seatPosition);
        tvNum.setText(Integer.toString(numOfTic));

        // This String is for notification Msg
        notiMsg="Movie Name: "+ movieName + "\nTheater Name: " + bundle.getString("theaterName") + "\nTime: "
        + bundle.getString("showTimeName") + "\nSeats: " + seatPosition
                + "\nGet in the app to see more information!";

        //database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID=user.getUid();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Snacks snacks = dataSnapshot.child("Theaters").child(Integer.toString(bundle.getInt("theaterID"))).child("Snacks").getValue(Snacks.class);
                assert snacks != null;
                cokeprice = snacks.getCoke();
                popprice = snacks.getPopcorn();
                // Read data from theater
                Theater theater = dataSnapshot.child("Theaters").child(Integer.toString(bundle.getInt("theaterID"))).getValue(Theater.class);
                assert theater != null;
                rate = theater.getRate();
                rateNum = theater.getRateNum();

                MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
                try {
                    numOfRecord = dataSnapshot.child("MovieGoers").child(userID).child("Tickets").getChildrenCount();
                } catch (NullPointerException e) {
                    numOfRecord = 0;
                }
                ArrayList<String> mList = new ArrayList<>();
                try {
                    cardNumber = movieGoer.getCardNumber();
                    if (cardNumber.length() >= 4) { cardNumber = cardNumber.substring(cardNumber.length() - 4); }
                    mList.add("Card ending with " + cardNumber);
                } catch (NullPointerException e) {
                    Toast.makeText(PaymentActivity.this, "You do not have a card stored", Toast.LENGTH_SHORT).show();
                }
                mList.add("----Use Another Card----");
                ArrayAdapter arrayAdapter = new ArrayAdapter(PaymentActivity.this, R.layout.item_select, mList);
                spinMethod.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Fail to read data");
            }
        });
        // This value will depend on the price of the ticket, and number of tickets.
        tAmount = 8*numOfTic;
        tvTAmount.setText(String.format("%.2f", tAmount));
        // Above part will be changed later!!!!!!!!!!!!!
        // Following are + and - buttons
        btnAcok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfCok=Integer.parseInt(tvNumOfCok.getText().toString());
                if (numOfCok<1000) numOfCok++;
                tvNumOfCok.setText(Integer.toString(numOfCok));
                numOfPop=Integer.parseInt(tvNumOfPop.getText().toString());
                tAmount = 8*numOfTic+popprice*numOfPop+cokeprice*numOfCok;
                tvTAmount.setText(String.format("%.2f", tAmount));
            }
        });
        btnApop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfPop=Integer.parseInt(tvNumOfPop.getText().toString());
                if (numOfPop<1000) numOfPop++;
                tvNumOfPop.setText(Integer.toString(numOfPop));
                numOfCok=Integer.parseInt(tvNumOfCok.getText().toString());
                tAmount = 8*numOfTic+popprice*numOfPop+cokeprice*numOfCok;
                tvTAmount.setText(String.format("%.2f", tAmount));
            }
        });
        btnMcok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfCok=Integer.parseInt(tvNumOfCok.getText().toString());
                if (numOfCok>0) numOfCok--;
                tvNumOfCok.setText(Integer.toString(numOfCok));
                numOfPop=Integer.parseInt(tvNumOfPop.getText().toString());
                tAmount = 8*numOfTic+popprice*numOfPop+cokeprice*numOfCok;
                tvTAmount.setText(String.format("%.2f", tAmount));
            }
        });
        btnMpop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfPop=Integer.parseInt(tvNumOfPop.getText().toString());
                if (numOfPop>0) numOfPop--;
                tvNumOfPop.setText(Integer.toString(numOfPop));
                numOfCok=Integer.parseInt(tvNumOfCok.getText().toString());
                tAmount = 8*numOfTic+popprice*numOfPop+cokeprice*numOfCok;
                tvTAmount.setText(String.format("%.2f", tAmount));
            }
        });

        // Cancel button
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Confirm button
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send a message to notification bar
                sendTicketMsg(v);

                Intent intent = new Intent(PaymentActivity.this, TicketDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                // 0: movieName; 1: theaterName; 2: showTime; 3: seatCode; 4: status; 5: Amount
                // 0: numOfTic; 1: numOfCok; 2: numOfPop; 3: ticID, 4: theaterID; 5: rateNum
                String[] ticInfo = {bundle.getString("movieName"), bundle.getString("theaterName"), bundle.getString("showTimeName"), seatPosition, "PAID", String.format("%.2f", tAmount)};
                int[] ticNum = {numOfTic, numOfCok, numOfPop, (int)numOfRecord, bundle.getInt("theaterID"), rateNum};
                // 将ticket信息写入数据库里面
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("movieName").setValue(ticInfo[0]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("theaterName").setValue(ticInfo[1]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("showTime").setValue(ticInfo[2]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("seat").setValue(ticInfo[3]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("status").setValue(ticInfo[4]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("tAmount").setValue(ticInfo[5]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("numOfTic").setValue(ticNum[0]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("numOfCok").setValue(ticNum[1]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("numOfPop").setValue(ticNum[2]);
                myRef.child("MovieGoers").child(userID).child("Tickets").child(Long.toString(numOfRecord)).child("theaterID").setValue(ticNum[4]);

                // put necessary data in bundle and pass it into next page
                bundle.clear();
                bundle.putStringArray("ticInfo", ticInfo);
                bundle.putIntArray("ticNum", ticNum);
                bundle.putDouble("rate", rate);
                intent.putExtras(bundle);
                startActivity(intent);
                // If confirm, terminate all activity related to this ticket.
                finish();
            }
        });


        spinMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    // Pop a window, allow user to enter Card Details

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "ticket";
            String channelName = "Ticket Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    public void sendTicketMsg(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "ticket")
                .setContentTitle("You booked a ticket!")
                .setContentText(notiMsg)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon_main)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon_main))
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notiMsg))
                .build();
        manager.notify(1, notification);
    }

}
