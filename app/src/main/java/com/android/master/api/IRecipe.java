package com.android.master.api;

import com.android.master.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRecipe {

    @GET(ClienteHttp.BAKING)
    Call<ArrayList<Recipe>> getRecipe();
}
