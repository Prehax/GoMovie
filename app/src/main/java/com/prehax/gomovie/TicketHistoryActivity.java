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
    private FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();;
    private DatabaseReference myRef = mFirebaseDatabase.getReference();
    private FirebaseAuth myAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = myAuth.getCurrentUser();

    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private ArrayList<Theater> theaters = new ArrayList<Theater>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);
        final ListView lvTickets = findViewById(R.id.lv_tickets);
        // Data part

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    long numOfTickets = dataSnapshot.child("MovieGoers").child(user.getUid()).child("Tickets").getChildrenCount();
                    System.out.println(numOfTickets);
                    Ticket ticket = new Ticket();
                    tickets.clear();
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
                        map.put("status", ticket.getStatus());
                        listItem.add(map);
                    }
                    // Items
                    SimpleAdapter listItemAdapter = new SimpleAdapter(TicketHistoryActivity.this, listItem,// 数据源
                            R.layout.layout_tickets,// ListItem的XML实现
                            // 动态数组与Item对应的子项
                            new String[] { "movieName", "theaterName", "seat", "status" },
                            // XML文件里面的3个TextView ID
                            new int[] { R.id.tv_th_movieName, R.id.tv_th_theaterName, R.id.tv_th_seatCode, R.id.tv_th_status });
                    // Set Adapter
                    lvTickets.setAdapter(listItemAdapter);

                    // Get all of theater's information, for rate calculation later
                    long numOfTheaters=dataSnapshot.child("Theaters").getChildrenCount();
                    theaters.clear();
                    for (long i = 0; i<numOfTheaters; i++) {
                        Theater theater = dataSnapshot.child("Theaters").child(Long.toString(i)).getValue(Theater.class);
                        theaters.add(theater);
                        System.out.println("读取数据完成, 读到了以下数据: ");
                        System.out.println(theater.getName() + " " + theater.getAddress());
                    }

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
                int theaterID, rateNum;
                double rate;
                theaterID = ticket.getTheaterID();
                rate = theaters.get(theaterID).getRate();
                rateNum = theaters.get(theaterID).getRateNum();
                // 0: movieName; 1: theatername; 2: showTime; 3: seatCode; 4: status; 5: Amount
                // 0: numOfTic; 1: numOfCok; 2: numOfpop; 3: ticID; 4: theaterID; 5: rateNum
                String[] ticInfo = {ticket.getMovieName(), ticket.getTheaterName(), ticket.getShowTime(), ticket.getSeat(), ticket.getStatus(), ticket.gettAmount()};
                int[] ticNum = {ticket.getNumOfTic(), ticket.getNumOfCok(), ticket.getNumOfPop(), position, ticket.getTheaterID(), rateNum};
                boolean isRated = ticket.isRated();
                Bundle bundle = new Bundle();
                bundle.putStringArray("ticInfo", ticInfo);
                bundle.putIntArray("ticNum", ticNum);
                bundle.putDouble("rate", rate);
                bundle.putBoolean("isRated", isRated);
                intent.putExtras(bundle);
                startActivity(intent);
                //-----------------------------
            }
        });
    }
}
