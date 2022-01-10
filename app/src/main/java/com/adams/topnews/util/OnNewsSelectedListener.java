package com.adams.topnews.util;

import com.adams.topnews.models.Article;

import java.util.ArrayList;

public interface OnNewsSelectedListener {
    public void onNewsSelected(Integer position, ArrayList<Article> articles, String source);
}
