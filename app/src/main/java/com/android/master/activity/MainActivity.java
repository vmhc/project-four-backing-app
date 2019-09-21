package com.android.master.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.android.master.IdlingResource.SimpleIdlingResource;
import com.android.master.R;
import com.android.master.adapter.RecipeAdapter;
import com.android.master.interfaces.IMain;
import com.android.master.models.Recipe;
import com.android.master.services.UpdateWidgetIntentService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMain {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @BindView(R.id.rv_recipe)
    RecyclerView rv_recipe;

    private static MainViewModel vm;
    /*private ActivityMainBinding binding;*/

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getIdlingResource();
        init();
    }

    private void init() {
        //TODO detectar cuando es una tableta
        vm = ViewModelProviders.of(this).get(MainViewModel.class);
        vm.iMain = this;
        mIdlingResource.setIdleState(false);
        vm.obtainRecipe();
       // rv_recipe.setLayoutManager(new LinearLayoutManager(this));
        rv_recipe.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter();
        rv_recipe.setAdapter(recipeAdapter);
        /* binding = DataBindingUtil.setContentView(this, R.layout.activity_main);  binding.setActivity(this);*/
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    public void setRecipe(ArrayList<Recipe> recipes) {
        recipeAdapter.setRecipe(recipes);
        mIdlingResource.setIdleState(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateWidgetIntentService.startClearWidget(this);

    }
}
