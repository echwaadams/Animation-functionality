package com.adams.topnews.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.adams.topnews.Constants;
import com.adams.topnews.R;
import com.adams.topnews.adapters.NewsListAdapter;
import com.adams.topnews.adapters.NewsPagerAdapter;
import com.adams.topnews.models.Article;
import com.adams.topnews.models.NewsBusinessesSearchResponse;
import com.adams.topnews.network.NewsApiInterface;
import com.adams.topnews.network.NewsClient;
import com.adams.topnews.util.OnNewsSelectedListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsListActivity extends AppCompatActivity implements OnNewsSelectedListener {
    private Integer mPosition;
    ArrayList<Article> mArticles;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentAddress;

    private DatabaseReference mSearchedLocationReference;

    private ValueEventListener mSearchedLocationReferenceListener;


    private static final String TAG = NewsListActivity.class.getSimpleName();


    @BindView(R.id.textView) TextView mErrorTextView;
    @BindView(R.id.firebaseProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.recyclerCategory)
    RecyclerView mRecyclerCategory;

    private NewsListAdapter mAdapter;

    public List<Article> mNews;
    private RecyclerView mRecyclerView;

    String mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        //Binding views
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentAddress = mSharedPreferences.getString(Constants.PREFERENCES_LOCATION_KEY, null);

        mArticles = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_NEWS));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        mAdapter = new NewsPagerAdapter(getSupportFragmentManager(), mArticles);
        mRecyclerCategory.setAdapter(mAdapter);
        mAdapter.setCurrentItem(startingPosition);

        if (mRecentAddress != null) {
            fetchNews(mRecentAddress);
        }

        if (savedInstanceState != null) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                mPosition = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                mArticles = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_NEWS));
                mSource = savedInstanceState.getString(Constants.KEY_SOURCE);

                if (mPosition != null && mArticles != null) {
                    Intent intent = new Intent(this, NewsDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_KEY_POSITION, mPosition);
                    intent.putExtra(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
                    intent.putExtra(Constants.KEY_SOURCE, mSource);
                    startActivity(intent);
                }
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mPosition != null && mArticles != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, mPosition);
            outState.putParcelable(Constants.EXTRA_KEY_NEWS, Parcels.wrap(mArticles));
            outState.putString(Constants.KEY_SOURCE, mSource);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {
                addToSharedPreferences(location);
                fetchNews(location);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String location) {
                return false;
            }
        });
        return true;
    }


    //@Override
    public void onNewsSelected(Integer position, ArrayList<Article> articles) {
        mPosition = position;
        mArticles = articles;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return super.onOptionsItemSelected(item);
    }
    private void showUnsuccessfulMessage(){
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }
    private void showNews(){
        mRecyclerCategory.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void addToSharedPreferences(String country){
        mEditor.putString(Constants.PREFERENCES_LOCATION_KEY, country).apply();
    }

    private void fetchNews(String country){
        Log.e("adams","fetching news");

        NewsClient.getClient().getAdams("business","d2b009aa81f942d4bba769b035e179a4")
                .enqueue(new Callback<NewsBusinessesSearchResponse>() {

            @Override
            public void onResponse(Call<NewsBusinessesSearchResponse> call, Response<NewsBusinessesSearchResponse> response) {

                hideProgressBar();

                Log.e("adams",response.message());
                if (response.isSuccessful()) {
                    mNews = response.body().getArticles();
                    NewsListAdapter mAdapter = new NewsListAdapter(NewsListActivity.this, mNews);
                    mRecyclerCategory.setAdapter(mAdapter);
                    mRecyclerCategory.setLayoutManager(new LinearLayoutManager(NewsListActivity.this));
                    mRecyclerCategory.setHasFixedSize(true);

                    showNews();
                } else {
                    showUnsuccessfulMessage();
                }

            }

            @Override
            public void onFailure(Call<NewsBusinessesSearchResponse> call, Throwable t) {
                //Log.e("adams",call.toString());
                Log.e("adams",t.getMessage());
                hideProgressBar();
                showFailureMessage();
            }
        });



   }

    private void   showFailureMessage(){
        mErrorTextView.setText("Something went wrong,Check your internet connection");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnSuccessfullMessage(){
        mErrorTextView.setText("Something went wrong,Try Again Later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void  ShowNews(){
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewsSelected(Integer position, ArrayList<Article> articles, String source) {
        mSource = source;
    }


}

