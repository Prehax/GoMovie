package com.prehax.gomovie.ListViewC;




import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.prehax.gomovie.R;



public class chooseActivity extends AppCompatActivity {
    private ListView mLv1;
//    private static final String[] cinema = {"C1", "C2", "C3", "C4", "C5"};
//    private static final String[] showtime = {"time1", "time2", "time3", "time4", "time5"};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        mLv1 = (ListView) findViewById(R.id.lv_cinema);
        mLv1.setAdapter(new Adapter(chooseActivity.this));

    }
}
