package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TicketHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);
        final ListView lvTickets = findViewById(R.id.lv_tickets);
        final FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();;
        final DatabaseReference myRef = mFirebaseDatabase.getReference();
        final FirebaseAuth myAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = myAuth.getCurrentUser();
        // Data part
        final ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    long numOfTickets = dataSnapshot.child("MovieGoers").child(user.getUid()).child("Tickets").getChildrenCount();
                    System.out.println(numOfTickets);
                    Ticket ticket = new Ticket();
                    for (long i = 0; i<numOfTickets; i++) {
                        ticket = dataSnapshot.child("MovieGoers").child(user.getUid()).child("Tickets").child(Long.toString(i)).getValue(Ticket.class);
                        // Put each one ticket from database into arraylist
                        tickets.add(ticket);
                    }
                    // Generate array
                    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                    for (int i = 0; i < tickets.size(); i++) {
                        ticket = tickets.get(i);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("movieName", ticket.getMovieName());
                        map.put("theaterName", ticket.getTheaterName());
                        map.put("seat", ticket.getSeat());
                        listItem.add(map);
                    }
                    // Items
                    SimpleAdapter listItemAdapter = new SimpleAdapter(TicketHistoryActivity.this, listItem,// 数据源
                            R.layout.layout_tickets,// ListItem的XML实现
                            // 动态数组与Item对应的子项
                            new String[] { "movieName", "theaterName", "seat" },
                            // XML文件里面的3个TextView ID
                            new int[] { R.id.tv_th_movieName, R.id.tv_th_theaterName, R.id.tv_th_seatCode });
                    // Set Adapter
                    lvTickets.setAdapter(listItemAdapter);
                } catch (NullPointerException e) {
                    System.out.println("读取未完成, 遭遇空指针错误");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("读取失败");
            }
        });

        // OnClick
        lvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // setTitle("OnClick No." + (position+1) + " item");
                // 传关键信息到下一个界面并且启动
                Intent intent = new Intent(TicketHistoryActivity.this, TicketDetailActivity.class);
                Ticket ticket = tickets.get(position);
                String ticInfo[] = {ticket.getMovieName(), ticket.getTheaterName(), ticket.getShowTime(), ticket.getSeat(), ticket.getStatus(), ticket.gettAmount()};
                int ticNum[] = {ticket.getNumOfTic(), ticket.getNumOfCok(), ticket.getNumOfPop()};
                Bundle bundle = new Bundle();
                bundle.putStringArray("ticInfo", ticInfo);
                bundle.putIntArray("ticNum", ticNum);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}
