package com.example.coba.myreport.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coba.myreport.Adapter.DraftAdapter;
import com.example.coba.myreport.Adapter.TaskAdapter;
import com.example.coba.myreport.R;


public class DraftFragment extends Fragment {

    DraftAdapter draftAdapter;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draft, container, false);

        draftAdapter = new DraftAdapter(getContext());
        recyclerView = view.findViewById(R.id.draftList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(draftAdapter);
        //mySwipeRefreshLayout = view.findViewById(R.id.taskRefresh);

        return view;
    }
}
