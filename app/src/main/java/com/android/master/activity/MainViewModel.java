package com.android.master.activity;

import androidx.lifecycle.ViewModel;

import com.android.master.api.ClienteHttp;
import com.android.master.api.IRecipe;
import com.android.master.interfaces.IMain;
import com.android.master.models.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private IRecipe iRecipe = ClienteHttp.getIRecipe();
    IMain iMain;

    void obtainRecipe() {
        iRecipe.getRecipe().enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                iMain.setRecipe(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                call.toString();
            }
        });
    }
}
