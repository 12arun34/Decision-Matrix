package com.example.decisionmatrix.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.decisionmatrix.ChoosingOptions;
import com.example.decisionmatrix.DecisionAdapter;
import com.example.decisionmatrix.DecisionMatrixApplication;
import com.example.decisionmatrix.R;
import com.example.decisionmatrix.SharedViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        System.out.println("Inside Home Fragment1");

        // Find the RecyclerView
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        if (recyclerView == null) {
            System.out.println("Failed to find RecyclerView");
            return root; // Exit early if RecyclerView is not found
        }
        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Inside HomeFragment onCreateView() method
//        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        DecisionMatrixApplication decisionMatrixApp = (DecisionMatrixApplication) requireActivity().getApplication();
        SharedViewModel viewModel = decisionMatrixApp.getSharedViewModel();

        if (viewModel == null) {
            System.out.println("Failed to initialize ViewModel");
            return root; // Exit early if ViewModel is not initialized
        }
        // Get the sorted options directly
        ArrayList<ArrayList<String>> sortedOptions = viewModel.getSortedOptions().getValue();
        ArrayList<String> decisionNames = viewModel.getDecisionNames().getValue();
        if (sortedOptions != null && !sortedOptions.isEmpty()) {
            // Update the adapter with the new sorted options
            System.out.println("Fetched data successfully");
            DecisionAdapter decisionAdapter = new DecisionAdapter(sortedOptions, decisionNames, requireActivity(), viewModel);
            recyclerView.setAdapter(decisionAdapter);
        } else {
            System.out.println("Sorted options data is null or empty");
        }
        // Find the "Take Decision" button
        Button takeDecision = root.findViewById(R.id.takeDecision);
        if (takeDecision == null) {
            System.out.println("Failed to find 'Take Decision' button");
            return root; // Exit early if "Take Decision" button is not found
        }

        // Set click listener for "Take Decision" button
        takeDecision.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChoosingOptions.class);
            startActivity(intent);
        });

        System.out.println("Inside Home Fragment6");

        return root;
    }
}
