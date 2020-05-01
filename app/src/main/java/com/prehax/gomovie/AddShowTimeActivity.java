package com.prehax.gomovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddShowTimeActivity extends AppCompatActivity {
    private EditText etDate, etTime;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Spinner spinMovie, spinTheater;
    private TextView tvTest;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Theaters");
    private String[] theaterNames;
    private ArrayList<String> movieNames = new ArrayList<String>();
    private final String JSON_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=142f01f330865c87d1523d3051162c8b&language=en-US&page=1";
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show_time);
        // find ID
        etDate = findViewById(R.id.et_ast_date);
        etTime = findViewById(R.id.et_ast_time);
        spinMovie = findViewById(R.id.spin_ast_movie);
        spinTheater = findViewById(R.id.spin_ast_theater);
        tvTest = findViewById(R.id.tv_ast_test);
        // Make editText uneditable
        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);
        // set Movie name
        requestQueue = Volley.newRequestQueue(this);
        jsonRequest();
        // Read from theater Name from database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    long numOfTheater = dataSnapshot.getChildrenCount();
                    theaterNames = new String[(int)numOfTheater];
                    for (int i=0; i<numOfTheater; i++) {
                        theaterNames[i]=dataSnapshot.child(Integer.toString(i)).child("name").getValue(String.class);
                    }
                    ArrayAdapter<String> arrayAdapterTheater = new ArrayAdapter<String>(AddShowTimeActivity.this, R.layout.item_select, theaterNames);
                    spinTheater.setAdapter(arrayAdapterTheater);
                } catch (NullPointerException e) {
                    System.out.println("no data in database, nullptr");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Can not read data from database");
            }
        });
    }

    public void datePick(View view) {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        datePickerDialog = new DatePickerDialog(AddShowTimeActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText(formatDate(year, monthOfYear+1, dayOfMonth));
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    public void timePick(View view) {
        // time picker dialog
        timePickerDialog = new TimePickerDialog(AddShowTimeActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etTime.setText(hourOfDay + ":" + minute);
            }
        }, 12, 0, true);
        timePickerDialog.show();
    }

    public String formatDate(int year, int month, int day) {
        String strMonth, strDay;
        if(month < 10) strMonth = "0"+Integer.toString(month);
        else strMonth = Integer.toString(month);

        if(day < 10) strDay = "0"+Integer.toString(day);
        else strDay = Integer.toString(day);

        return Integer.toString(year) + "-" + strMonth + "-" + strDay;
    }

    private void jsonRequest() {
        StringRequest request = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    JSONArray result = jsonObj.getJSONArray("results");
                    String tempName;
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject jsonObject = result.getJSONObject(i);
                        tempName = jsonObject.getString("title");
                        movieNames.add(tempName);
                    }
                    ArrayAdapter<String> arrayAdapterMovie = new ArrayAdapter<String>(AddShowTimeActivity.this, R.layout.item_select, movieNames);
                    spinMovie.setAdapter(arrayAdapterMovie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Some errors happened here");
            }
        });
        requestQueue = Volley.newRequestQueue(AddShowTimeActivity.this);
        requestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_show_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mbtn_ast_confirm:
                //tvTest.setText("movieName: " + spinMovie.getSelectedItem().toString() + "\ntheaterName: " + spinTheater.getSelectedItem().toString() + etDate.getText().toString() + etTime.getText().toString());
                final int theaterID = spinTheater.getSelectedItemPosition();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long numOfShow = dataSnapshot.child(Integer.toString(theaterID)).child("ShowTimes").getChildrenCount();
                        myRef.child(Integer.toString(theaterID)).child("ShowTimes").child(Long.toString(numOfShow)).child("time").setValue(etDate.getText().toString() + " " + etTime.getText().toString());
                        myRef.child(Integer.toString(theaterID)).child("ShowTimes").child(Long.toString(numOfShow)).child("movie").setValue(spinMovie.getSelectedItem().toString());
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });

                break;
            case R.id.mbtn_ast_cancel:
                break;
            default:
                break;
        }
        return true;
    }
}
