package com.prehax.gomovie.ListViewC;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.R;
import com.prehax.gomovie.Theater;


import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends BaseAdapter {
    private static final String TAG = "Adapter";
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String tempName, tempAddress, tempRate;
    /*
    private ArrayList<String> Tname = new ArrayList<String>();
    private ArrayList<String> Taddress = new ArrayList<String>();
    private ArrayList<String> Trate = new ArrayList<String>();
*/

    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

    private ArrayList<HashMap<String, Object>> getData() {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        final ArrayList<String> Tname = new ArrayList<String>();
        final ArrayList<String> Taddress = new ArrayList<String>();
        final ArrayList<String> Trate = new ArrayList<String>();


        Taddress.add("I'm an address");
        Tname.add("Cinemark");
        Trate.add("3.4");

        /*
        读数据从这开始
         */

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // showData(dataSnapshot);
                try {
                    Theater theater = dataSnapshot.child("Theaters").child("a3").getValue(Theater.class);
                    tempName=theater.getName();
                    tempAddress=theater.getAddress();
                    tempRate=String.format("%.1f", theater.getRate());
                    System.out.println("现在我在读取数据后, 读到了以下数据: ");
                    System.out.println(tempName+" "+tempAddress+" "+tempRate);
                } catch (NullPointerException e) {
                    Log.w(TAG, "No values to read");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
/*
        Taddress.add("I'm 2 address");
        Tname.add("Caonima");
        Trate.add("3.9");
 */
        System.out.println("现在我在掺入数据前, 插入以下数据: ");
        System.out.println(tempName+" "+tempAddress+" "+tempRate);
        Tname.add(tempName);
        Taddress.add(tempAddress);
        Trate.add(tempRate);
        /*add data*/
        for (int i = 0; i < Tname.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", Tname.get(i));
            map.put("address", Taddress.get(i));
            map.put("rate", "Rate: "+ Trate.get(i));
            listItem.add(map);
        }
        return listItem;
    }

    public Adapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return getData().size();
        //data length
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
       public TextView cname;
       public TextView caddress;
       public TextView cshowtime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.layout_list_choose,null);
            holder = new ViewHolder();
            holder.cname = convertView.findViewById(R.id.Lcname);
            holder.caddress = convertView.findViewById(R.id.Lcaddress);
            holder.cshowtime= convertView.findViewById(R.id.Lcshowtime);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        // give value
        System.out.println("目前正在执行getView, 调用getData前: "+ tempName+" "+tempAddress+" "+tempRate);
        try {
            holder.cname.setText(getData().get(position).get("name").toString());
            holder.caddress.setText(getData().get(position).get("address").toString());
            holder.cshowtime.setText(getData().get(position).get("rate").toString());
            System.out.println("目前正在执行getView, 正常调用getData: "+ tempName+" "+tempAddress+" "+tempRate);
        } catch (NullPointerException e) {
            System.out.println("目前正在执行getView, 调用getData后且出现了空指针: "+ tempName+" "+tempAddress+" "+tempRate);
        }

        return convertView;
    }

}
