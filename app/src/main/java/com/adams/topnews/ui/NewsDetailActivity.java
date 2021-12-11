package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.adams.topnews.R;
import com.adams.topnews.adapters.NewsPagerAdapter;
import com.adams.topnews.models.Article;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewpager;
    private NewsPagerAdapter adapterViewPager;
    List<Article> mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        //binding views
        ButterKnife.bind(this);

        mNews = Parcels.unwrap(getIntent().getParcelableExtra("news"));
        int startingPoint = getIntent().getIntExtra("position", 0);

        adapterViewPager = new NewsPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.
                BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mNews);
        mViewpager.setAdapter(adapterViewPager);
        mViewpager.setCurrentItem(startingPoint);
    }
}