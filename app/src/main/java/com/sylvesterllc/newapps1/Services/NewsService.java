package com.sylvesterllc.newapps1.Services;


import android.content.Context;
import android.util.Log;


import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sylvesterllc.newapps1.Interfaces.onDataChangerListener;
import com.sylvesterllc.newapps1.Loaders.NewsArticleLoader;
import com.sylvesterllc.newapps1.Models.GuardApiData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NewsService {


    private final String searchString = "computer";
    private final String API_PATH = String.format("https://content.guardianapis.com/tags?q=%s&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f", searchString);

    private final Context context;

    public GuardApiData newsFeed = new GuardApiData();

    public NewsService(Context ctx) {

        context = ctx;
    }

    public void GetNews(final onDataChangerListener listener) {

        String tempResult = "";
        final String result = "";

        try {

            Fuel.get(API_PATH).responseString(new Handler<String>() {
                @Override
                public void failure(Request request, Response response, FuelError error) {
                    //do something when it is failure

                    Log.d("HELP333", error.toString());

                }

                @Override
                public void success(Request request, Response response, String data) {
                    //do something when it is successful
                    //Log.d("HELP", request.toString());
                    Log.d("HELP", response.toString());
                    Log.d("HELP1", data);

                    Gson gson = new Gson();
                    JsonParser jsonParser = new JsonParser();
                    GuardApiData aa =  gson.fromJson(data, GuardApiData.class);
                    Log.d("HELP2", aa.response.results.get(0).webTitle);

                    newsFeed = aa;

                    listener.onDataChange(aa.response.results);


                }
            });
        }
        catch (Exception e){
    Exception aaa = e;
        }
    }

    public void GetNewsOverHttps() {

        new NewsArticleLoader(context).loadInBackground();


    }
}
