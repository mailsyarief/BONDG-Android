package com.example.coba.myreport.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.coba.myreport.R;
import com.example.coba.myreport.Session.SessionManager;

public class SplashScreenActivity extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(getApplicationContext());

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sessionManager.getUser().getToken().isEmpty()){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }


            }
        }, 1300);
    }
}
