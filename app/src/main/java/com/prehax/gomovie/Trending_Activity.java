package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.prehax.gomovie.Adapaters.RecyclerViewAdapter;
import com.prehax.gomovie.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trending_Activity extends AppCompatActivity {

    private static final String TAG = "Trending_Activity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String JSON_URL =  "https://api.themoviedb.org/3/trending/movie/day?api_key=142f01f330865c87d1523d3051162c8b";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Movie> listMovie;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestQueue = Volley.newRequestQueue(this);
        listMovie = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        jsonRequest();
    }


    private void jsonRequest(){
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray result = jsonObj.getJSONArray("results");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jsonObject = result.getJSONObject(i);
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
                    }
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

        requestQueue = Volley.newRequestQueue(Trending_Activity.this);
        requestQueue.add(request);
    }

    private void setUpRecyclerView(List<Movie> listMovie) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,listMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_trendingmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.upcoming:
                Intent intent1 = new Intent(Trending_Activity.this, Upcoming_Activity.class);
                startActivity(intent1);
                return true;
            case R.id.Latest:
                Intent intent5 = new Intent(Trending_Activity.this,HomeActivity.class);
                startActivity(intent5);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


