package com.android.master.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.android.master.RecipeAppIngredientsWidget;
import com.android.master.models.Ingredient;
import com.android.master.models.Recipe;

import java.util.ArrayList;

/**
 * Intent services that update widget information.
 */
public class UpdateWidgetIntentService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.android.master.services.action.update_widget";

    public static final String ACTION_CLEAR_WIDGET_INGREDIENTS = "com.android.master.services.action.clear_widget_ingredients";

    private static final String KEY_RECIPE_INGREDIENTS_LIST = "recipe_ingredients_list";

    private static final String KEY_RECIPE_NAME = "recipe_name";

    private static final String SERVICE_NAME = "UpdateWidgetIntentService";

    public UpdateWidgetIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_WIDGET.equals(action)) {

                String recipeName = intent.getStringExtra(KEY_RECIPE_NAME);
                ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra(KEY_RECIPE_INGREDIENTS_LIST);

                handleUpdateWidget(recipeName, ingredients);
            } else if (ACTION_CLEAR_WIDGET_INGREDIENTS.equals(action)) {

                handleClearWidget();
            }
        }

    }

    private void handleUpdateWidget(String recipeName, ArrayList<Ingredient> ingredients) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeAppIngredientsWidget.class));

        RecipeAppIngredientsWidget.updateDataIngredientList(this, appWidgetManager, appWidgetIds, recipeName, ingredients);
    }

    private void handleClearWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeAppIngredientsWidget.class));

        RecipeAppIngredientsWidget.clearDataIngredientList(this, appWidgetManager, appWidgetIds);
    }


    public static void startUpdateWidget(Context context, Recipe recipe) {

        Intent intent = new Intent(context, UpdateWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);

        intent.putExtra(KEY_RECIPE_NAME, recipe.getName());
        intent.putParcelableArrayListExtra(KEY_RECIPE_INGREDIENTS_LIST, recipe.getIngredients());

        context.startService(intent);
    }


    public static void startClearWidget(Context context) {

        Intent intent = new Intent(context, UpdateWidgetIntentService.class);
        intent.setAction(ACTION_CLEAR_WIDGET_INGREDIENTS);
        context.startService(intent);
    }

}
