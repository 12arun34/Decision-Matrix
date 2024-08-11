package com.example.decisionmatrix;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;

public class DecisionMatrixApplication extends Application {

    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedViewModel = new ViewModelProvider.AndroidViewModelFactory(this).create(SharedViewModel.class);
    }

    public SharedViewModel getSharedViewModel() {
        return sharedViewModel;
    }
}

