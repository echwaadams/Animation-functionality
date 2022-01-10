package com.adams.topnews.adapters;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;
import com.adams.topnews.models.Article;
import com.adams.topnews.ui.NewsDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public ImageView mNewImageView;

    public FirebaseNewsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }
    public void bindNews(Article news){
        mNewImageView = (ImageView) mView.findViewById(R.id.newsImageView);
        ImageView newsImageView = (ImageView) mView.findViewById(R.id.newsImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.newsNameTextView);
        TextView category = (TextView) mView.findViewById(R.id.categoryTextView);

        Picasso.get().load(news.getUrlToImage()).into(newsImageView);

        if (!news.getUrlToImage().contains("https")) {
            try {
                Bitmap imageBitmap = decodeFromFirebaseBase64(news.getUrlToImage());
                mNewImageView.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // This block of code should already exist, we're just moving it to the 'else' statement:
            Picasso.get().load(news.getUrlToImage()).into(mNewImageView);
            nameTextView.setText(news.getAuthor());
            category.setText(news.getSource());
        }

        nameTextView.setText(news.getAuthor());
        category.setText(news.getSource());
    }
    @Override
    public void onClick(View view){
        final ArrayList<Article> news = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_NEWS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    news.add(snapshot.getValue(Article.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("news", Parcels.wrap(news));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    //@Override
    public void onItemSelected(){
        Log.d("Animation", "onItemSelected");
        //we will add animations here
//        itemView.animate()
//                .alpha(0.7f)
//                .scaleX(0.9f)
//                .scaleY(0.9f)
//                .setDuration(500);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_on);
        set.setTarget(itemView);
        set.start();
    }
    //@Override
    public void onItemClear() {
//        Log.d("animation", "onItemClear");
//        //we will add animation here
//        itemView.animate()
//                .alpha(1f)
//                .scaleX(1f)
//                .scaleY(1f);
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(mContext,
                R.animator.drag_scale_off);
        set.setTarget(itemView);
        set.start();
    }

    public static Bitmap decodeFromFirebaseBase64(String image) throws IOException {
        byte[] decodedByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);
    }
}
