package com.example.coba.myreport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Response.LoginResponse;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Url.BaseUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button loginbtn;
    BaseUrl baseUrl;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginBtn);


        sessionManager = new SessionManager(getApplicationContext());
        baseUrl = new BaseUrl();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loginbtn.setVisibility(View.INVISIBLE);
                if(TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(username.getText().toString())){
                    final Toast toast = Toast.makeText(LoginActivity.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    login();
                }
            }
        });

    }

    private void login(){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<LoginResponse> call = api.login();
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i("SUCC", response.body().getMessage().getUsername());
                sessionManager.setUser(response.body().getMessage());
                Intent goToActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(goToActivity);

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("SUCC", t.getMessage());
            }
        });

    }
}
