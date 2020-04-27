package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieTrailerActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String urlKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        requestQueue = Volley.newRequestQueue(this);
        getSupportActionBar().hide();
        String title = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("movie_description");
        String rating = getIntent().getExtras().getString("movie_rating");
        String imageUrl = getIntent().getExtras().getString("movie_backdrop");
        String mid = getIntent().getExtras().getString("movieid");
        String JSON_URL = "https://api.themoviedb.org/3/movie/"+mid+"/videos?api_key=142f01f330865c87d1523d3051162c8b&language=en-US";
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray result = jsonObj.getJSONArray("results");
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        urlKey = jsonObject.getString("key");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(MovieTrailerActivity.this);
        requestQueue.add(request);

    CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

    TextView textViewTitle = findViewById(R.id.MovieDescription);

        textViewTitle.setText(description);
        collapsingToolbarLayout.setTitle(title);
    YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);

    getLifecycle().

    addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new

    AbstractYouTubePlayerListener() {
        @Override
        public void onReady (YouTubePlayer youTubePlayer){
            super.onReady(youTubePlayer);
            String videoId = urlKey;
            youTubePlayer.loadVideo(videoId, 0);
        }
    });
}

    public void selTheater(View view) {
        Intent intent = new Intent(MovieTrailerActivity.this, TheaterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieName", getIntent().getExtras().getString("movie_name"));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
