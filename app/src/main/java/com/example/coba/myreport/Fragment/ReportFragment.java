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
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.coba.myreport.Adapter.TaskAdapter;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Url.BaseUrl;


public class ReportFragment extends Fragment {

    BaseUrl baseUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        baseUrl = new BaseUrl();

        WebView web = view.findViewById(R.id.tableWebView);
        web.loadUrl(baseUrl.getBaseUrl()+"/bondg");
        web.setWebViewClient(new WebViewClient());

        return view;
    }
}
