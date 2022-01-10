package com.adams.topnews.network;

import static com.adams.topnews.Constants.NEWSAPI_BASE_URL;
import static com.adams.topnews.Constants.NEWS_API_KEY;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Chain;

import com.adams.topnews.models.Source;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {

//    private static Retrofit retrofit = null;
//    public static  NewsApiInterface getClient(){
////        if (retrofit == null){
////            retrofit = new Retrofit.Builder()
////                    .baseUrl(NEWSAPI_BASE_URL)
////                    .addConverterFactory(GsonConverterFactory.create())
////                    .build();
////        }
////
////        return retrofit.create(NewsApiInterface.class);
////    }
//        if (retrofit ==null){
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request newRequest = chain.request().newBuilder()
//                                    .addHeader("apiKey",NEWS_API_KEY)
//                                    .build();
//                            Log.e("adams",newRequest.toString());
//                            return chain.proceed(newRequest);
//                        }
//                    })
//                    .build();
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(NEWSAPI_BASE_URL)
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return  retrofit.create(NewsApiInterface.class);
//    }

    private static Retrofit retrofit = null;
    public static  NewsApiInterface getClient() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://dark-sky.p.rapidapi.com/%7Blatitude%7D,%7Blongitude%7D?units=auto&lang=en")
                    .get()
                    .addHeader("x-rapidapi-host", "dark-sky.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "099553db8dmsh68c697c48214419p1d5221jsnf1df0021aec3")
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retrofit.create(NewsApiInterface.class);
    }

}





