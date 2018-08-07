package com.sylvesterllc.newapps1.Infrastructure;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class NewsViewModelFactory extends  AndroidViewModel {

    private RecyclerView.Adapter adapter;

    public NewsViewModelFactory(@NonNull Application application, RecyclerView.Adapter adapter) {
        super(application);

        this.adapter = adapter;
    }
}
