package com.sylvesterllc.newapps1.Models;

import java.util.ArrayList;

public class GuardianResponse {

    public String status  = "";
    public String userTier = "";
    public int total = 0;
    public int startIndex = 1;
    public int pageSize = 1;
    public int currentPage = 1;
    public int pages = 0;
    public ArrayList<NewsArticle> results = new ArrayList<>();


}
