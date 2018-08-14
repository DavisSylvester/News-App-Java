package com.sylvesterllc.newapps1.Services;


import android.content.Context;
import android.util.Log;


//import com.github.kittinunf.fuel.Fuel;
//import com.github.kittinunf.fuel.core.FuelError;
//import com.github.kittinunf.fuel.core.Handler;
//import com.github.kittinunf.fuel.core.Request;
//import com.github.kittinunf.fuel.core.Response;
//import com.google.gson.Gson;
//import com.google.gson.JsonParser;
import com.sylvesterllc.newapps1.Interfaces.onDataChangerListener;
import com.sylvesterllc.newapps1.Loaders.NewsArticleLoader;
import com.sylvesterllc.newapps1.Models.GuardApiData;

import org.json.JSONObject;

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

    public void GetNewsOverHttps() {

        new NewsArticleLoader(context).loadInBackground();


    }
}
