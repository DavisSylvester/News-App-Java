package com.sylvesterllc.newapps1.Models;

public class ArticleTags {

    public String id = "";
    public String type  = "";
    public String webTitle = "";
    public String webUrl = "";
    public String apiUrl = "";
    public String bio = "";
    public String bylineImageUrl = "";
    public String firstName = "";
    public String lastName = "";
    public String authorFullName = "";

    public ArticleTags() {}

    public ArticleTags(String ID, String Type, String WebTitle, String WebUrl, String ApiUrl,
                       String Bio, String BylineImageUrl, String FirstName, String LastName) {

        id = ID;
        type = Type;
        webTitle = WebTitle;
        webUrl = WebUrl;
        apiUrl = ApiUrl;
        bio = Bio;
        bylineImageUrl = BylineImageUrl;
        firstName = FirstName;
        lastName = LastName;
        authorFullName = firstName + " " + lastName;
    }
}
