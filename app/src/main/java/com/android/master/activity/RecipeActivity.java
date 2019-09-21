package com.android.master.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.android.master.R;
import com.android.master.fragment.DetailsRecipeFragment;
import com.android.master.fragment.MasterRecipeFragment;
import com.android.master.interfaces.IMasterCallbacks;
import com.android.master.models.Ingredient;
import com.android.master.models.Recipe;
import com.android.master.models.Step;

import java.util.ArrayList;

import static com.android.master.activity.RecipeDetailActivity.TYPE_INGREDIENT;
import static com.android.master.activity.RecipeDetailActivity.TYPE_STEP;
import static com.android.master.fragment.DetailsRecipeFragment.ARG_EMPTY;

public class RecipeActivity extends AppCompatActivity implements MasterRecipeFragment.OnFragmentInteractionListener, DetailsRecipeFragment.OnFragmentInteractionListener, IMasterCallbacks {

    public static final String KEY_RECIPE = "KEY_RECIPE";
    private RecipeViewModel vm;
    private IMasterCallbacks iMasterCallbacks;
    private Toolbar toolbar;
    private Boolean isMasterDetail = false;

    @Override
    protected void onCreate(Bundle arg) {
        super.onCreate(arg);
        iMasterCallbacks = this;
        setContentView(R.layout.activity_recipe);

        vm = ViewModelProviders.of(this).get(RecipeViewModel.class);
        vm.recipe = getIntent().getParcelableExtra(KEY_RECIPE);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace);
        toolbar.setTitle(vm.recipe.getName());
        setSupportActionBar(toolbar);


        if (findViewById(R.id.children_recipe) != null) {
            isMasterDetail = true;
        }
        init(getIntent());

    }

    private void init(Intent intent) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.master_recipe, MasterRecipeFragment.createInstance(intent));
        ft.commit();

        if (isMasterDetail) {
            FragmentTransaction ftc = getSupportFragmentManager().beginTransaction();
            ftc.replace(R.id.children_recipe, DetailsRecipeFragment.createInstance(ARG_EMPTY, null, null));
            ftc.commit();
        }
    }

    public static Intent newActivityIntent(Context ctx, Recipe recipe) {
        Intent intent = new Intent(ctx, RecipeActivity.class);
        intent.putExtra(KEY_RECIPE, recipe);
        return intent;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void showIngredient(ArrayList<Ingredient> ingredients) {
        if (isMasterDetail) {
            FragmentTransaction ftc = getSupportFragmentManager().beginTransaction();
            ftc.replace(R.id.children_recipe, DetailsRecipeFragment.createInstance(TYPE_INGREDIENT, ingredients, null));
            ftc.commit();
        } else {
            startActivity(RecipeDetailActivity.newActivityIntent(getBaseContext(), ingredients));
        }
    }

    @Override
    public void showStep(Step step) {
        if (isMasterDetail) {
            FragmentTransaction ftc = getSupportFragmentManager().beginTransaction();
            ftc.replace(R.id.children_recipe, DetailsRecipeFragment.createInstance(TYPE_STEP, null, step));
            ftc.commit();
        } else {
            startActivity(RecipeDetailActivity.newActivityIntent(this, step));
        }

    }
}
