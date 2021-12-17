package com.adams.topnews.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;
import com.adams.topnews.adapters.FirebaseNewsViewHolder;
import com.adams.topnews.models.Article;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

public class SavedNewsListActivity extends AppCompatActivity {
    private DatabaseReference mNewsReference;
    private FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder> mFirebaseAdapter;


    @BindView(R.id.recyclerCategory)
    RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView)
    TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //binding views

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        mNewsReference = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_NEWS)
                .child(uid);

        setUpFirebaseAdapter();
        hideProgressBar();
        showNews();
    }
    private void setUpFirebaseAdapter(){
        FirebaseRecyclerOptions<Article> options =
                new FirebaseRecyclerOptions.Builder<Article>()
                .setQuery(mNewsReference, Article.class)
                .build();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Article, FirebaseNewsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseNewsViewHolder firebaseNewsViewHolder, int position, @NonNull Article news) {
                firebaseNewsViewHolder.bindNews(news);
            }

            @NonNull
            @Override
            public FirebaseNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
                return new FirebaseNewsViewHolder(view);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }
    @Override
    public void onStart(){
        super.onStart();
        mFirebaseAdapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (mFirebaseAdapter != null){
            mFirebaseAdapter.stopListening();
        }
    }
    private void showNews() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}