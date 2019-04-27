package com.example.coba.myreport.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.coba.myreport.R;

public class DetailDraftActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_draft);

        FloatingActionButton fab = findViewById(R.id.fabSelesai);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Waktu selesai pengerjaan telah disimpan", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
