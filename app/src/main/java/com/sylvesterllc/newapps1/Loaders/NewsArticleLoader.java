package com.sylvesterllc.newapps1.Loaders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.sylvesterllc.newapps1.Models.NewsArticle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewsArticleLoader extends AsyncTaskLoader<String> {

    private final String API_PATH =
            "https://content.guardianapis.com/tags?q=business&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f";

    public NewsArticleLoader(Context context){

        super(context);
        loadInBackground();
    }


    @Nullable
    @Override
    public String loadInBackground() {

        return GetNewsOverHttps();

    }

    public String GetNewsOverHttps() {

        URL url;
        String result = "";
        HttpURLConnection urlConnection;

        try {
            url = new URL(API_PATH);
            urlConnection = (HttpURLConnection) url.openConnection();

            try {
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String aa = "";
                StringBuilder sb = new StringBuilder();

                while ((aa = br.readLine()) != null) {
                    sb.append(aa);
                }

                result = sb.toString();

                return result;

            } catch (IOException ioe) {

            }

            catch (Exception ex){

                String aaa = ex.getMessage();

            }
            finally {
                urlConnection.disconnect();
            }
        }


        catch (MalformedURLException mal) {

        }

        catch (IOException ioe) {

        }
        return result;

    }
}
