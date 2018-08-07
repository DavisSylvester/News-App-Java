package com.sylvesterllc.newapps1.Repository;

import android.os.Debug;
import android.util.Log;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.sylvesterllc.newapps1.NewsData;

import java.util.List;

public class ApiRepository {
    private static final ApiRepository ourInstance = new ApiRepository();

    private final String API_PATH = "https://content.guardianapis.com/tags?q=android&api-key=e9e16519-7502-46af-b08f-47f5fdd4535f";

    public static ApiRepository getInstance() {
        return ourInstance;
    }

    private ApiRepository() {

    }


}
