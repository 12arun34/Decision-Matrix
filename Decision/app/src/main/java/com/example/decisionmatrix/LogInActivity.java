package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

public class LogInActivity extends AppCompatActivity {
    Button loginNextButton;
    EditText phoneInput;
    Button sendOtpBtn;
    CountryCodePicker countryCodePicker;
EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        phoneInput = findViewById(R.id.phone);   // password = phone
        sendOtpBtn = findViewById(R.id.nextButton);
        name = findViewById(R.id.username);

        countryCodePicker = findViewById(R.id.countryCode);

        countryCodePicker.registerCarrierNumberEditText(phoneInput);
        sendOtpBtn.setOnClickListener((v)->{
            if(name.getText().toString().equals("")){
                name.setError("Enter Valid Name");
                return;
            }
            if(!countryCodePicker.isValidFullNumber()){
                phoneInput.setError("Enter valid Phone number");
                return;
            }
            Intent intent = new Intent(LogInActivity.this,OtpActivity.class);
            intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
            startActivity(intent);

        });

    }
}