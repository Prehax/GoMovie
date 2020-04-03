package com.prehax.gomovie.ListViewC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.R;
import com.prehax.gomovie.Theaters;


import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

    private ArrayList<HashMap<String, Object>> getData() {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        final ArrayList<String> Tname = new ArrayList<String>();
        final ArrayList<String> Taddress = new ArrayList<String>();
        final ArrayList<String> Trate = new ArrayList<String>();
        Taddress.add("I'm an address");
        Tname.add("Cinemark");
        Trate.add("3.4");
//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        myRef = mFirebaseDatabase.getReference();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Theaters theaters = dataSnapshot.child("Theaters").child("1").child("Detail").getValue(Theaters.class);
//                Tname.add(theaters.getName());
//                Taddress.add(theaters.getAddress());
//                Trate.add(theaters.getRate());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        /*add data*/
        for (int i = 0; i < Tname.size(); i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Name", Tname.get(i));
            map.put("Address", Taddress.get(i));
            map.put("Rate", "Rate:"+ Trate.get(i));
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

        holder.cname.setText(getData().get(position).get("Name").toString());
        holder.caddress.setText(getData().get(position).get("Address").toString());
        holder.cshowtime.setText(getData().get(position).get("Rate").toString());

        return convertView;
    }

}
