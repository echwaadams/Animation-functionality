package com.adams.topnews.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.adams.topnews.models.Article;
import com.adams.topnews.ui.NewsDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Article> mNews;
    private String mSource;

    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Article> mNews, String source) {
        super(fm, behavior);
        this.mNews = mNews;
        mSource = source;
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.newInstance(mNews, position, mSource);
    }

    @Override
    public int getCount() {
        return mNews.size();
    }
    @Override
    public CharSequence getPageTitle(int position){
        return mNews.get(position).getAuthor();
    }
}
