package com.sylvesterllc.newapps1.Models;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.sylvesterllc.newapps1.Interfaces.onDataUpdateListener;
import com.sylvesterllc.newapps1.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
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
    public ActivityMainBinding binding;

    private static  boolean isInitialLoad = true;

    public MutableLiveData<String> searchText;
    public MutableLiveData<String> searchHintText;
    public MutableLiveData<ArrayList<NewsArticle>> newsArticlesList;
    public MutableLiveData<Integer> showNoData = new MutableLiveData<>();
    public MutableLiveData<Integer> showRecyclerView = new MutableLiveData<>();
    public MutableLiveData<String> noRecordText = new MutableLiveData<>();


    public NewsViewModel(@NonNull Application application) {
        super(application);

        setDefaults();
    }

    private void setDefaults() {
        searchText = new MutableLiveData<>();
        searchHintText = new MutableLiveData<>();
        newsArticlesList = new MutableLiveData<>();
        showNoData.setValue(View.VISIBLE);
        showRecyclerView.setValue(View.VISIBLE);

        String tempSearch = PreferenceManager.getDefaultSharedPreferences(getApplication())
                .getString("FirstName", "");

        searchText.setValue((tempSearch == "") ? "computer" : tempSearch);

        searchHintText.setValue("Enter New Search");
    }

    public void setShowNoData(int val) {
        showNoData.setValue(val);
    }

    public int getShowNoData() {
        return showNoData.getValue();

    }

    public LiveData<ArrayList<NewsArticle>> getNewsArticles() {

        if (newsArticlesList == null) {
            newsArticlesList = new MutableLiveData();


            loadNewsArticlesInitially(searchText.toString());
        }
        return newsArticlesList;

    }

    private void loadNewsArticlesInitially(final String search) {

        if (newsArticlesList != null && newsArticlesList.getValue() != null) {
            newsArticlesList.getValue().clear();
        }
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String searchString = searchText.getValue().toString();

                    // Get Preference Text
                    SharedPreferences sharedPref = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
                    String tempResult = sharedPref.getString("FirstName", "");

                    String API_PATH =
                            String.format(
                                    "https://content.guardianapis.com/search?show-tags=contributor&q=%s&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f",
                                    (tempResult.length() == 0) ? searchString : tempResult);


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

                            newsArticlesList.postValue(fromJsonString(result));

                            Log.d("HELPD", fromJsonString(result).toString());

                        } catch (IOException ioe) {

                        } catch (Exception ex) {

                            String aaa = ex.getMessage();

                        } finally {
                            urlConnection.disconnect();
                        }
                    } catch (MalformedURLException mal) {

                    } catch (IOException ioe) {

                        String today = ioe.getMessage();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();


    }

    public void loadNewsArticles(final String search,
                                 final onDataUpdateListener dataChange,
                                 final Activity context) {

        if (newsArticlesList != null && newsArticlesList.getValue() != null) {
            newsArticlesList.getValue().clear();
        }

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo.isConnected()) {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        String searchString = searchText.getValue().toString();
                        String searchTextResult = "";

                        if (isInitialLoad) {

                            isInitialLoad = false;

                            // Get Preference Text
                            SharedPreferences sharedPref = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
                            String tempResult = sharedPref.getString("FirstName", "");

                            searchTextResult = (tempResult.length() == 0) ? searchString : tempResult;
                        }
                        else {
                            searchTextResult = searchString;
                        }


                        String API_PATH =
                                String.format("https://content.guardianapis.com/search?show-tags=contributor&q=%s&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f",
                                        searchTextResult);

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

                                ArrayList<NewsArticle> articles = fromJsonString(result);

                                newsArticlesList.postValue(articles);

                                context.runOnUiThread(new Runnable() {
                                    public void run() {
                                        dataChange.onDataChange();
                                        if (newsArticlesList.getValue().size() == 0) {
                                            noRecordText.setValue("No Articles Found!  Please Enter a new Search Topic and click 'Get News' ");
                                        } else {
                                            noRecordText.setValue("");
                                        }

                                    }
                                });


                                //adapter.notifyDataSetChanged();

                            } catch (IOException ioe) {

                            } catch (Exception ex) {

                                String aaa = ex.getMessage();

                            } finally {
                                urlConnection.disconnect();
                            }
                        } catch (MalformedURLException mal) {

                        } catch (IOException ioe) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        }
        context.getSharedPreferences("deaths", Context.MODE_PRIVATE).getAll();
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
                na.webTitle = (tempNA.has("webTitle")) ? tempNA.getString("webTitle") : "";
                na.sectionName = tempNA.getString("sectionName");
                na.type = tempNA.getString("type");
                na.webPublicationDate = (tempNA.has("webPublicationDate")) ? tempNA.getString("webPublicationDate") : "";

                na.tags = getTags(tempNA);


                returnResults.add(na);

            }

            return returnResults;

        } catch (Exception ex) {

            String aaa = ex.getMessage();

        }
        return returnResults;
    }

    private ArrayList<ArticleTags> getTags(JSONObject tempNA) {

        if (!tempNA.has("tags")) {
            return new ArrayList<>();
        }

        ArrayList<ArticleTags> tags = new ArrayList<>();

        try {
            JSONArray array = tempNA.getJSONArray("tags");

            for (int i = 0; i < array.length(); i++) {

                JSONObject obj = array.getJSONObject(i);

                ArticleTags tag = new ArticleTags();
                tag.firstName = (obj.has("firstName")) ? obj.getString("firstName") : "";
                tag.lastName = (obj.has("lastName")) ? obj.getString("lastName") : "";
                tag.bylineImageUrl = (obj.has("bylineImageUrl")) ? obj.getString("bylineImageUrl") : "";
                tag.apiUrl = (obj.has("apiUrl")) ? obj.getString("apiUrl") : "";
                tag.bio = (obj.has("bio")) ? obj.getString("bio") : "";
                tag.id = (obj.has("id")) ? obj.getString("id") : "";
                tag.type = (obj.has("type")) ? obj.getString("type") : "";
                tag.webTitle = (obj.has("webTitle")) ? obj.getString("webTitle") : "";
                tag.webUrl = (obj.has("webUrl")) ? obj.getString("webUrl") : "";
                tag.authorFullName = (obj.has("firstName") && obj.has("lastName")) ? tag.firstName + " " + tag.lastName : "";
                tags.add(tag);
            }


        } catch (JSONException jsone) {

        } catch (Exception e) {

        }

        return tags;
    }

    public void updateSearchText(String value, RecyclerView.Adapter adapter, Activity context) {

        searchText.setValue(value);
        loadNewsArticles(searchText.toString(), new Linker(adapter), context);

    }


    public class Linker implements onDataUpdateListener {

        public RecyclerView.Adapter adapter;
        private Context context;

        public Linker(RecyclerView.Adapter apt) {
            adapter = apt;
            this.context = context;
        }

        @Override
        public void onDataChange() {
            try {
                adapter.notifyDataSetChanged();

                if (newsArticlesList.getValue() != null && newsArticlesList.getValue().size() == 0) {
                    int aaaa = newsArticlesList.getValue().size();


                    noRecordText.setValue("No Articles Found");

                    binding.notifyChange();

                } else {
                    noRecordText.setValue("");

                    binding.notifyChange();
                }


                Log.d("HELPD", "adapter Data set has changed from Linker class");
                Log.d("HELPD", "No Data " + showNoData);
            } catch (IndexOutOfBoundsException iox) {

                String aaaa = iox.getMessage();

            } catch (Exception iox) {

                String aaaa = iox.getMessage();

            }
        }
    }


}
