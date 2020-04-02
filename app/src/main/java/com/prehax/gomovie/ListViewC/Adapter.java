package com.prehax.gomovie.ListViewC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prehax.gomovie.R;

public class Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public Adapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 10;
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
       public String [] cinema;
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
        holder.cname.setText("Name");
        holder.caddress.setText("Address");
        holder.cshowtime.setText("Rate:5.0");

        return convertView;
    }
}
