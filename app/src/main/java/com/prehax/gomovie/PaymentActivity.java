package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {
    private Button btnConfirm, btnCancel, btnMpop, btnApop, btnMcok, btnAcok;
    private TextView tvMovie, tvTheater, tvTime, tvSeat, tvNum, tvTAmount, tvNumOfPop, tvNumOfCok;
    private Spinner spinMethod;
    private int numOfPop=0, numOfCok=0;
    private double tAmount=0;
    private double cokeprice=0,popprice=0;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
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
        tvSeat.setText(bundle.getString("seatCode"));
        tvNum.setText(Integer.toString(bundle.getInt("numOfTic")));
        //database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Snacks snacks = dataSnapshot.child("Theaters").child("1").child("Snacks").getValue(Snacks.class);
                assert snacks != null;
                cokeprice = snacks.getCoke();
                System.out.println(cokeprice);
                System.out.println(popprice);
                popprice = snacks.getPopcorn();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnAcok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfCok=Integer.parseInt(tvNumOfCok.getText().toString());
                if (numOfCok<1000) numOfCok++;
                tvNumOfCok.setText(Integer.toString(numOfCok));
                numOfPop=Integer.parseInt(tvNumOfPop.getText().toString());
                tAmount = (cokeprice * numOfCok) + (popprice * numOfPop);
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
                tAmount = (cokeprice * numOfCok) + (popprice * numOfPop);
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
                tAmount = (cokeprice * numOfCok) + (popprice * numOfPop);
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
                tAmount = (cokeprice * numOfCok) + (popprice * numOfPop);
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
                finish();
            }
        });

    }

}
