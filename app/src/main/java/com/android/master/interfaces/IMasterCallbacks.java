package com.android.master.interfaces;

import com.android.master.models.Ingredient;
import com.android.master.models.Step;

import java.util.ArrayList;

public interface IMasterCallbacks {

    void showIngredient(ArrayList<Ingredient> ingredients);

    void showStep(Step step);
}
