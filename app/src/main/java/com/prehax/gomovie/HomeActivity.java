package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.Adapaters.RecyclerViewAdapter;
import com.prehax.gomovie.Models.Movie;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private DatabaseReference mDatabase;
    private String userID;
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
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG,"onAuthStateChanged:Signed_in:"+user.getUid());
                    //Toast.makeText(HomeActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HomeActivity.this, "Successfully signed out.", Toast.LENGTH_SHORT).show();
                }
            }
        };

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
                    Intent intent3 = new Intent(HomeActivity.this, CardActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.WatchList:
                    Intent intent4 = new Intent(HomeActivity.this, WatchListActivity.class);
                    startActivity(intent4);
                    return true;
                case R.id.purchaseHistory:
                    startActivity(new Intent(HomeActivity.this,TicketHistoryActivity.class));
                    return true;
                case R.id.shwCoupon:
                    Intent intent5 = new Intent(HomeActivity.this,ListCouponActivity.class);
                    startActivity(intent5);
                    return true;
                case R.id.sign:
                    myRef = mFirebaseDatabase.getReference();
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Long i = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Sign").getValue();
                            boolean a;
                            a = Check(i);
                            if (a) {
                                Toast.makeText(HomeActivity.this, "Already signed today", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(HomeActivity.this, "Sign success", Toast.LENGTH_SHORT).show();
                                Calendar calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
                                try {
                                    myRef.child("MovieGoers").child(userID).child("Sign").setValue(day);
                                    myRef.child("MovieGoers").child(userID).child("Signeddays").setValue(+1);
                                    Long days = (Long) dataSnapshot.child("MovieGoers").child(userID).child("Signeddays").getValue();
                                    //if(days>=7){
                                     //
                                    //}
                                } catch (NullPointerException e) {
                                }
                            }
                        }
                        private boolean Check(Long i) {
                            Calendar calendar = Calendar.getInstance();
                            int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
                            System.out.println(day);
                            if (i == day) {
                                return true;
                            } else {
                                return false;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                case R.id.logout:
                    mAuth.signOut();
                    finish();
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
}