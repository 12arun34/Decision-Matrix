package com.example.decisionmatrix;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class SharedPreferencesHelper {

    private static final String KEY_SORTED_OPTIONS = "sortedOptions";
    private static final String KEY_DECISION_NAMES = "decisionNames";

    public static void saveSortedOptions(Context context, ArrayList<ArrayList<String>> sortedOptions) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        // Retrieve existing sorted options
//        ArrayList<ArrayList<String>> existingSortedOptions = getSortedOptions(context);

        // Merge existing and new sorted options
//        if (existingSortedOptions != null) {
//            existingSortedOptions.addAll(sortedOptions);
//        } else {
//            existingSortedOptions = sortedOptions;
//        }

        // Convert the combined list to JSON
        Gson gson = new Gson();
        String json = gson.toJson(sortedOptions);

        // Save the combined list back to SharedPreferences
        editor.putString(KEY_SORTED_OPTIONS, json);
        editor.apply();
    }
    public static ArrayList<ArrayList<String>> getSortedOptions(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(KEY_SORTED_OPTIONS, "");

        if (json.isEmpty()) {
            return new ArrayList<>(); // Return an empty ArrayList if the JSON string is empty
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<ArrayList<ArrayList<String>>>() {}.getType());
        }
    }

    public static void clearSortedOptions(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_SORTED_OPTIONS);
        editor.apply();
    }
    // for Decision names =======================
    // Method to save decision names
    public static void saveDecisionNames(Context context, ArrayList<String> decisionNames) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(decisionNames);
        editor.putString(KEY_DECISION_NAMES, json);
        editor.apply();
    }

    // Method to retrieve decision names
    public static ArrayList<String> getDecisionNames
(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String json = preferences.getString(KEY_DECISION_NAMES, "");
        if (json.isEmpty()) {
            // If no decision names are found, return an empty list
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            return gson.fromJson(json, new TypeToken<ArrayList<String>>() {}.getType());
        }
    }
    public static void clearDecisionNames(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(KEY_DECISION_NAMES);
        editor.apply();
    }
    // NO error GUnjais
}

