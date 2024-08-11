package com.example.decisionmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.decisionmatrix.utils.FireBaseUtil;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(FireBaseUtil.isLoggedIn()){
                        startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this,LogInActivity.class));
                    }
                    finish();

                }
            },1000);
    }

}