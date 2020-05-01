package com.prehax.gomovie.Adapaters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prehax.gomovie.Models.Movie;
import com.prehax.gomovie.MovieTrailerActivity;
import com.prehax.gomovie.R;

import java.util.List;

public class RecyclerViewUpAdminAdapter extends RecyclerView.Adapter<RecyclerViewUpAdminAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mData;
    RequestOptions options;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID;
    private int count;

    public RecyclerViewUpAdminAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;
        options = new RequestOptions().centerCrop().placeholder(R.drawable.loadingshape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_adminmoviecell, parent , false);
        final RecyclerViewUpAdminAdapter.MyViewHolder viewHolder = new RecyclerViewUpAdminAdapter.MyViewHolder(view);
//        viewHolder.linearContainer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(mContext, MovieTrailerActivity.class);
//                i.putExtra("movie_name",mData.get(viewHolder.getAdapterPosition()).getTitle());
//                i.putExtra("movie_rating",mData.get(viewHolder.getAdapterPosition()).getRating());
//                i.putExtra("movie_image",mData.get(viewHolder.getAdapterPosition()).getImageurl());
//                i.putExtra("movieid",mData.get(viewHolder.getAdapterPosition()).getId());
//                mContext.startActivity(i);
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewUpAdminAdapter.MyViewHolder holder, final int position) {

        holder.titleText.setText(mData.get(position).getTitle());
        holder.rating.setText(mData.get(position).getRating());
        holder.releaseDate.setText(mData.get(position).getDate());
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("AdminUpcoming").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> value = (List<String>) dataSnapshot.getValue();
                if (value != null) {
                    count = value.size();
                } else {
                    myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("AdminUpcoming");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mid = mData.get(position).getId();
                myRef.child("AdminUpcoming").child(String.valueOf(count)).setValue(mid);
            }
        });

        Glide.with(mContext).load(mData.get(position).getImageurl()).apply(options).into(holder.imageThumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static  class  MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView releaseDate;
        TextView rating;
        ImageView imageThumbnail;
        LinearLayout linearContainer;
        Button likeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearContainer = itemView.findViewById(R.id.movieAdminContainer);
            titleText = itemView.findViewById(R.id.titleAdmin);
            releaseDate = itemView.findViewById(R.id.dateAdmin);
            rating = itemView.findViewById(R.id.ratingAdmin);
            imageThumbnail = itemView.findViewById(R.id.thumbnailAdmin);
            likeButton = itemView.findViewById(R.id.star_button_Admin);
        }
    }
}
