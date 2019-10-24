package com.example.coba.myreport.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.coba.myreport.R;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Session.TaskSessionManager;
import com.example.coba.myreport.Url.BaseUrl;

public class ViewOnlyActivity extends AppCompatActivity {

    BaseUrl baseUrl;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_only);

        baseUrl = new BaseUrl();

        WebView web = findViewById(R.id.tableWebViewOnly);
        Log.i("WEB VIEW URL", baseUrl.getBaseUrlNoApi());
        web.loadUrl(baseUrl.getBaseUrlNoApi()+"laporan");
        web.setWebViewClient(new WebViewClient());
    }
}
