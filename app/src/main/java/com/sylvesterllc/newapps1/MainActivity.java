package com.sylvesterllc.newapps1;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.sylvesterllc.newapps1.Adapters.NewsAdapater;
import com.sylvesterllc.newapps1.Fragments.SettingsDialogFragment;
import com.sylvesterllc.newapps1.Loaders.NewsArticleLoader;
import com.sylvesterllc.newapps1.Models.NewsViewModel;
import com.sylvesterllc.newapps1.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity
{

    private String TAG = "HelpD";
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, item.toString());

        DialogFragment dialog = new SettingsDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog1");
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    protected void loadListeners() {

        txtSearchNews = findViewById(R.id.txtSearchNews);

        txtSearchNews.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // Log.d("HelpD", String.valueOf(hasFocus));

                if (!hasFocus) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

                    if (imm.isAcceptingText()) {
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                }
            }
        });

    }

    public void getNews(View view) {
        txtSearchNews = findViewById(R.id.txtSearchNews);

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        String temp = txtSearchNews.getText().toString();
        newsViewModel.updateSearchText(temp, mAdapter, this);
//        RecyclerView.Adapter aaa =   mRecycleView.getAdapter();
//        aaa.notify();

        mAdapter.notifyDataSetChanged();
    }


    private void setDefaults(ActivityMainBinding binding) {

        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        binding.setLifecycleOwner(this);

        newsViewModel.binding = binding;

        String aa = newsViewModel.searchText.toString();

        newsViewModel.getNewsArticles();

        binding.setModel(newsViewModel);

        mAdapter = new NewsAdapater(this, this, newsViewModel.newsArticlesList);

        mlayoutManager = new LinearLayoutManager(this);

        mRecycleView.setAdapter(mAdapter);

        mRecycleView.hasFixedSize();

        mRecycleView.setLayoutManager(mlayoutManager);

        NewsViewModel nvm = new NewsViewModel(getApplication());

        newsViewModel.loadNewsArticles("murder", new NewsViewModel(getApplication()).new Linker(mAdapter), this);


    }

}
