package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.Adapaters.RecyclerViewAdapter;
import com.prehax.gomovie.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity {

    private static final String TAG = "WatchListActivity";
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Movie> listMovie;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        requestQueue = Volley.newRequestQueue(this);
        listMovie = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewWatch);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        //Authentication

        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        userID=user.getUid();
        Log.d("WatchListActivity","Hello Watch1");
        myRef.child("LikeMovies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("WatchListActivity","Hello Watch2");
             List<String> value = (List<String>) dataSnapshot.child(userID).getValue();
                 Log.d("WatchListActivity", "Hello Watch3");
                for (int i = 0; i < value.size(); i++) {
                    Log.d("WatchListActivity", value.get(i));
                    getMovies(value.get(i));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getMovies(String value) {
            String JSON_URL = "https://api.themoviedb.org/3/movie/" + value + "?api_key=142f01f330865c87d1523d3051162c8b&language=en-US";
            StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //    JSONArray result = jsonObj.getJSONArray("results");
                        //    for (int i = 0; i < result.length(); i++) {
                        //        JSONObject jsonObject = result.getJSONObject(i);
                        Log.d(TAG, jsonObject.toString());
                        Movie movie = new Movie();
                        movie.setId(jsonObject.getString("id"));
                        movie.setTitle(jsonObject.getString("title"));
                        movie.setDate(jsonObject.getString("release_date"));
                        String url = jsonObject.getString("poster_path");
                        String imageURL = "https://image.tmdb.org/t/p/w500" + url;
                        movie.setImageurl(imageURL);
                        String urlback = jsonObject.getString("backdrop_path");
                        String imagebackURL = "https://image.tmdb.org/t/p/w500" + urlback;
                        movie.setBackdropurl(imagebackURL);
                        movie.setRating(jsonObject.getString("vote_average"));
                        movie.setDescrption(jsonObject.getString("overview"));
                        listMovie.add(movie);
                        // }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setUpRecyclerView(listMovie);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            requestQueue = Volley.newRequestQueue(WatchListActivity.this);
            requestQueue.add(request);
        }
    private void setUpRecyclerView(List<Movie> listMovie) {
            RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, listMovie);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(myadapter);
    }
}
