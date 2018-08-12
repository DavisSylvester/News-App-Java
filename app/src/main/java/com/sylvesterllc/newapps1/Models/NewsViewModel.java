package com.sylvesterllc.newapps1.Models;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

// import com.google.gson.Gson;
import com.sylvesterllc.newapps1.Interfaces.onDataUpdateListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class NewsViewModel extends AndroidViewModel {

    private onDataUpdateListener listener;

    public MutableLiveData<String> searchText;
    public MutableLiveData<String> searchHintText;
    public MutableLiveData<ArrayList<NewsArticle>> newsArticlesList;



    public NewsViewModel(@NonNull Application application) {
        super(application);


        setDefaults();
    }

    private void setDefaults() {
        searchText = new MutableLiveData<>();
        searchHintText = new MutableLiveData<>();
        newsArticlesList = new MutableLiveData<>();


        searchText.setValue("computer");

        searchHintText.setValue("Enter New Search");

        // loadNewsArticles(searchText.toString(), new Linker());
    }

    public LiveData<ArrayList<NewsArticle>> getNewsArticles() {

        if (newsArticlesList == null) {
            newsArticlesList = new MutableLiveData();
            loadNewsArticlesInitially(searchText.toString());
        }
        return newsArticlesList;

    }

    private void loadNewsArticlesInitially(final String  search) {

        if (newsArticlesList != null && newsArticlesList.getValue() != null) {
            newsArticlesList.getValue().clear();
        }
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {

                    String searchString = searchText.getValue().toString();

                    String API_PATH =
                            String.format("https://content.guardianapis.com/tags?q=%s&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f", searchString);


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
                            // GuardApiData ad =  new Gson().fromJson(result, GuardApiData.class);


                            newsArticlesList.postValue(fromJsonString(result));


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

                        String today = ioe.getMessage();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    public void loadNewsArticles(final String  search,
                                  final onDataUpdateListener dataChange,
                                  final Activity context) {

        if (newsArticlesList != null && newsArticlesList.getValue() != null) {
            newsArticlesList.getValue().clear();
        }

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {

                    String searchString = searchText.getValue().toString();

                    String API_PATH =
                    String.format("https://content.guardianapis.com/tags?q=%s&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f", searchString);


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
                            // GuardApiData ad =  new Gson().fromJson(result, GuardApiData.class);

                            ArrayList<NewsArticle> articles = fromJsonString(result);

                            //ArrayList<NewsArticle> articles = ad.response.results;

                            newsArticlesList.postValue(articles);

                            if (articles.size() == 0) {

                            }

                            context.runOnUiThread(new Runnable() {
                                public void run()
                                {
                                    dataChange.onDataChange();
                                }
                            });



                            //adapter.notifyDataSetChanged();

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    public void setSearchText(String searchText) {

        this.searchText.setValue(searchText);

    }

    private ArrayList<NewsArticle> fromJsonString(String json) {

        ArrayList<NewsArticle> returnResults = new ArrayList<>();

        try {
            JSONObject myObj = new JSONObject(json);

            String response = myObj.getString("response");

            JSONObject tempResult = new JSONObject(response);

            JSONArray results = tempResult.getJSONArray("results");

            if (results.length() == 0) {
                return new ArrayList<>();

            }



            for (int i = 0; i < results.length(); i++) {

                JSONObject tempNA = results.getJSONObject(i);

                NewsArticle na = new NewsArticle();

                na.id = tempNA.getString("id");
                na.apiUrl = tempNA.getString("apiUrl");
                na.description = (tempNA.has("description")) ? tempNA.getString("description") : "";
                na.webUrl = tempNA.getString("webUrl");
                na.webTitle = tempNA.getString("webTitle");
                na.sectionName = tempNA.getString("sectionName");
                na.type = tempNA.getString("type");

                returnResults.add(na);

            }

            return returnResults;

        }

        catch(Exception ex) {

            String aaa = ex.getMessage();

        }
return returnResults;
    }

    public void updateSearchText(String value, RecyclerView.Adapter adapter, Activity context) {

        searchText.setValue(value);
        loadNewsArticles(searchText.toString(), new Linker(adapter), context);

    }

    public class Linker implements onDataUpdateListener {

        public RecyclerView.Adapter adapter;

        public Linker(RecyclerView.Adapter apt) {
            adapter = apt;
        }

        @Override
        public void onDataChange() {
            try {
                adapter.notifyDataSetChanged();
                Log.d("HELPD", "adapter Data set has changed from Linker class");
            }
            catch(IndexOutOfBoundsException iox) {

            }
        }
    }


}
