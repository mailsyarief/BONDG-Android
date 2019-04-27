package com.example.coba.myreport.Fragment;

import android.content.Context;
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
import android.widget.Toast;

import com.example.coba.myreport.Adapter.TaskAdapter;
import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.Pojo.Task;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Response.TaskResponse;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Session.TaskSessionManager;
import com.example.coba.myreport.Url.BaseUrl;

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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

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

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<TaskResponse> call = api.getTask();
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                Log.i("SUCC", response.message());
                Log.i("SUCC DATA", response.body().getMessage().get(0).getNama_pelapor());
                taskSessionManager.setTaskList(response.body().getMessage());

                taskAdapter = new TaskAdapter(getContext(),response.body().getMessage());
                recyclerView.setAdapter(taskAdapter);

            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                Log.i("ERR", t.getMessage());

                taskAdapter = new TaskAdapter(getContext(),taskSessionManager.getTaskList());
                recyclerView.setAdapter(taskAdapter);

            }
        });


    }
}
