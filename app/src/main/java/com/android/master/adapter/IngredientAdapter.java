package com.android.master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.android.master.R;
import com.android.master.models.Ingredient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Ingredient> ingredients = new ArrayList<>();
    private Context ctx;

    public IngredientAdapter(Context ctx, ArrayList<Ingredient> ingredients) {
        this.ctx = ctx;
        this.ingredients.addAll(ingredients);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((IngredientViewHolder) holder).setData(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return (!ingredients.isEmpty()) ? ingredients.size() : 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.et_ingredient)
        EditText et_ingredient;
        @BindView(R.id.til_ingredients)
        TextInputLayout til_ingredients;
        @BindView(R.id.gl)
        Guideline gl;
        @BindView(R.id.et_quanty)
        EditText et_quanty;
        @BindView(R.id.til_quanty)
        TextInputLayout til_quanty;
        @BindView(R.id.et_measure)
        EditText et_measure;
        @BindView(R.id.til_measure)
        TextInputLayout til_measure;
        @BindView(R.id.cv_ingredient)
        CardView cv_ingredient;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Ingredient ingredient) {
            et_measure.setText(ingredient.getMeasure());
            et_ingredient.setText(ingredient.getIngredient());
            et_quanty.setText(ingredient.getQuantity().toString());
        }
    }

}

