package com.prehax.gomovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prehax.gomovie.R;

import java.util.ArrayList;
import java.util.List;

public class SeatMapAdapter extends BaseAdapter {
    private Context context;
    private List<Seat> seats = new ArrayList<Seat>();
    private String[] titles;
    private int[] images = {R.drawable.seat_gray, R.drawable.seat_green, R.drawable.seat_sold};
    private int numOfRow, numOfCol;
    private ArrayList<Integer> seatSed;

    public SeatMapAdapter(int numOfCol, int numOfRow, List<Integer> seatSed, Context context) {
        super();
        titles = new String[numOfCol*numOfRow];
        fillSeatMap();
        this.context = context;
        int status=0, j=0;
        for(int i = 0; i<titles.length; i++) {
            if (j<seatSed.size() && seatSed.get(j) == i) {
                status = 2;  // occupied
                j++;
            } else {
                status = 0;  // available
            }
            Seat seat = new Seat(titles[i], images[status]);
            seats.add(seat);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // get a new container
            convertView = LayoutInflater.from(this.context).inflate(R.layout.layout_seat_item, null);
            // Initialization
            viewHolder.image = convertView.findViewById(R.id.iv_si_seat);
            viewHolder.title = convertView.findViewById(R.id.tv_si_pos);
            // attach a obj
            convertView.setTag(viewHolder);
        }
        else {
            // get the obj attached
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Set resources to the component
        Seat seat = seats.get(position);
        viewHolder.image.setImageResource(seat.getImageId());
        viewHolder.title.setText(seat.getTitle());
        System.out.println("当前运行在" + position + "位置.");

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return seats.get(position);
    }

    @Override
    public int getCount() {
        if (null != seats) {
            return seats.size();
        } else {
            return 0;
        }
    }

    private void fillSeatMap() {
        for (int row = 0; row < numOfRow; row ++) {
            for (int col = 0; col < numOfCol; col++) {
                titles[row*numOfCol+col] = "r"+(row+1)+"c"+(col+1);
            }
        }
    }

    public boolean setSelected(int position, View view, ViewGroup parent) {

        Seat seat = seats.get(position);
        if(seat.getImageId()==R.drawable.seat_gray) {
            seat.setImageId(R.drawable.seat_green);
            getView(position, view, parent);
            return true;
        } else if (seat.getImageId()==R.drawable.seat_green) {
            seat.setImageId(R.drawable.seat_gray);
            getView(position, view, parent);
            return true;
        } else {
            Toast.makeText(this.context, "This seat is occuppied, please select another one", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    class ViewHolder {
        public TextView title;
        public ImageView image;
    }

    class Seat {
        // Status: 0 means available
        // 1 means occupied
        // 2 means selected
        private String title;
        private int imageId;

        public Seat(String title, Integer imageId) {
            this.imageId=imageId;
            this.title=title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public String getTitle() {
            return title;
        }

        public int getImageId() {
            return imageId;
        }
    }
}
