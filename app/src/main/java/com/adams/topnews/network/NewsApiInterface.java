package com.adams.topnews.network;

import static com.adams.topnews.Constants.NEWS_API_KEY;

import com.adams.topnews.BuildConfig;
import com.adams.topnews.models.NewsBusinessesSearchResponse;

import java.util.Map;

import javax.xml.transform.Source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NewsApiInterface {
    @GET("/v2/everything")
    Call<NewsBusinessesSearchResponse> getAdams(
            @Query("q") String keyword,
            @Query("apiKey") String apiKey
    );

}
