package com.example.decisionmatrix;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ChoosingOptions extends AppCompatActivity {

    private LinearLayout optionsContainer;
    private ArrayList<String> optionsList;
    private int optionCounter = 1; // Counter to keep track of the row number

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_options);
        optionsContainer = findViewById(R.id.optionsContainer);
        Button addOptionButton = findViewById(R.id.addOptionButton);
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setEnabled(false);
        optionsList = new ArrayList<>();
        // setting navigation bar
        Toolbar toolbar = findViewById(R.id.toolbar1);
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
        // Set click listener for the Add Option button
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new EditText field to the optionsContainer
                addEditText();
            }
        });

        // Set click listener for the Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!hasDuplicateOptions()) {
                    // Gather the option entries from EditText fields and store them in the optionsList
                    gatherOptions();
                    // Start the ChoosingFactors activity if all options are non-empty
                    if (!optionsList.contains("")) {
                        Intent intent = new Intent(ChoosingOptions.this, ChoosingMyFactors.class);
                        intent.putStringArrayListExtra("optionsList", optionsList);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private void addEditText() {
        // Check for empty option rows and display alerts
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            LinearLayout rowLayout = (LinearLayout) optionsContainer.getChildAt(i);
            EditText editText = (EditText) rowLayout.getChildAt(0);
            if (editText.getText().toString().trim().isEmpty()) {
                // Display alert for empty option row
                TextView alertTextView = (TextView) rowLayout.getChildAt(1);
                alertTextView.setText("Option cannot be empty");
                alertTextView.setVisibility(View.VISIBLE);
                return; // Stop execution if empty option found
            }
        }

        // Create a new LinearLayout to hold EditText and alert text
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setOrientation(LinearLayout.VERTICAL);
        // Set padding for top and bottom
        int paddingTopBottom = getResources().getDimensionPixelSize(R.dimen.row_bottom_margin); // Replace with your desired padding size in pixels
        rowLayout.setPadding(paddingTopBottom, paddingTopBottom, paddingTopBottom, 0);
        // Create a new EditText dynamically
        EditText newEditText = new EditText(this);

        // Set layout parameters for EditText
        LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        newEditText.setLayoutParams(editTextParams);
        // Set background from the XML file
        newEditText.setBackground(ContextCompat.getDrawable(this, R.drawable.option_editext_bg));
        // Set hint as "Enter option x"
        newEditText.setHint("Enter option " + optionCounter);

        // Increment the counter for the next row
        optionCounter++;

        // Create a new TextView for alert text
        TextView alertText = new TextView(this);
        alertText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        alertText.setTextSize(12);
        alertText.setVisibility(View.GONE); // Initially hide the alert text

        // Add the new EditText and alert text to the rowLayout
        rowLayout.addView(newEditText);
        rowLayout.addView(alertText);

        // Add the rowLayout to the optionsContainer
        optionsContainer.addView(rowLayout);

        // Add a TextWatcher to the EditText to update data character-wise
        newEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // This method is called when the text is changed.
                // You can update data or perform any action here.
                gatherOptions();
                updateNextButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This method is called after the text is changed.
            }
        });

        // Update Next button state if no duplicates or empty options found
        updateNextButtonState();
    }




    private void gatherOptions() {
        optionsList.clear();
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            LinearLayout rowLayout = (LinearLayout) optionsContainer.getChildAt(i);
            EditText editText = (EditText) rowLayout.getChildAt(0);
            String option = editText.getText().toString().trim();
            optionsList.add(option);
        }
    }

    private void updateNextButtonState() {
        Button nextButton = findViewById(R.id.nextButton);
        boolean isAnyOptionEmpty = false;


        // Check for empty option fields and duplicate options
        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            LinearLayout rowLayout = (LinearLayout) optionsContainer.getChildAt(i);
            EditText editText = (EditText) rowLayout.getChildAt(0);
            String option = editText.getText().toString().trim();
            TextView alertTextView = (TextView) rowLayout.getChildAt(1);
            alertTextView.setText("Option cannot be empty");
            // Check for empty option
            if (option.isEmpty()) {
                isAnyOptionEmpty = true;
                // Display alert for empty option row

                alertTextView.setVisibility(View.VISIBLE);
//                Toast.makeText(this, "Option is empty", Toast.LENGTH_SHORT).show();
                break;
            }
            else{
                alertTextView.setVisibility(View.GONE);
            }
         }

        // Enable or disable the next button based on the conditions
        nextButton.setEnabled(!isAnyOptionEmpty  && optionsContainer.getChildCount() >= 2);
    }

    private boolean hasDuplicateOptions(){
        boolean hasDuplicateOption_flag = false;

        for (int i = 0; i < optionsContainer.getChildCount(); i++) {
            LinearLayout rowLayout = (LinearLayout) optionsContainer.getChildAt(i);
            EditText editText = (EditText) rowLayout.getChildAt(0);
            String option = editText.getText().toString().trim();

             // Check for duplicate option
            if (optionsList.indexOf(option) != optionsList.lastIndexOf(option)) {
                hasDuplicateOption_flag = true;
                // Show prompt message
//                Toast.makeText(this, "Two options CAN NOT be same", Toast.LENGTH_SHORT).show();
                alert_for_duplicate_option();
                break;
            }
        }
        return hasDuplicateOption_flag;
    }
private  void alert_for_duplicate_option(){
    // Create an AlertDialog.Builder object
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Set the title and message for the AlertDialog
    builder.setTitle("Duplicate Options");
    builder.setMessage("Two options CAN NOT be same");
// Set the positive button (OK button) and its click listener
    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // User clicked OK button
            dialog.dismiss(); // Close the dialog
        }
    });

// Create and show the AlertDialog
    AlertDialog alertDialog = builder.create();
    alertDialog.show();

}
    // IT IS BEST 2
}
