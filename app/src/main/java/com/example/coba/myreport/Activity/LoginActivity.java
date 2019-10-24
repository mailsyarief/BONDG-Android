package com.example.coba.myreport.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

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
    String tokenhp;
    SessionManager sessionManager;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginbtn = findViewById(R.id.loginBtn);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //To do//
                            return;
                        }
                        // Get the Instance ID token//
                        tokenhp = task.getResult().getToken();
                        Log.d("FCM TOKEN", tokenhp);
                    }
                });


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
                    progressdialog = new ProgressDialog(getApplicationContext());
                    progressdialog.setMessage("Please Wait....");
                    loginbtn.setVisibility(View.INVISIBLE);
                    Log.i("FORM DATA", username.getText().toString() + " " + password.getText().toString());
                    login();
                }
            }
        });

    }

    private void login(){

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //To do//
                            return;
                        }
                        // Get the Instance ID token//
                        tokenhp = task.getResult().getToken();
                        Log.d("FCM TOKEN", tokenhp);
                    }
                });

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<LoginResponse> call = api.login(username.getText().toString(), password.getText().toString(), tokenhp);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i("SUCC", response.body().getMessage().getRole().toString());
                if(response.body().getError() == 0) {

                    if(response.body().getMessage().getRole() == 0){
                        sessionManager.setUser(response.body().getMessage());
                        Intent goToActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToActivity);
                        progressdialog.dismiss();
                        finish();
                    }else if(response.body().getMessage().getRole() == 2){
                        sessionManager.setUser(response.body().getMessage());
                        Intent goToActivity = new Intent(LoginActivity.this, ViewOnlyActivity.class);
                        startActivity(goToActivity);
                        progressdialog.dismiss();
                        finish();
                    }else{
                        loginbtn.setVisibility(View.VISIBLE);
                        final Toast toast = Toast.makeText(getApplicationContext(), "Username/Password Salah", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else{

                    loginbtn.setVisibility(View.VISIBLE);
                    final Toast toast = Toast.makeText(getApplicationContext(), "Username/Password Salah", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i("FAILED", t.getMessage());
                final Toast toast = Toast.makeText(getApplicationContext(), "Terjadi Kesalahan :(", Toast.LENGTH_SHORT);
                toast.show();
                loginbtn.setVisibility(View.VISIBLE);
            }
        });

    }
}
