package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class ChoosingMyFactors extends AppCompatActivity {
    private boolean[] isSelected = new boolean[9]; // Array to store selection state
    private ArrayList<String> selectedFactors = new ArrayList<>(); // ArrayList to store selected factors
    private Button nextButton;
    private ArrayList<String> optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_my_factors);
        nextButton = findViewById(R.id.nextButtonF);
        nextButton.setEnabled(false); // Disable next button
        //Receiving Option list
        optionsList = getIntent().getStringArrayListExtra("optionsList");

        Toolbar toolbar = findViewById(R.id.toolbar2);
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
        // Initialize TextViews and set OnClickListener for each one
        for (int i = 0; i < 9; i++) {
            int resId = getResources().getIdentifier("factor" + (i + 1), "id", getPackageName());
            final TextView textView = findViewById(resId);
            final int index = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isSelected[index] = !isSelected[index]; // Toggle the selection state
                    updateTextViewAppearance(textView, isSelected[index]);
                    updateSelectedFactors(textView.getText().toString());
                    // Check if any factor is selected
                    if (isAnyFactorSelected()) {
                        nextButton.setEnabled(true); // Enable next button
                    } else {
                        nextButton.setEnabled(false); // Disable next button
                    }
                }
            });
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FactorsPriority activity and pass the selected factors as an extra
                Intent intent = new Intent(ChoosingMyFactors.this, FactorsPriority.class);
                intent.putStringArrayListExtra("selectedFactors", selectedFactors);
                intent.putStringArrayListExtra("optionsList", optionsList);
                startActivity(intent);
            }
        });

    }

    // Method to update TextView appearance based on selection state
    private void updateTextViewAppearance(TextView textView, boolean isSelected) {
        if (isSelected) {
            textView.setBackgroundColor(Color.parseColor("#6DE747"));
        } else {
            textView.setBackgroundColor(Color.parseColor("#C8B3B3"));
        }
    }

    // Method to update selected factors list
    private void updateSelectedFactors(String factor) {
        if (!selectedFactors.contains(factor)) {
            selectedFactors.add(factor);
        } else {
            selectedFactors.remove(factor);
        }
    }

    // Method to check if any factor is selected
    private boolean isAnyFactorSelected() {
        for (boolean selected : isSelected) {
            if (selected) {
                return true; // At least one factor is selected
            }
        }
        return false; // No factor is selected
    }
}
