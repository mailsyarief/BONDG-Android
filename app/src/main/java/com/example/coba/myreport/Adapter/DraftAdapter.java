package com.example.coba.myreport.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coba.myreport.Activity.DetailDraftActivity;
import com.example.coba.myreport.R;

public class DraftAdapter extends RecyclerView.Adapter<DraftAdapter.ViewHolder>{

    Context context;

    public DraftAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_draft, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DraftAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetail = new Intent(context, DetailDraftActivity.class);
                    context.startActivity(goToDetail);
                }
            });
        }
    }

}
