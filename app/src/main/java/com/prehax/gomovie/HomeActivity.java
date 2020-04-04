package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    //MyAdapter adapter;
    //Bitmap images;
    //private RecyclerView.LayoutManager layoutManager;
    List<HashMap<String, String>> items = new ArrayList<>();
    private ListView lv;
    int[] posterImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,
            R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10};
    String[] rating = {"7.3", "7.2", "6.6", "7.4", "7.1", "8.0", "6.8", "6.7", "8.5", "7.8"};
    String[] genre = {"Action", "Drama", "Action", "Action", "Horror", "Adventure", "Action", "Drama", "Comedy", "Action"};
    String[] date = {"2020-02-20", "2019-11-08", "2020-01-08", "2020-02-12", "2020-02-26", "2020-02-29", "2020-03-11", "2020-02-19", "2019-05-30", "2019-12-16"};
    String[] title = {"Bloodshot", "The Platform", "Underwater", "Sonic the Hedgehog", "The Invisible Man", "Onward", "The Hunt", "The Call of the Wild",
            "기생충", "The Gentlemen"};

    //  ArrayList<Bitmap> titles = new ArrayList<Bitmap>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        items.clear();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> hm = new HashMap<>();
            hm.put("title", title[i]);
            hm.put("rating", rating[i]);
            hm.put("genre", genre[i]);
            hm.put("date", date[i]);
            hm.put("posterImages", Integer.toString(posterImages[i]));
            items.add(hm);
        }

        String[] from = {"title", "posterImages", "rating", "genre", "date"};
        int[] to = {R.id.titleText, R.id.toggle_btn, R.id.ratingText, R.id.genreText, R.id.dateText};
        SimpleAdapter adapter = new SimpleAdapter(HomeActivity.this, items, R.layout.activity_moviecell, from, to);
        lv = findViewById(R.id.list);
        lv.setAdapter(adapter);

        /*tested....*/
//    private static final String TAG = "HomeActivity";
//    private FirebaseAuth mAuth;
//    private FirebaseAuth.AuthStateListener mAuthListener;
//    private DatabaseReference mDatabase;// ...
//    private Button btnPayment, btnSeats;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:Signed_in:" + user.getUid());
                    //Toast.makeText(HomeActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
//        Button btn_personInfo = findViewById(R.id.btn_personInfo);
//        btn_personInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ShowInfoActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btn_logout = findViewById(R.id.btn_logout);
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mAuth.signOut();
//                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        Button btn_card = findViewById(R.id.btn_card);
//        btn_card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, ShowCardActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button btn_jump = findViewById(R.id.btn_jump);
//        btn_jump.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v) {
//                Intent intent;
//                intent = new Intent(HomeActivity.this, TheaterActivity.class);
//                startActivity(intent);
//            }
//        });


        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_homemenu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.trending:
                    Intent intent = new Intent(HomeActivity.this, Trending_Activity.class);
                    startActivity(intent);
                    return true;
                case R.id.upcoming:
                    Intent intent1 = new Intent(HomeActivity.this, Upcoming_Activity.class);
                    startActivity(intent1);
                    return true;
                case R.id.ShowInfo:
                    Intent intent2 = new Intent(HomeActivity.this, ShowInfoActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.Showcard:
                    Intent intent3 = new Intent(HomeActivity.this, ShowCardActivity.class);
                    startActivity(intent3);
                    return  true;
                case R.id.Theater:
                    intent = new Intent(HomeActivity.this, TheaterActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.logout:
                    mAuth.signOut();
                    finish();
                default:
                    return super.onOptionsItemSelected(item);
            }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}