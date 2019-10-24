package com.example.coba.myreport.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coba.myreport.Activity.LoginActivity;
import com.example.coba.myreport.Adapter.TaskAdapter;
import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.Pojo.Task;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Response.SubmitResponse;
import com.example.coba.myreport.Response.TaskResponse;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Session.TaskSessionManager;
import com.example.coba.myreport.Url.BaseUrl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskFragment extends Fragment {

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;
    BaseUrl baseUrl = new BaseUrl();
    SwipeRefreshLayout mySwipeRefreshLayout;
    SessionManager sessionManager;
    TaskSessionManager taskSessionManager;
    List<Task> taskList = new ArrayList<>();
    ProgressBar progressBarload;
    TextView tidakadatugas;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        progressBarload = view.findViewById(R.id.progressBarload);
        progressBarload.setVisibility(View.GONE);
        tidakadatugas = view.findViewById(R.id.tidakadatugas);
        tidakadatugas.setVisibility(View.GONE);

        taskAdapter = new TaskAdapter(getContext(), taskList);
        recyclerView = view.findViewById(R.id.taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(taskAdapter);
        mySwipeRefreshLayout = view.findViewById(R.id.taskRefresh);

        sessionManager = new SessionManager(getContext());
        taskSessionManager = new TaskSessionManager(getContext());

        loadTask();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadTask();

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mySwipeRefreshLayout.setColorSchemeColors(Color.GRAY);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Toast toast = Toast.makeText(getActivity(), "Mengecek Pembaruan", Toast.LENGTH_SHORT);
                        toast.show();
                        loadTask();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }, 800);
            }
        });
    }

    private void loadTask(){

        progressBarload.setVisibility(View.VISIBLE);
        tidakadatugas.setVisibility(View.GONE);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<TaskResponse> call = api.getTask(sessionManager.getUser().getRemember_token());
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                Log.i("SUCC TASK", response.message());

                if(response.body().getMessage().size() <= 0){
                    progressBarload.setVisibility(View.GONE);
                    final Toast toast = Toast.makeText(getActivity(), "Tidak Ada Tugas", Toast.LENGTH_SHORT);
                    toast.show();
                    taskSessionManager.setTaskList(response.body().getMessage());
                    tidakadatugas.setVisibility(View.VISIBLE);
                }else{
                    progressBarload.setVisibility(View.GONE);
                    tidakadatugas.setVisibility(View.GONE);
                    taskSessionManager.setTaskList(response.body().getMessage());
                    Log.i("AFTER LOAD OFFLINE TASK", taskSessionManager.getTaskList().toString());
                    taskAdapter = new TaskAdapter(getContext(),response.body().getMessage());
                    recyclerView.setAdapter(taskAdapter);

                }

            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Log.i("ERR TASK", t.getMessage());
                final Toast toast = Toast.makeText(getContext(), "Terjadi Kesalahan Koneksi Internet :(", Toast.LENGTH_LONG);
                toast.show();

                taskAdapter = new TaskAdapter(getContext(),taskSessionManager.getTaskList());
                progressBarload.setVisibility(View.GONE);
                tidakadatugas.setVisibility(View.GONE);

                if(taskSessionManager.getTaskList().size() <= 0){
                    tidakadatugas.setVisibility(View.VISIBLE);
                }

                Log.i("OFFLINE TASK", taskSessionManager.getTaskList().toString());

                recyclerView.setAdapter(taskAdapter);

            }
        });

    }


}
