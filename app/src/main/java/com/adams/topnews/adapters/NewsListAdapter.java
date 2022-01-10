package com.adams.topnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;
import com.adams.topnews.models.Article;
import com.adams.topnews.ui.NewsDetailActivity;
import com.adams.topnews.ui.NewsDetailFragment;
import com.adams.topnews.util.OnNewsSelectedListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Article> mArticles = new ArrayList<>();
    private Context mContext;
    private int mOrientation;
    private OnNewsSelectedListener mOnNewsSelectedListener;

    //private NewsViewHolder viewHolder;

    public NewsListAdapter(Context mContext, ArrayList<Article> mArticles, OnNewsSelectedListener newsSelectedListener) {
        this.mArticles = mArticles;
        this.mContext = mContext;
        mOnNewsSelectedListener = newsSelectedListener;
    }

    @Override
    public  NewsListAdapter.NewsViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view, mArticles, mOnNewsSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.NewsViewHolder holder, int position){
        holder.bindNews(mArticles.get(position));
    }
    public int getItemCount(){
        return mArticles.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.newsImageView)
        ImageView mNewsImageView;
        @BindView(R.id.categoryTextView)
        TextView mCategoryTextView;
        @BindView(R.id.newsNameTextView) TextView mNewsNameTextView;
        @BindView(R.id.recyclerCategory) RecyclerView mRecyclerCategory;


        private int mOrientation;
        private Context mContext;

        public NewsViewHolder(View itemView, ArrayList<Article> articles, OnNewsSelectedListener onNewsSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            mOrientation = itemView.getResources().getConfiguration().orientation;
            mArticles = articles;
            mOnNewsSelectedListener = onNewsSelectedListener;
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }

            itemView.setOnClickListener(this);

            //Determines the current orientation of the device:
            mOrientation = itemView.getResources().getConfiguration().orientation;

            //Checks if the recorded orientation  matches Android's landscape configuration.
            //if so, we create a new DetailFragment to display in our special landscape layout:
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(0);
            }
        }

        //Takes position of news in list as parameter:
        private void createDetailFragment(int position) {
            //creates new NewsDetailFragment with the given position:
            NewsDetailFragment newsDetailFragment = NewsDetailFragment.newInstance(mArticles, position,Constants.SOURCE_FIND);
            //Gathers necessary components to replace the FrameLayout with the NewsDetailFragment:
            FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
            //Replaces the FrameLayout with the NewsDetailFragment:
            ft.replace(R.id.NewsDetailContainer, newsDetailFragment);
            //Commits these changes:
            ft.commit();
        }
        public void bindNews(Article news) {
            Picasso.get().load(news.getUrlToImage()).into(mNewsImageView);
            mNewsNameTextView.setText(news.getAuthor());
            //mCategoryTextView.setText(news.getSource().getId());
        }
        //@Override
        public void onClick(View v){
            //Determines the position of the news clicked:
            int itemPosition = getLayoutPosition();
            mOnNewsSelectedListener.onNewsSelected(itemPosition, mArticles);
            if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
                intent.putExtra(Constants.KEY_SOURCE, Constants.SOURCE_FIND);
                mContext.startActivity(intent);
            }

        }
    }
}
