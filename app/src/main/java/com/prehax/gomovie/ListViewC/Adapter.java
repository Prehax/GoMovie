package com.prehax.gomovie.ListViewC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.prehax.gomovie.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    private ArrayList<HashMap<String, Object>> getDate() {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        String[] name = {"Cinemark", "Wanda", "17.5", "xintiandi", "leina", "6", "1213"};
        String[] address = {"I am an address1", "I am an address2", "I am an address3", "I am an address4", "I am an address5", "I am an address6", "I am an address7"};
        String[] rate = {"3.0", "3.3", "4.3", "4.9", "3.5", "2.3", "1.2"};
        /*为动态数组添加数据*/
        for (int i = 0; i < name.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Name", name[i]);
            map.put("Address", address[i]);
            map.put("Rate", "Rate"+rate[i]);
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
        return getDate().size();
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
        holder.cname.setText(getDate().get(position).get("Name").toString());
        holder.caddress.setText(getDate().get(position).get("Address").toString());
        holder.cshowtime.setText(getDate().get(position).get("Rate").toString());

        return convertView;
    }


}
