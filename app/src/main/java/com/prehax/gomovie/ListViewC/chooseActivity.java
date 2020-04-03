package com.prehax.gomovie.ListViewC;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.prehax.gomovie.R;

import java.util.ArrayList;
import java.util.HashMap;


public class chooseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        ListView mLv1 = (ListView) findViewById(R.id.lv_cinema);
        mLv1.setAdapter(new Adapter(chooseActivity.this));
        mLv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(chooseActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(chooseActivity.this, showActivity.class);
                startActivity(intent);
            }
        });
    }



}
