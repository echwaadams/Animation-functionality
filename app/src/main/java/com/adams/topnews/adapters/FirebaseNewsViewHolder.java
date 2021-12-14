package com.adams.topnews.adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class FirebaseNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseNewsViewHolder(View itemView){
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }
    public void bindNews(Article news){
        ImageView newsImageView = (ImageView) mView.findViewById(R.id.newsImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.newsNameTextView);
        TextView category = (TextView) mView.findViewById(R.id.categoryTextView);

        Picasso.get().load(news.getUrlToImage()).into(newsImageView);

        nameTextView.setText(news.getAuthor());
        category.setText(news.getSource().getSystemId());
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
}
