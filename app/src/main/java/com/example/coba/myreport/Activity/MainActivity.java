package com.example.coba.myreport.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Fragment.ReportFragment;
import com.example.coba.myreport.Fragment.TaskFragment;
import com.example.coba.myreport.Response.LoginResponse;
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


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BaseUrl baseUrl;
    String tokenhp;
    SessionManager sessionManager;
    ProgressDialog progressdialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getBaseContext());
        baseUrl = new BaseUrl();





        loadFragment(new TaskFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bn_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutBtn:

                TaskSessionManager taskSessionManager = new TaskSessionManager(getApplicationContext());
                SessionManager sessionManager = new SessionManager(getApplicationContext());

                sessionManager.logout();
                taskSessionManager.logout();

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.task_menu:
                fragment = new TaskFragment();
                break;
        }
        return loadFragment(fragment);
    }




}

