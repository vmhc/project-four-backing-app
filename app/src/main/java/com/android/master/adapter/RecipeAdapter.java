package com.android.master.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.master.R;
import com.android.master.activity.RecipeActivity;
import com.android.master.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Recipe> recipes = new ArrayList<>();

    public RecipeAdapter() {
    }

    public void setRecipe(List<Recipe> recipes) {
        this.recipes.clear();
        this.recipes.addAll(recipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecipeViewHolder) holder).setData(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return (!recipes.isEmpty()) ? recipes.size() : 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_recipeName)
        TextView tv_recipeName;
        @BindView(R.id.cv_recipe)
        CardView cv_recipe;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Recipe recipe) {

            tv_recipeName.setText(recipe.getName());
            cv_recipe.setOnClickListener(v ->
                    itemView.getContext().startActivity(RecipeActivity.newActivityIntent(itemView.getContext(), recipe)));
        }
    }

}

