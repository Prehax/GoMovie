package com.prehax.gomovie.ListViewC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.prehax.gomovie.GridView.GridViewActivity;
import com.prehax.gomovie.R;


public class showActivity extends AppCompatActivity {
    private ListView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        final Bundle bundle = getIntent().getExtras();
        setTitle(bundle.getString("theaterName"));
        show = findViewById(R.id.lv_show);
        final String[] showtime = {"1-5 13:00","1-6 13:00","1-7 13:00","1-8 13:00","1-9 13:00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,showtime);
        show.setAdapter(adapter);
        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(showActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();

                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(showActivity.this, GridViewActivity.class);
                // ShowTime Information
                bundle.putInt("showTimeID", position);
                bundle.putString("showTimeName", showtime[position]);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}
