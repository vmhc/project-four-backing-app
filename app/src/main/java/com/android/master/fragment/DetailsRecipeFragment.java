package com.android.master.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.master.R;
import com.android.master.adapter.IngredientAdapter;
import com.android.master.models.Ingredient;
import com.android.master.models.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.master.activity.RecipeDetailActivity.TYPE_INGREDIENT;
import static com.android.master.activity.RecipeDetailActivity.TYPE_STEP;

public class DetailsRecipeFragment extends Fragment {

    private static final String ARG_TYPE = "ARG_TYPE";
    public static final String ARG_INGREDIENTS = "ARG_INGREDIENTS";
    private static final String ARG_STEPS = "ARG_STEPS";
    public static final String ARG_EMPTY = "ARG_EMPTY";

    @BindView(R.id.rv_ingredients)
    RecyclerView rv_ingredients;
    @BindView(R.id.cl_general_ingredients)
    ConstraintLayout cl_general_ingredients;
    @BindView(R.id.cl_general_steps)
    ConstraintLayout cl_general_steps;


    @BindView(R.id.main_player_view)
    PlayerView fragmentDetailsMainPlayerView;

    @BindView(R.id.step_instructions)
    TextView fragmentDetailsStepInstructions;

    private ArrayList<Ingredient> ingredients;
    private Step step;

    private IngredientAdapter ingredientAdapter;

    private OnFragmentInteractionListener mListener;

    SimpleExoPlayer player;

    public DetailsRecipeFragment() {
    }

    public static DetailsRecipeFragment createInstance(String type, ArrayList<Ingredient> ingredients, Step step) {
        DetailsRecipeFragment fragment = new DetailsRecipeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putParcelableArrayList(ARG_INGREDIENTS, ingredients);
        args.putParcelable(ARG_STEPS, step);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (Objects.requireNonNull(getArguments().getString(ARG_TYPE)).equals(TYPE_INGREDIENT)) {
                ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS);
            } else if (Objects.requireNonNull(getArguments().getString(ARG_TYPE)).equals(TYPE_STEP)) {
                step = getArguments().getParcelable(ARG_STEPS);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_recipe, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    private void initUI() {

        if (ingredients != null) {
            cl_general_ingredients.setVisibility(View.VISIBLE);
            cl_general_steps.setVisibility(View.GONE);
            rv_ingredients.setHasFixedSize(true);
            ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
            rv_ingredients.setAdapter(ingredientAdapter);
        } else if (step != null) {
            cl_general_ingredients.setVisibility(View.GONE);
            cl_general_steps.setVisibility(View.VISIBLE);
            fragmentDetailsStepInstructions.setText(step.getDescription());

            if (!step.getVideoURL().isEmpty()) {
                player = ExoPlayerFactory.newSimpleInstance(getContext());
                fragmentDetailsMainPlayerView.setPlayer(player);
                Uri mp4VideoUri = Uri.parse(step.getVideoURL());
                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                        Util.getUserAgent(getContext(), getString(R.string.app_name)));
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mp4VideoUri);
                // Prepare the player with the source.
                player.prepare(videoSource);
            }else{
                fragmentDetailsMainPlayerView.setVisibility(View.GONE);
            }
            // release player.
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);

    }
}
