package com.prehax.gomovie.Adapaters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.prehax.gomovie.HomeActivity;
import com.prehax.gomovie.MovieTrailerActivity;
import com.prehax.gomovie.R;

import java.util.List;

import com.prehax.gomovie.Models.Movie;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mData;
    RequestOptions options;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String userID;
    private int count;

    public RecyclerViewAdapter(Context mContext, List<Movie> mData) {
        this.mContext = mContext;
        this.mData = mData;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.loadingshape);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.movie_cell_layout, parent , false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.linearContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(mContext, MovieTrailerActivity.class);
                i.putExtra("movie_name",mData.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("movie_description",mData.get(viewHolder.getAdapterPosition()).getDescrption());
                i.putExtra("movie_rating",mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("movie_image",mData.get(viewHolder.getAdapterPosition()).getImageurl());
                i.putExtra("movie_backdrop",mData.get(viewHolder.getAdapterPosition()).getBackdropurl());
                i.putExtra("movieid",mData.get(viewHolder.getAdapterPosition()).getId());
                mContext.startActivity(i);
            }
        });
        return viewHolder;
    }

    @SuppressLint("Assert")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.titleText.setText(mData.get(position).getTitle());
        holder.genre.setText(mData.get(position).getGenre());
        holder.rating.setText(mData.get(position).getRating());
        holder.releaseDate.setText(mData.get(position).getDate());
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("LikeMovies").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> value = (List<String>) dataSnapshot.child(userID).getValue();
                if (value != null) {
                    count = value.size();
                } else {
                    userID = user.getUid();
                    myRef = FirebaseDatabase.getInstance().getReference();
                    myRef.child("LikeMovies").child(userID);
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
                Log.d("HomeActivity",mid);
                myRef.child("LikeMovies").child(userID).child(String.valueOf(count)).setValue(mid);
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
        TextView genre;
        ImageView imageThumbnail;
        LinearLayout linearContainer;
        Button likeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearContainer = itemView.findViewById(R.id.movieContainer);
            titleText = itemView.findViewById(R.id.title);
            releaseDate = itemView.findViewById(R.id.date);
            rating = itemView.findViewById(R.id.rating);
            genre = itemView.findViewById(R.id.genre);
            imageThumbnail = itemView.findViewById(R.id.thumbnail);
            likeButton = itemView.findViewById(R.id.star_button);
        }
    }
}
