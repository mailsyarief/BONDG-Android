package com.example.coba.myreport.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coba.myreport.Activity.DetailTaskActivity;
import com.example.coba.myreport.Pojo.Task;
import com.example.coba.myreport.R;

import java.util.List;

import butterknife.BindView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    Context context;
    List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_task, viewGroup, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Task task = taskList.get(i);
        Log.i("TASK LIST ITEM", task.getKeluhan());

        viewHolder.nameCard.setText(task.getNama_pelapor() + " | " + task.getNo_hp_pelapor());
        viewHolder.alamatCard.setText(task.getAlamat_pelapor());
        viewHolder.keluhanCard.setText(task.getKeluhan());

        viewHolder.nama.setText(task.getNama_pelapor());
        viewHolder.posko.setText(task.getPosko());
        viewHolder.id_laporan.setText(task.getId_laporan());
        viewHolder.no_hp_pelapor.setText(task.getNo_hp_pelapor());
        viewHolder.no_agenda.setText(task.getNomor_agenda());
        viewHolder.tgl_bln_thn.setText(task.getTgl_bln_thn());
        viewHolder.no_meter_lama.setText(task.getNo_meter_lama());
        viewHolder.no_meter_baru.setText(task.getNo_meter_baru());
        viewHolder.daya.setText(task.getDaya());
        viewHolder.gardu.setText(task.getGardu());
        viewHolder.perbaikan.setText(task.getPerbaikan());
        viewHolder.tgl_kirim_petugas.setText(task.getTgl_kirim_petugas());

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameCard = itemView.findViewById(R.id.taskNamaCard);
        TextView alamatCard = itemView.findViewById(R.id.taskAlamatCard);
        TextView keluhanCard = itemView.findViewById(R.id.taskKeluhanCard);


        TextView nama = new TextView(context);
        TextView posko = new TextView(context);
        TextView id_laporan = new TextView(context);
        TextView no_hp_pelapor = new TextView(context);
        TextView no_agenda = new TextView(context);
        TextView tgl_bln_thn = new TextView(context);
        TextView no_meter_lama = new TextView(context);
        TextView no_meter_baru = new TextView(context);
        TextView daya = new TextView(context);
        TextView gardu = new TextView(context);
        TextView perbaikan = new TextView(context);
        TextView tgl_kirim_petugas = new TextView(context);


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetail = new Intent(context, DetailTaskActivity.class);

                    goToDetail.putExtra("namaPelapor", nama.getText());
                    goToDetail.putExtra("alamat", alamatCard.getText());
                    goToDetail.putExtra("no_hp_pelapor", no_hp_pelapor.getText());
                    goToDetail.putExtra("id_laporan", id_laporan.getText());
                    goToDetail.putExtra("keluhan", keluhanCard.getText());
                    goToDetail.putExtra("posko", posko.getText());
                    goToDetail.putExtra("no_agenda", no_agenda.getText());
                    goToDetail.putExtra("tgl_bln_thn", tgl_bln_thn.getText());
                    goToDetail.putExtra("no_meter_lama", no_meter_lama.getText());
                    goToDetail.putExtra("no_meter_baru", no_meter_baru.getText());
                    goToDetail.putExtra("daya", daya.getText());
                    goToDetail.putExtra("gardu", gardu.getText());
                    goToDetail.putExtra("perbaikan", perbaikan.getText());
                    goToDetail.putExtra("tgl_kirim_petugas", tgl_kirim_petugas.getText());

                    context.startActivity(goToDetail);
                }
            });
        }
    }

}

