package com.adams.topnews.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.adams.topnews.R;
import com.adams.topnews.models.Article;
import com.adams.topnews.ui.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private List<Article> mNews;
    private Context mContext;
    private NewsViewHolder viewHolder;

    public NewsListAdapter(Context mContext, List<Article> mNews) {
        this.mNews = mNews;
        this.mContext = mContext;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.NewsViewHolder holder, int position){
        holder.bindNews(mNews.get(position));
    }
    public int getItemCount(){
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.newsImageView)
        ImageView mNewsImageView;
        @BindView(R.id.categoryTextView)
        TextView mCategoryTextView;
        @BindView(R.id.newsNameTextView) TextView mNewsNameTextView;
        @BindView(R.id.recyclerCategory) RecyclerView mRecyclerCategory;


        private Context mContext;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener((View.OnClickListener) this);
        }

        public void bindNews(Article news) {
            Picasso.get().load(news.getUrlToImage()).into(mNewsImageView);
            mNewsNameTextView.setText(news.getAuthor());
            //mCategoryTextView.setText(news.getSource().getId());
        }
        //@Override
        public void onClick(View v){
            int itemPosition = getItemCount();
            Intent intent = new Intent(mContext, NewsDetailActivity.class);
            intent.putExtra("position", itemPosition);
            intent.putExtra("news", Parcels.wrap(mNews));
            mContext.startActivity(intent);
        }
    }
}
