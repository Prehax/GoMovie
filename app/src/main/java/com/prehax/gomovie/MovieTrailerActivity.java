package com.prehax.gomovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MovieTrailerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);


        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String videoId = "S0Q4gqBUs7c";
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        getSupportActionBar().hide();
        String title = getIntent().getExtras().getString("movie_name");
        String description = getIntent().getExtras().getString("movie_description");
        String rating = getIntent().getExtras().getString("movie_rating");
        String imageUrl = getIntent().getExtras().getString("movie_backdrop");


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView textViewTitle = findViewById(R.id.MovieDescription);
     //   ImageView poster = findViewById(R.id.tralier_thumbnail);


      //  Glide.with(this).load(imageUrl).into(poster);
        textViewTitle.setText(description);
        collapsingToolbarLayout.setTitle(title);

    }

    public void selTheater(View view) {
        Intent intent = new Intent(MovieTrailerActivity.this, TheaterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieName", getIntent().getExtras().getString("movie_name"));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
