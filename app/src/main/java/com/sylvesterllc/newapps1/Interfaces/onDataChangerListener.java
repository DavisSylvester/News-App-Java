package com.sylvesterllc.newapps1.Interfaces;

import com.sylvesterllc.newapps1.Models.NewsArticle;

import java.sql.Array;
import java.util.ArrayList;

public interface onDataChangerListener {

    void onDataChange(ArrayList<NewsArticle> data);

}

