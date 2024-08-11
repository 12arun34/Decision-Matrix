package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.graphics.Color;

public class Result extends AppCompatActivity {
    private ArrayList<String> optionsList;
    private ArrayList<String> factorPriorities;
    private String[][] priorityMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        System.out.println("Entered in result layout");


        // Get intent extras and perform null checks
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            optionsList = intent.getStringArrayListExtra("optionsList");
            factorPriorities = intent.getStringArrayListExtra("factorPriorities");
            priorityMatrix = (String[][]) extras.getSerializable("priorityMatrix");
            // Log print to check success till here
            System.out.println("Arun retrieving done successfully");

            // Check if priorityMatrix is not null
            if (priorityMatrix != null) {
                // Check if optionsList and factorPriorities are not null
                if (optionsList != null && factorPriorities != null) {
                    // Calculate scores and sort optionsList
                    ArrayList<String> sortedOptions = calculateScoresAndSort();
                    // Log print to check sorted Options list
                    for (String option : sortedOptions) {
                        System.out.println(option);
                    }
                    // Display the sorted optionsList in the TableLayout
                    displaySortedOptions(sortedOptions);
                    // Reverse the sorted options list
                    Collections.reverse(sortedOptions);

                    // Initialize button and set OnClickListener
                    Button goToHomeButton = findViewById(R.id.goToHomeButton);
                    goToHomeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Create intent to navigate to home page
                            Intent intent = new Intent(Result.this, HomeActivity.class);
                            // Pass sorted options as extras
                            intent.putStringArrayListExtra("sortedOptions", sortedOptions);
                            // Start activity
                            startActivity(intent);
                            // Finish current activity
                            finish();
                        }
                    });
                } else {
                    // Handle the case where either optionsList or factorPriorities is null
                    if(optionsList==null){
                        System.out.println("Arun ji error: optionsList is null");
                    }
                    else{
                        System.out.println("Arun ji error: factorPriorities is null");
                    }

                }
            } else {
                // Handle the case where priorityMatrix is null
                System.out.println("Arun ji error: priorityMatrix is null");
            }
        } else {
            // Handle the case where extras bundle is null
            System.out.println("Arun ji error: extras bundle is null");
        }
    }



    // Method to calculate scores and sort optionsList
    private ArrayList<String> calculateScoresAndSort() {
        // Create a list to store the scores for each option
        ArrayList<Double> optionScores = new ArrayList<>();
        //printing factor priorities
        System.out.println("factors priorities of size:"+factorPriorities.size()+" are");
        for(int i=0;i< factorPriorities.size();i++){
            System.out.println(factorPriorities.get(i));
        }

        // Iterate through each option
        // Check if optionsList is not null and its size is greater than 0
        if (optionsList != null && optionsList.size() > 0) {
            // Iterate through each option
            for (int i = 0; i < optionsList.size(); i++) {
                double score = 0.0; // Initialize score for the current option

                // Check if factorPriorities and priorityMatrix are not null
                if (factorPriorities != null && priorityMatrix != null) {
                    // Check if factorPriorities and priorityMatrix have valid sizes
                    if (factorPriorities.size() == priorityMatrix[i].length) {
                        // Iterate through each factor and calculate the product with its priority
                        for (int j = 0; j < factorPriorities.size(); j++) {
                            try {
                                // Parse priority from String to double
                                double priority = Double.parseDouble(factorPriorities.get(j));
                                // Parse matrix value from String to double
                                double matrixValue = Double.parseDouble(priorityMatrix[i][j]);
                                // Add the product to the score
                                score += priority * matrixValue;
                            } catch (NumberFormatException e) {
                                // Handle the case where parsing fails
                                System.out.println("Error parsing priority or matrix value for option " + i);
                            }
                        }
                    } else {
                        // Handle the case where sizes of factorPriorities and priorityMatrix do not match
                        System.out.println("Size mismatch between factorPriorities and priorityMatrix for option " + i +"Because factorPriorities size is "+factorPriorities.size()+" and priority matrix width is "+ priorityMatrix[i].length);
                    }
                } else {
                    // Handle the case where factorPriorities or priorityMatrix is null
                    System.out.println("Factor priorities or priorityMatrix is null for option " + i);
                }

                // Add the calculated score to the optionScores list
                optionScores.add(score);
                // Print the option score
                System.out.println(optionsList.get(i) + " score: " + score);
            }
        } else {
            // Handle the case where optionsList is null or empty
            System.out.println("OptionsList is null or empty");
        }


        // Sort the optionsList based on the calculated scores in descending order
        ArrayList<String> sortedOptions = new ArrayList<>(optionsList); // Create a copy of optionsList
        // Create a custom comparator to sort optionsList based on optionScores
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String option1, String option2) {
                // Find the indices of the options in optionsList
                int index1 = sortedOptions.indexOf(option1);
                int index2 = sortedOptions.indexOf(option2);

                // Retrieve the scores for the options
                double score1 = optionScores.get(index1);
                double score2 = optionScores.get(index2);

                // Compare the scores
                return Double.compare(score1, score2);
            }
        };

        // Sort optionsList using the custom comparator
        Collections.sort(sortedOptions, comparator);

        return sortedOptions;
    }

    // Method to add a header row to the TableLayout
    private void addHeaderRow(TableLayout tableLayout) {
        TableRow headerRow = new TableRow(this);

        // Add TextView for Rank
        TextView tvRankHeader = new TextView(this);
        tvRankHeader.setText("Rank");
        // Apply style attributes to the header TextView
        tvRankHeader.setTextColor(Color.GREEN); // Example text color
        tvRankHeader.setTextSize(24); // Example text size in SP
        tvRankHeader.setPadding(8, 8, 8, 8); // Example padding in pixels
        tvRankHeader.setTypeface(null, Typeface.BOLD);
        // Set layout parameters for the first column (1/5th of the width)
        TableRow.LayoutParams rankParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.MATCH_PARENT,
                2.0f / 5.0f // Set weight to 1/5th of the total width
        );
        tvRankHeader.setLayoutParams(rankParams);
        // Add TextView for Options
        TextView tvOptionHeader = new TextView(this);
        tvOptionHeader.setText("Options");
        // Apply style attributes to the header TextView
        tvOptionHeader.setTextColor(Color.GREEN); // Example text color
        tvOptionHeader.setTextSize(24); // Example text size in SP
        tvOptionHeader.setPadding(8, 8, 8, 16); // Example padding in pixels
        tvOptionHeader.setTypeface(null, Typeface.BOLD);
        TableRow.LayoutParams optionParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.MATCH_PARENT,
                3.0f / 5.0f // Set weight to 4/5th of the total width
        );
        tvOptionHeader.setLayoutParams(optionParams);

