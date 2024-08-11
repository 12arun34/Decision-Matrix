package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Objects;

public class MainPriority extends AppCompatActivity {
    private ArrayList<String> selectedFactors;
    private ArrayList<String> optionsList;
    private ArrayList<String> factorPriorities;
    private int numberOfFactors = 5; // Example number of factors
    private int numberOfOptions = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_priority);
        LinearLayout mainLayout = findViewById(R.id.main_layout);
        // Get the selected factors from the intent
        selectedFactors = getIntent().getStringArrayListExtra("selectedFactors");
        optionsList = getIntent().getStringArrayListExtra("optionsList");
        factorPriorities = getIntent().getStringArrayListExtra("factorPriorities");
        numberOfFactors = selectedFactors.size();
        numberOfOptions = optionsList.size();
        // setting navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar4);
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
        // Add LinearLayouts for each factor
        for (int i = 0; i < numberOfFactors; i++) {
            LinearLayout factorLayout = new LinearLayout(this);
            factorLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            factorLayout.setOrientation(LinearLayout.VERTICAL);

            // Add TextView for factor name
            TextView factorNameTextView = new TextView(this);
            factorNameTextView.setText(selectedFactors.get(i));
            factorNameTextView.setTextSize(20); // Set text size to 20sp
            factorNameTextView.setTextColor(Color.BLACK); // Set text color to black
            factorNameTextView.setPadding(16, 16, 16, 16); // Set padding
            factorNameTextView.setGravity(Gravity.CENTER); // Set text gravity to center
            // Set other attributes as needed

            factorLayout.addView(factorNameTextView);

            // Add TableLayout for options and priorities
            TableLayout tableLayout = new TableLayout(this);
            tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            // Arun row copying
            for (int j = 0; j < numberOfOptions; j++) {
                addFactorRow( tableLayout, j + 1, optionsList.get(j));
            }

            factorLayout.addView(tableLayout);
            mainLayout.addView(factorLayout);
        }

        // Add submit button
        Button submitButton = new Button(this);
        submitButton.setText("Submit");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;
        submitButton.setLayoutParams(layoutParams);
        mainLayout.addView(submitButton);

// Set OnClickListener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize the priority matrix
                String[][] priorityMatrix = new String[numberOfOptions][numberOfFactors];

                // Iterate through each factor layout
                for (int i = 0; i < numberOfFactors; i++) {
                    LinearLayout factorLayout = (LinearLayout) mainLayout.getChildAt(i);
                    TableLayout tableLayout = (TableLayout) factorLayout.getChildAt(1); // Index 0 is the factor name TextView

                    // Iterate through each row (option) in the TableLayout
                    for (int j = 0; j < tableLayout.getChildCount(); j++) {
                        TableRow row = (TableRow) tableLayout.getChildAt(j);
                        Spinner spinnerPriority = (Spinner) row.getChildAt(2); // Index 0 is the serial number TextView, index 1 is the factor name TextView

                        // Get the selected priority from the spinner
                        String priority = spinnerPriority.getSelectedItem().toString();

                        // Store the priority in the priority matrix
                        priorityMatrix[j][i] = priority;
                    }
                }

                // Pass the priority matrix to the next activity
                Intent intent = new Intent(MainPriority.this, Result.class);
//                intent.putExtra("priorityMatrix", priorityMatrix);
                Bundle bundle = new Bundle();
                bundle.putSerializable("priorityMatrix", priorityMatrix);

                // Pass the serialized array to the intent
                intent.putExtras(bundle);
                intent.putStringArrayListExtra("optionsList", optionsList);
                if(factorPriorities.isEmpty()){
                    System.out.println("Arun ji , You are passing empty factorPriorities");
                }
                else{
                    System.out.println("Arun you are passing correct factorPriorities");
                }
                intent.putStringArrayListExtra("factorPriorities", factorPriorities);
                startActivity(intent);
            }
        });

    }

    private void addFactorRow(TableLayout tableLayout , int serialNumber, String factorName) {
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

        // Add the row to the table layout
        tableLayout.addView(row);
    }
}