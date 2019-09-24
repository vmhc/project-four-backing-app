package com.android.master.activity;

import androidx.lifecycle.ViewModel;

import com.android.master.models.Ingredient;
import com.android.master.models.Recipe;

import java.util.ArrayList;

public class RecipeViewModel extends ViewModel {

    public ArrayList<Ingredient> ingredients;
    public Recipe recipe;
}