//        tvRankHeader.setGravity(Gravity.CENTER);
        // Add TextViews to the header row
        headerRow.addView(tvRankHeader);
        headerRow.addView(tvOptionHeader);

        // Add the header row to the TableLayout at the top
        tableLayout.addView(headerRow, 0);
    }

    // Method to display the sorted optionsList in the TableLayout
    private void displaySortedOptions(ArrayList<String> sortedOptions) {
        TableLayout tableLayout = findViewById(R.id.resultLayout);
        tableLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_table_bg));
        // Add header row
        addHeaderRow(tableLayout);
        // Add rows to the TableLayout for each option
        for (int i = sortedOptions.size()-1; i >=0; i--) {
            TableRow row = new TableRow(this);
            // Calculate the intensity of red color based on the row index
            int redIntensity = 255 - ((i+1) * (250 / sortedOptions.size()));
            int backgroundColor = Color.rgb(255, redIntensity, redIntensity);
            row.setBackgroundColor(backgroundColor);
            // Add TextView for rank
            TextView tvRank = new TextView(this);
            tvRank.setText(String.valueOf(sortedOptions.size() - i )); // Rank starts from 1
            // style
            tvRank.setTextColor(Color.BLACK); // Example text color
            tvRank.setTextSize(24); // Example text size in SP
            tvRank.setPadding(8, 8, 8, 8); // Example padding in pixels
            tvRank.setTypeface(null, Typeface.BOLD);
            // Set layout parameters for the TextView
            TableRow.LayoutParams rankParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    2.0f/5.0f // Set weight to 1 to make it occupy half the width of TableRow
            );
            tvRank.setLayoutParams(rankParams);
            // Add TextView for option
            TextView tvOption = new TextView(this);

            tvOption.setText(sortedOptions.get(i));
            // style
            tvOption.setTextColor(Color.BLACK); // Example text color
            tvOption.setTextSize(24); // Example text size in SP
            tvOption.setPadding(8, 8, 8, 8); // Example padding in pixels
            tvOption.setTypeface(null, Typeface.BOLD);
            // Set layout parameters for the TextView
            TableRow.LayoutParams optionParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    3.0f/5.0f // Set weight to 1 to make it occupy half the width of TableRow
            );
            tvOption.setLayoutParams(optionParams);


            // Add TextViews to the TableRow
            row.addView(tvRank);
            row.addView(tvOption);

            // Add the TableRow to the TableLayout
            tableLayout.addView(row);
        }
    }
}
