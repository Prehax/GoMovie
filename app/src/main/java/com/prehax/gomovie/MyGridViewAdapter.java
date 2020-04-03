package com.prehax.gomovie;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.prehax.gomovie.R;

public class MyGridViewAdapter extends BaseAdapter {
    private Context mContext;
    final int itemLength = 100;
    private LayoutInflater mLayoutInflater;
    private int clickTemp = -1;
    int location = -1;
    private int[] clickedList = new int[itemLength];
    public MyGridViewAdapter(Context context){
            this.mContext = context;
            mLayoutInflater = LayoutInflater.from(context);
            for(int i = 0;i<itemLength;i++){
                clickedList[i]=0;
            }
    }

    public MyGridViewAdapter() {
        super();
    }

    public  void setSeclection(int position) {
        clickTemp = position;
        System.out.println(clickTemp);

    }
    @Override
    public int getCount() {
        return itemLength;
    }

    @Override
    public Object getItem(int position) {
        location = position ;
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (clickTemp == position) {
            System.out.println("我运行了么？");
            System.out.println(location);
            System.out.println(clickTemp);
            if (clickedList[position] == 0) {
                System.out.println("我运行了么if？");
//                convertView.setBackgroundColor(Color.RED);
                clickedList[position] = 1;
            } else {
                System.out.println("我运行了么else？");
//                convertView.setBackgroundColor(Color.TRANSPARENT);
                clickedList[position] = 0;
            }
        }
        if(convertView == null){
                convertView = mLayoutInflater.inflate(R.layout.layout_grid_item,null);
                holder = new ViewHolder();
                holder.imageView = convertView.findViewById(R.id.seat);
                convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
