package com.android.master.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.master.R;
import com.android.master.activity.RecipeViewModel;
import com.android.master.adapter.StepsAdapter;
import com.android.master.interfaces.IMasterCallbacks;
import com.android.master.models.Recipe;
import com.android.master.services.UpdateWidgetIntentService;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.master.activity.RecipeActivity.KEY_RECIPE;

public class MasterRecipeFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";

    @BindView(R.id.btn_ingredients)
    Button btn_ingredients;
    @BindView(R.id.rv_step)
    RecyclerView rv_step;

    private Recipe recipe;

    private IMasterCallbacks iMasterCallbacks;

    private RecipeViewModel vm;

    public MasterRecipeFragment() {
    }

    public static MasterRecipeFragment createInstance(Intent intent) {
        MasterRecipeFragment fragment = new MasterRecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, intent.getParcelableExtra(KEY_RECIPE));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARG_PARAM1);

            UpdateWidgetIntentService.startUpdateWidget(requireContext(), recipe);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_recipe, container, false);
        ButterKnife.bind(this, view);
        vm = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecipeViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iMasterCallbacks = (IMasterCallbacks) getActivity();

    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {
        Recipe r = vm.recipe;
        rv_step.setHasFixedSize(true);
        StepsAdapter stepsAdapter = new StepsAdapter(recipe.getSteps(), iMasterCallbacks);
        rv_step.setAdapter(stepsAdapter);

        btn_ingredients.setText(getString(R.string.number_ingredients, String.valueOf(recipe.getIngredients().size())));
        btn_ingredients.setOnClickListener(v -> iMasterCallbacks.showIngredient(recipe.getIngredients()));
    }

}
