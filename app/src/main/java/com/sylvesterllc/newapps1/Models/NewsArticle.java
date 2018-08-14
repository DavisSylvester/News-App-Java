package com.sylvesterllc.newapps1.Models;

import java.util.ArrayList;

public class NewsArticle {

    public String id = "";
    public String type  = "";
    public String sectionId = "";
    public String sectionName = "";
    public String webTitle = "";
    public String webUrl = "";
    public String apiUrl = "";
    public String description = "";
    public ArrayList<ArticleTags> tags = new ArrayList<>();



    public NewsArticle(){ }

    public NewsArticle(String ID, String Type, String SectionID, String SectionName,
                       String WebTitle, String WebUrl, String ApiUrl,
                       String desc, ArrayList<ArticleTags> tags){

        id = ID;
        type = Type;
        sectionId = SectionID;
        sectionName = SectionName;
        webTitle = WebTitle;
        webUrl = WebUrl;
        apiUrl = ApiUrl;
        description = desc;
        this.tags = tags;
    }
}
