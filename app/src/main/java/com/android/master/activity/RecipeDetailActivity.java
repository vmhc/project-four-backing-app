package com.android.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.master.R;
import com.android.master.fragment.DetailsRecipeFragment;
import com.android.master.models.Ingredient;
import com.android.master.models.Step;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String KEY_INGREDIENTS = "KEY_INGREDIENTS";
    public static final String KEY_STEP = "KEY_STEP";
    public static final String TYPE_INGREDIENT = "TYPE_INGREDIENT";
    public static final String TYPE_STEP = "TYPE_STEP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ArrayList<Ingredient> ingredients = getIntent().getParcelableArrayListExtra(KEY_INGREDIENTS);
        Step step = getIntent().getParcelableExtra(KEY_STEP);

        if (ingredients != null) {
            initIngredient(getIntent(), ft);
            toolbar.setTitle(getString(R.string.ingredients));
        } else if (step != null) {
            initStep(getIntent(), ft);
            toolbar.setTitle(step.getShortDescription());
        } else {
            finish();
        }
        setSupportActionBar(toolbar);
    }

    private void initIngredient(Intent intent, FragmentTransaction ft) {
        ft.replace(R.id.detail_recipe, DetailsRecipeFragment.createInstance(TYPE_INGREDIENT, intent.getParcelableArrayListExtra(KEY_INGREDIENTS), null));
        ft.commit();
    }

    private void initStep(Intent intent, FragmentTransaction ft) {
        ft.replace(R.id.detail_recipe, DetailsRecipeFragment.createInstance(TYPE_STEP, null, intent.getParcelableExtra(KEY_STEP)));
        ft.commit();
    }

    public static Intent newActivityIntent(Context ctx, ArrayList<Ingredient> ingredients) {
        Intent intent = new Intent(ctx, RecipeDetailActivity.class);
        intent.putParcelableArrayListExtra(KEY_INGREDIENTS, ingredients);
        return intent;
    }

    public static Intent newActivityIntent(Context ctx, Step step) {
        Intent intent = new Intent(ctx, RecipeDetailActivity.class);
        intent.putExtra(KEY_STEP, step);
        return intent;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
