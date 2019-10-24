package com.example.coba.myreport.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Response.SubmitResponse;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Session.TaskSessionManager;
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

public class SplashScreenActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String tokenhp;
    BaseUrl baseUrl = new BaseUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.getUser() != null){
            cekLogin();
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.getUser() == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }else{
                    if (sessionManager.getUser().getRole() == 0){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }else if(sessionManager.getUser().getRole() == 2){
                        startActivity(new Intent(getApplicationContext(), ViewOnlyActivity.class));
                        finish();
                    }
                }
            }
        }, 1300);
    }

    private void cekLogin(){

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //To do//
                            return;
                        }
                        // Get the Instance ID token//
                        tokenhp = task.getResult().getToken();
                        Log.d("FCM TOKEN FROM MAIN", tokenhp);
                    }
                });

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<SubmitResponse> call = api.cektoTenHp(sessionManager.getUser().getUsername());
        call.enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                Log.i("RESPONSE", response.message());
                Log.i("RESPONSE BODY", response.body().getMessage());
                if(!response.body().getMessage().equals(tokenhp)){
                    TaskSessionManager taskSessionManager = new TaskSessionManager(getApplicationContext());
                    SessionManager sessionManager = new SessionManager(getApplicationContext());

                    sessionManager.logout();
                    taskSessionManager.logout();

                    Toast.makeText(SplashScreenActivity.this, "Anda Telah Login Di Perangkat Lain", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                };
            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {
                Log.i("RESPONSE ERR", t.getMessage());
                final Toast toast = Toast.makeText(getApplicationContext(), "Terjadi Kesalahan Koneksi Internet :(", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
