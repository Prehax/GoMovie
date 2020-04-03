

package com.prehax.gomovie;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


public class SeatsActivity extends AppCompatActivity {
    private SeatTable seatTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seats);

        seatTable=findViewById(R.id.seats);
        seatTable.setData(10,15);
    }

}
