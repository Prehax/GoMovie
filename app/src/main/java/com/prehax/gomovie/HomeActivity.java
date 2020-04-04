package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity<override> extends AppCompatActivity {

    /*tested....*/
    private static final String TAG = "HomeActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    //MyAdapter adapter;
    //Bitmap images;
    //private RecyclerView.LayoutManager layoutManager;
    List<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
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
            HashMap<String, String> hm = new HashMap<String, String>();
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
        lv = (ListView) findViewById(R.id.listTrending);
        lv.setAdapter(adapter);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_homemenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }
}

//    private class JsonTask extends AsyncTask<String, String, Void> {
//
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//         //   pd = new ProgressDialog(HomeActivity.this);
//          //  pd.setMessage("Please wait");
//          //  pd.setCancelable(false);
//           // pd.show();
//        }
//
//        @SuppressLint("WrongThread")
//        protected Void doInBackground(String... params) {
//            HttpHandler sh = new HttpHandler();
//            // Making a request to url and getting response
//            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=142f01f330865c87d1523d3051162c8b&language=en-US&page=1";
//            String jsonStr = sh.makeServiceCall(url);
//
//            Log.d(TAG, "Response from url: " + jsonStr);
//            if (jsonStr != null) {
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("results");
//                    for (int i = 0; i < contacts.length(); i++) {
//
//                            JSONObject c = contacts.getJSONObject(i);
//                            String title = c.getString("original_title");
//                            String rating = c.getString("vote_average");
//                            String imageURL = c.getString("poster_path");
//                       //    RequestCreator bitmap = Picasso.get().load(imageURL);
//                            HashMap<String, Object> contact = new HashMap<String, Object>();
//                         // String url1 = "https://image.tmdb.org/t/p/w500"+ imageURL;
//
//                          images = getBitmapFromURL(imageURL);
//                     //   Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.image);
//                        //Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//                    //    Drawable d = new BitmapDrawable(images);
//                       // imageView.setImageDrawable(d);
//
//                        //ImageView b = (ImageView) findViewById(R.id.toggle_btn);
//                        //Picasso.get().load("https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg").into(b);
//
//                            //titles.add(title);
//                            contact.put("title", title);
//                            contact.put("rating",rating);
//                            contact.put("imageURL",images);
//                            for(int j=0;j<20;j++) {
//                                contact.put("poster", Integer.toString(posterImages[j]));
//                            }
//                            titles.add(images);
//                          //  contact.put("bitmap",bitmap.toString());
//                          //  Log.d(TAG, bitmap.toString());
//                            items.add(contact);
//                        Log.d(TAG, String.valueOf(contact));
//                        }
//
//                } catch (final JSONException | IOException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//            }
//            else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            return null;
//            }
//
//            @Override
//            protected void onPostExecute (Void result){
//                super.onPostExecute(result);
//                Log.d(TAG,"PostExe");
//                onCancelled();
//              //  items.add(tit)
//                //txtJson.setText(result);
//              //  ImageView b = (ImageView) findViewById(R.id.toggle_btn);
//                ListAdapter adapter = new SimpleAdapter(HomeActivity.this, items,
//                        R.layout.activity_moviecell, new String[]{"rating","title","poster"},
//                        new int[]{R.id.ratingText,R.id.titleText,R.id.toggle_btn});
//                lv.setAdapter(adapter);
//        //        lv.setAdapter((ListAdapter) new MyAdapter(HomeActivity.this, items));
////                for(int i=0;i<adapter.getCount();i++){
////                    HashMap<String, Object> hm = (HashMap<String, Object>) adapter.getItem(i);
////                    String imgUrl = (String) hm.get("imageURL");
////                    b.setImageBitmap(images);
////                }
//            }
//    }
//
//    public Bitmap getBitmapFromURL(String src) throws IOException {
//       // return Picasso.get().load(src);
//        try {
//            URL url = new URL("https://image.tmdb.org/t/p/w500" + src);
//            Log.d(TAG, url.toString());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } finally {
//
//        }
//    }
//
//        class HttpHandler {
//
//            private final String TAG = HttpHandler.class.getSimpleName();
//
//            public HttpHandler() {
//            }
//
//            public String makeServiceCall(String reqUrl) {
//                String response = null;
//                try {
//                    URL url = new URL(reqUrl);
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    // read the response
//                    InputStream in = new BufferedInputStream(conn.getInputStream());
//                    response = convertStreamToString(in);
//                } catch (MalformedURLException e) {
//                    Log.e(TAG, "MalformedURLException: " + e.getMessage());
//                } catch (ProtocolException e) {
//                    Log.e(TAG, "ProtocolException: " + e.getMessage());
//                } catch (IOException e) {
//                    Log.e(TAG, "IOException: " + e.getMessage());
//                } catch (Exception e) {
//                    Log.e(TAG, "Exception: " + e.getMessage());
//                }
//                return response;
//            }
//
//            private String convertStreamToString(InputStream is) {
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//                StringBuilder sb = new StringBuilder();
//
//                String line;
//                try {
//                    while ((line = reader.readLine()) != null) {
//                        sb.append(line).append('\n');
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                return sb.toString();
//            }
//        }
//    }

