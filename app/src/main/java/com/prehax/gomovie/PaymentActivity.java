package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
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

public class PaymentActivity extends AppCompatActivity {
    private Button btnConfirm, btnCancel, btnMpop, btnApop, btnMcok, btnAcok;
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvNumOfPop, tvNumOfCok;
    private Spinner spinMethod;
    private int numOfPop=0, numOfCok=0;
    private double tAmount=0;
    private double cokeprice=0,popprice=0;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private String userID;
    private String cardNumber, notiMsg;
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
        //tvMovie=...
        tvTheater.setText(bundle.getString("theaterName"));
        tvTime.setText(bundle.getString("showTimeName"));


//        tvSeat.setText(bundle.getString("seatCode"));
        String position="";
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
            position += "Col:"+col+" Row:"+row;
            position += "; ";
        }
        bundle.putString("position", position);
        bundle.putInt("numOfTic", numOfTic);
        tvSeat.setText(position);
        tvNum.setText(Integer.toString(numOfTic));

        // This String is for notification Msg
        notiMsg="Movie Name: A Movie\nTheater Name: " + bundle.getString("theaterName") + "\nTime: "
        + bundle.getString("showTimeName") + "\nSeats: " + position
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

                MovieGoer movieGoer = dataSnapshot.child("MovieGoers").child(userID).getValue(MovieGoer.class);
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
                System.out.println("不读数据了");
            }
        });
        // This value will depend on the price of the ticket, and number of tickets.
        tAmount = 8*numOfTic;
        tvTAmount.setText(String.format("%.2f", tAmount));
        // Above part will be changed later!!!!!!!!!!!!!
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTicketMsg(v);

                Intent intent = new Intent(PaymentActivity.this, TicketDetailActivity.class);
                bundle.putString("totalAmount", String.format("%.2f", tAmount));
                bundle.putString("numOfCok", Integer.toString(numOfCok));
                bundle.putString("numOfPop", Integer.toString(numOfPop));
                intent.putExtras(bundle);
                startActivity(intent);
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
        Notification notification = new NotificationCompat.Builder(this, "chat")
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
