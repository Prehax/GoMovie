package com.prehax.gomovie.GridView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.prehax.gomovie.R;

public class GridViewActivity extends AppCompatActivity {
    private GridView mGv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        mGv=findViewById(R.id.gv);
        mGv.setAdapter(new MyGridViewAdapter(GridViewActivity.this));
        mGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyGridViewAdapter myGridViewAdapter = new MyGridViewAdapter();
                        myGridViewAdapter.setSeclection(position);
                        myGridViewAdapter.notifyDataSetChanged();
            }
        });
    }
}
