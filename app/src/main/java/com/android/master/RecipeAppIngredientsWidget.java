package com.android.master;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.master.activity.MainActivity;
import com.android.master.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeAppIngredientsWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appWidget_text_ingredients, context.getString(R.string.appwidget_text_select_recipe));

        views.setOnClickPendingIntent(R.id.appwidget_text, getPendingIntent(context));

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }


    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String recipeName, ArrayList<Ingredient> ingredients) {

        CharSequence widgetText = context.getString(R.string.recipe_name_placeholder, recipeName);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_app_ingredients_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        StringBuilder stringBuilderIngredients = new StringBuilder();

        for (Ingredient ingredient : ingredients) {

            stringBuilderIngredients.append(context.getString(R.string.ingredient_name_placeholder, ingredient.getIngredient(), ingredient.getQuantity().toString(), ingredient.getMeasure()));
        }

        views.setTextViewText(R.id.appWidget_text_ingredients, stringBuilderIngredients);

        views.setOnClickPendingIntent(R.id.appwidget_text, getPendingIntent(context));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateDataIngredientList(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, String recipeName, ArrayList<Ingredient> ingredients) {

        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId, recipeName, ingredients);
        }
    }

    public static void clearDataIngredientList(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static PendingIntent getPendingIntent(Context context) {

        Intent intent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context, 0, intent, 0);

    }
}

