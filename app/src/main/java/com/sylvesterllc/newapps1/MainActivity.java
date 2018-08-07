package com.sylvesterllc.newapps1;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.sylvesterllc.newapps1.Adapters.NewsAdapater;
import com.sylvesterllc.newapps1.Loaders.NewsArticleLoader;
import com.sylvesterllc.newapps1.Models.NewsViewModel;
import com.sylvesterllc.newapps1.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>
{

    private RecyclerView mRecycleView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    private EditText txtSearchNews;

    private NewsViewModel newsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        setContentView(R.layout.activity_main);
        mRecycleView = findViewById(R.id.rvNewsList);

        setDefaults(binding);

        loadListeners();
    }

    protected void loadListeners() {

        txtSearchNews = findViewById(R.id.txtSearchNews);

        txtSearchNews.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String temp = txtSearchNews.getText().toString();
                    newsViewModel.updateSearchText(temp, mAdapter);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }
        });

    }


    private void setDefaults(ActivityMainBinding binding) {

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        binding.setLifecycleOwner(this);

        String aa = newsViewModel.searchText.toString();

        newsViewModel.getNewsArticles();

        binding.setModel(newsViewModel);

        mAdapter = new NewsAdapater(this, this, newsViewModel.newsArticlesList);

        mlayoutManager = new LinearLayoutManager(this);

        mRecycleView.setAdapter(mAdapter);

        mRecycleView.hasFixedSize();

        mRecycleView.setLayoutManager(mlayoutManager);

    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new NewsArticleLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
