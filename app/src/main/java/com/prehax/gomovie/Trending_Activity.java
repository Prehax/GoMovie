package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trending_Activity extends AppCompatActivity {


        private FirebaseAuth mAuth;
        private FirebaseAuth.AuthStateListener mAuthListener;

        List<HashMap<String, String>> items1 = new ArrayList<>();
         ListView lvTrending;
        int[] posterImages = {R.drawable.image9,R.drawable.image5, R.drawable.image2, R.drawable.image3,R.drawable.image1, R.drawable.image4, R.drawable.image6,
                R.drawable.image7, R.drawable.image8,R.drawable.image10};
        String[] rating = {"7.3", "7.2", "6.6", "7.4", "7.1", "8.0", "6.8", "6.7", "8.5", "7.8"};
        String[] genre = {"Comedy","Action", "Drama", "Action", "Action", "Horror", "Adventure", "Action", "Drama", "Action"};
        String[] date = {"2019-05-30","2020-02-20", "2019-11-08", "2020-01-08", "2020-02-12", "2020-02-26", "2020-02-29", "2020-03-11", "2020-02-19", "2019-12-16"};
        String[] title = {"기생충", "The Platform","Onward","Underwater", "Bloodshot","Sonic the Hedgehog", "The Invisible Man", "The Hunt", "The Call of the Wild"
                ,"The Gentlemen"};

       @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trending_);
            items1.clear();
            for (int i = 0; i < 10; i++) {
                HashMap<String, String> hm = new HashMap<>();
                hm.put("title", title[i]);
                hm.put("rating", rating[i]);
                hm.put("genre", genre[i]);
                hm.put("date", date[i]);
                hm.put("posterImages", Integer.toString(posterImages[i]));
                items1.add(hm);
            }

            String[] from = {"title", "posterImages", "rating", "genre", "date"};
            int[] to = {R.id.titleText, R.id.toggle_btn, R.id.ratingText, R.id.genreText, R.id.dateText};
            SimpleAdapter adapter = new SimpleAdapter(Trending_Activity.this, items1, R.layout.activity_moviecell, from, to);
            lvTrending = findViewById(R.id.listTrending);
            lvTrending.setAdapter(adapter);
        }
    }

