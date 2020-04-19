package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.prehax.gomovie.Adapaters.RecyclerViewAdapter;
import com.prehax.gomovie.Models.Movie;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private final String JSON_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=142f01f330865c87d1523d3051162c8b&language=en-US&page=1";
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

                Log.d(TAG, "Hello");

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray result = jsonObj.getJSONArray("results");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        Log.d(TAG, jsonObject.toString());
                        Movie movie = new Movie();
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

        requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(request);
    }

    private void setUpRecyclerView(List<Movie> listMovie) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,listMovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
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
}