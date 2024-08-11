package com.example.decisionmatrix;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ArrayList<String>>> sortedOptions = new MutableLiveData<>();
    private MutableLiveData<ArrayList<String>> decisionNames = new MutableLiveData<>();
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "SharedPreferencesHelper";
    private static final String KEY_SORTED_OPTIONS = "sortedOptions";

    public void addSortedOptions(Context context, ArrayList<ArrayList<String>> options) {
        if(options!=null && !options.isEmpty()) {
            if (sortedOptions.getValue() == null) {
                sortedOptions.setValue(options);
            } else {
                ArrayList<ArrayList<String>> currentOptions = sortedOptions.getValue();
                currentOptions.addAll(options);
                sortedOptions.setValue(currentOptions);
            }
            SharedPreferencesHelper.saveSortedOptions(context, sortedOptions.getValue());
        }
    }
    public MutableLiveData<ArrayList<ArrayList<String>>> getSortedOptions() {
        return sortedOptions;
    }

    // for decsision ==================


    public void addDecisionNames(Context context, ArrayList<String> decision) {
        if(decision!=null && !decision.isEmpty()) {
            if (decisionNames.getValue() == null) {
                decisionNames.setValue(decision);
            } else {
                ArrayList<String> currentDecision = decisionNames.getValue();
                currentDecision.addAll(decision);
                decisionNames.setValue(currentDecision);
            }
            SharedPreferencesHelper.saveDecisionNames(context, decisionNames.getValue());
        }
    }

    public MutableLiveData<ArrayList<String>> getDecisionNames() {
        return decisionNames;
    }
    public void updateDecisionName(Context context, int position, String newName){
        System.out.println("ARUN ENTERS IN UPDATE FUNCTION");
        SharedPreferencesHelper.clearDecisionNames(context);
        Objects.requireNonNull(decisionNames.getValue()).set(position,newName);
        SharedPreferencesHelper.saveDecisionNames(context,decisionNames.getValue());
    }
    public void deleteDecision(Context context, int position){
        SharedPreferencesHelper.clearDecisionNames(context);
        SharedPreferencesHelper.clearSortedOptions(context);
        Objects.requireNonNull(sortedOptions.getValue()).remove(position);
        Objects.requireNonNull(decisionNames.getValue()).remove(position);

        SharedPreferencesHelper.saveDecisionNames(context,decisionNames.getValue());
        SharedPreferencesHelper.saveSortedOptions(context, sortedOptions.getValue());
    }
    public void clearSortedOptions(){
        ArrayList<ArrayList<String>> empty = new ArrayList<>();
        sortedOptions.setValue(empty);
    }
    public void clearDecisionNames(){
        ArrayList<String> empty = new ArrayList<>();
        decisionNames.setValue(empty);
    }
    // NO error GUnjais
}
