package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class FactorsPriority extends AppCompatActivity {
    private TableLayout tableLayout;
    private ArrayList<String> selectedFactors;
    private ArrayList<String> optionsList;
    private Button nextButton;
    private ArrayList<String> factorPriorities; // Declare as a global variable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factors_priority);
        nextButton = findViewById(R.id.nextButtonFP);
        // Get the selected factors from the intent
        selectedFactors = getIntent().getStringArrayListExtra("selectedFactors");
        optionsList = getIntent().getStringArrayListExtra("optionsList");
        // Initialize TableLayout
        tableLayout = findViewById(R.id.tableLayout);
        factorPriorities = new ArrayList<>(); // Initialize the global variable

        // setting navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); // Enable the back button

        Drawable navIcon = toolbar.getNavigationIcon();

        // Set the color filter to white
        if (navIcon != null) {
            navIcon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Navigate back when the back button is clicked
            }
        });
        // Add rows for each selected factor
        for (int i = 0; i < selectedFactors.size(); i++) {
            addFactorRow(i + 1, selectedFactors.get(i));
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainPriority activity and pass the selected factors, options list, and factor priorities as extras
                Intent intent = new Intent(FactorsPriority.this, MainPriority.class);
                intent.putStringArrayListExtra("selectedFactors", selectedFactors);
                intent.putStringArrayListExtra("optionsList", optionsList);
                if(factorPriorities.isEmpty()){
                    System.out.println("Arun kumar ji , You are passing empty factorPriorities");
                }
                else{
                    System.out.println("Arun kumar you are passing correct factorPriorities");
                }
                intent.putStringArrayListExtra("factorPriorities", factorPriorities);
                startActivity(intent);
            }
        });
    }

    // Method to add a row for a selected factor
    private void addFactorRow(int serialNumber, String factorName) {
        // Inflate the row layout
        LayoutInflater inflater = LayoutInflater.from(this);
        TableRow row = (TableRow) inflater.inflate(R.layout.row_factor_priority, tableLayout, false);

        // Find TextView and Spinner in the row layout
        TextView tvSerialNumber = row.findViewById(R.id.tvSerialNumber);
        TextView tvFactorName = row.findViewById(R.id.tvFactorName);
        Spinner spinnerPriority = row.findViewById(R.id.spinnerPriority);

        // Set serial number and factor name
        tvSerialNumber.setText(String.valueOf(serialNumber));
        tvFactorName.setText(factorName);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.factor_values, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        // Set listener to update factorPriorities when a priority is selected
        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update the factorPriorities list with the selected priority
                String priority = parentView.getItemAtPosition(position).toString();
                factorPriorities.set(serialNumber - 1, priority); // Update the priority for the corresponding factor
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle case when nothing is selected
            }
        });
        // Add the row to the table layout
        tableLayout.addView(row);
        factorPriorities.add("1");
    }


}