package com.example.coba.myreport.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.coba.myreport.BottomSheetDialog.ServiceRejectionBottomSheet;
import com.example.coba.myreport.R;

public class DetailTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabAccept);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Waktu mulai pengerjaan telah disimpan", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fabreject = findViewById(R.id.fabReject);
        fabreject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceRejectionBottomSheet bottomSheetFragment = new ServiceRejectionBottomSheet();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });
    }

}
