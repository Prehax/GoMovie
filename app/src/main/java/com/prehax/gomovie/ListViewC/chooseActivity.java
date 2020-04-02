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

//        String [] name = {"1","2","3","4","5","6","1213" };
//        String [] address = {"1","2","3","4","5","6","1213" };
//        String [] rate = {"1","2","3","4","5","6","12.13" };
//        for(int i =0;i<listItem.size();i++){
//            HashMap<String,Object> map = new HashMap<String, Object>();
//            map.put("Name",name[i]);
//            map.put("Address",address[i]);
//            map.put("Rate",rate[i]);
//            listItem.add(map);
//        }
//
//        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,R.layout.layout_list_choose,new String[] {"Name","Address","Rate"},newint[] {R.id.Lcname,R.id.Lcaddress,R.id.Lcshowtime});
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
