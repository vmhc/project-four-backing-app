package com.android.master.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteHttp {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    static final String BAKING = "baking.json";
    private static Retrofit retrofit;
    private static IRecipe iRecipe;

    public ClienteHttp() {
    }

    static {
        setupRetrofit();
        setupResources();
    }

    private static void setupResources() {
        iRecipe = retrofit.create(IRecipe.class);
    }

    private static void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL).build();
    }

    public static IRecipe getIRecipe() {
        return iRecipe;
    }


}
