package com.example.coba.myreport.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coba.myreport.Api.API;
import com.example.coba.myreport.R;
import com.example.coba.myreport.Response.SubmitResponse;
import com.example.coba.myreport.Session.SessionManager;
import com.example.coba.myreport.Session.TaskSessionManager;
import com.example.coba.myreport.Url.BaseUrl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailTaskActivity extends AppCompatActivity {



    TextView DwaktuMulai, DwaktuSelesai;
    TaskSessionManager taskSessionManager;
    SessionManager sessionManager;
    ImageView imgSebelum, imgSesudah, imgBeritaAcara;
    Uri uriSebelum, uriSesudah, uriBeritaAcara;
    Integer selectedImg;
    BaseUrl baseUrl = new BaseUrl();
    String imgPath1;
    String kwhsebelum, kwhsesudah, beritaacarar,idplaporan;
    ProgressDialog pd;
    Button submitRejectBtn;
    Spinner spinner;

    CardView cardMeterSebelum, cardMeterSesudah, cardBeritaAcara, spinnerCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitRejectBtn = findViewById(R.id.submitRejectBtn);
        spinnerCard = findViewById(R.id.spinnerCard);
        spinner = findViewById(R.id.spinner);
        cardBeritaAcara = findViewById(R.id.beritaAcaraCard);
        cardMeterSebelum = findViewById(R.id.noMeteranSebelum);
        cardMeterSesudah = findViewById(R.id.noMeteranSesudah);

        submitRejectBtn.setVisibility(View.GONE);
        spinnerCard.setVisibility(View.GONE);

        //checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        final FloatingActionButton fabComplete = findViewById(R.id.fabComplete);
        final FloatingActionButton fabreject = findViewById(R.id.fabReject);
        final FloatingActionButton fab = findViewById(R.id.fabAccept);

        final Button kirimBtn = findViewById(R.id.kirimBtn);
        final ImageButton telpBtn = findViewById(R.id.detailTelpBtn);
        final ImageButton mapsBtn = findViewById(R.id.detailMapsBtn);

        pd = new ProgressDialog(DetailTaskActivity.this);

        imgSebelum = findViewById(R.id.imgSebelum);
        imgSesudah = findViewById(R.id.imgSesudah);
        imgBeritaAcara = findViewById(R.id.imgBeritaAcara);


        taskSessionManager = new TaskSessionManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        final TextView DnoAgenda = findViewById(R.id.detailNomorAgenda);
        TextView DnamaPelapor = findViewById(R.id.detailNamaPelapor);
        TextView DtglBlnThn = findViewById(R.id.detailTglBlnThn);
        TextView DnoTlp = findViewById(R.id.detailNoTelp);
        TextView Dposko = findViewById(R.id.detailPosko);
        TextView DidPelanggan = findViewById(R.id.detailIdPelanggan);
        TextView DnoMeterLama = findViewById(R.id.detailNoMeterLama);
        TextView DnoMeterBaru = findViewById(R.id.detailNoMeterBaru);
        TextView Ddaya = findViewById(R.id.detailDaya);
        TextView Dgardu = findViewById(R.id.detailGardu);
        TextView Dalamat = findViewById(R.id.detailAlamat);
        TextView Dkeluhan = findViewById(R.id.detailKeluhan);
        TextView Dperbaikan = findViewById(R.id.detailPerbaikan);

        DwaktuMulai = findViewById(R.id.waktuMulai);
        DwaktuSelesai = findViewById(R.id.waktuSelesai);



        final Intent fromTaskList = getIntent();


        DnamaPelapor.setText(fromTaskList.getStringExtra("namaPelapor"));
        Dalamat.setText(fromTaskList.getStringExtra("alamat"));
        DnoTlp.setText(fromTaskList.getStringExtra("no_hp_pelapor"));
        DnoAgenda.setText(fromTaskList.getStringExtra("no_agenda"));
        DtglBlnThn.setText(fromTaskList.getStringExtra("tgl_bln_thn"));
        DidPelanggan.setText(fromTaskList.getStringExtra("id_laporan"));
        DnoMeterLama.setText(fromTaskList.getStringExtra("no_meter_lama"));
        DnoMeterBaru.setText(fromTaskList.getStringExtra("no_meter_baru"));
        Ddaya.setText(fromTaskList.getStringExtra("daya"));
        Dgardu.setText(fromTaskList.getStringExtra("gardu"));
        Dposko.setText(fromTaskList.getStringExtra("posko"));
        Dkeluhan.setText(fromTaskList.getStringExtra("keluhan"));
        Dperbaikan.setText(fromTaskList.getStringExtra("perbaikan"));

        idplaporan = fromTaskList.getStringExtra("id_laporan");
//        goToDetail.putExtra("namaPelapor", nameCard.getText());
//        goToDetail.putExtra("alamat", alamatCard.getText());
//        goToDetail.putExtra("no_hp_pelapor", no_hp_pelapor.getText());
//        goToDetail.putExtra("keluhan", keluhanCard.getText());
//        goToDetail.putExtra("posko", posko.getText());
//        goToDetail.putExtra("no_agenda", no_agenda.getText());
//        goToDetail.putExtra("tgl_bln_thn", tgl_bln_thn.getText());
//        goToDetail.putExtra("no_meter_lama", no_meter_lama.getText());
//        goToDetail.putExtra("no_meter_baru", no_meter_baru.getText());
//        goToDetail.putExtra("daya", daya.getText());
//        goToDetail.putExtra("gardu", gardu.getText());
//        goToDetail.putExtra("perbaikan", perbaikan.getText());
//        goToDetail.putExtra("tgl_kirim_petugas", tgl_kirim_petugas.getText());

        if(DwaktuMulai.getText().equals("-")){
            fabComplete.hide();
        }

        if(DwaktuSelesai.getText().equals("-")){
            kirimBtn.setVisibility(View.GONE);
        }

        //        0 = belum ada apa apa
        //        1 = sudah mulai kerja
        //        2 = sudah selesai kerja
        //        3 = tolak


        if(taskSessionManager.getTaskStatus(DnoAgenda.getText().toString()).equals("0")){

            fabComplete.hide();
            fab.show();
            fabreject.show();

            kirimBtn.setVisibility(View.GONE);
        }else if(taskSessionManager.getTaskStatus(DnoAgenda.getText().toString()).equals("1")){

            fabComplete.show();
            fab.hide();
            fabreject.hide();

            kirimBtn.setVisibility(View.GONE);

        }else if(taskSessionManager.getTaskStatus(DnoAgenda.getText().toString()).equals("2")){

            fab.hide();
            fabreject.hide();
            fabComplete.hide();

            kirimBtn.setVisibility(View.VISIBLE);

        }else if(taskSessionManager.getTaskStatus(DnoAgenda.getText().toString()).equals("3")){

            fab.hide();
            fabreject.hide();
            fabComplete.hide();

            cardBeritaAcara.setVisibility(View.GONE);
            cardMeterSebelum.setVisibility(View.GONE);
            cardMeterSesudah.setVisibility(View.GONE);

            submitRejectBtn.setVisibility(View.VISIBLE);
            spinnerCard.setVisibility(View.VISIBLE);

            kirimBtn.setVisibility(View.GONE);
        }

        DwaktuMulai.setText(taskSessionManager.getStartTask(DnoAgenda.getText().toString()));
        DwaktuSelesai.setText(taskSessionManager.getEndTask(DnoAgenda.getText().toString()));



        TextView meteranSebelum = findViewById(R.id.drafFotoSebelum);
        TextView meteranSesudah = findViewById(R.id.drafFotoSesudah);
        TextView beritaAcara = findViewById(R.id.drafBeritaAcara);



        submitRejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Mengirim Data...");
                pd.show();
                kirimReject(sessionManager.getUser().getRemember_token(),idplaporan, spinner.getSelectedItem().toString());
            }
        });

        meteranSebelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaFotoMeteranSebelum();
            }
        });

        meteranSesudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaFotoMeteranSesudah();

            }
        });

        telpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + fromTaskList.getStringExtra("noTlp")));
                startActivity(intent);
            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + fromTaskList.getStringExtra("alamat"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


        beritaAcara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bukaFotoBeritaAcara();
            }
        });


        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "Waktu mulai pengerjaan telah disimpan", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
                String strDate = sdf.format(c.getTime());

                DwaktuMulai.setText(strDate);

                taskSessionManager.setStartTask(DnoAgenda.getText().toString(), strDate);
                taskSessionManager.setTaskStatus(DnoAgenda.getText().toString(), "1");

                fabreject.hide();
                fab.hide();
                fabComplete.show();

                return true;
            }


        });



        fabComplete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, "Waktu selesai pengerjaan telah disimpan", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
                String strDate = sdf.format(c.getTime());

                DwaktuSelesai.setText(strDate);

                taskSessionManager.setEndTask(DnoAgenda.getText().toString(), strDate);
                taskSessionManager.setTaskStatus(DnoAgenda.getText().toString(), "2");

                fabComplete.hide();
                kirimBtn.setVisibility(View.VISIBLE);


                return true;
            }
        });


        fabreject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Snackbar.make(view, "Tugas berhasil ditolak", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                taskSessionManager.setTaskStatus(DnoAgenda.getText().toString(), "3");
                fab.hide();
                kirimBtn.setVisibility(View.GONE);

                cardBeritaAcara.setVisibility(View.GONE);
                cardMeterSebelum.setVisibility(View.GONE);
                cardMeterSesudah.setVisibility(View.GONE);

                submitRejectBtn.setVisibility(View.VISIBLE);
                spinnerCard.setVisibility(View.VISIBLE);

                fabreject.hide();


                return true;
            }
        });

        kirimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final File file1 = new File(imgPath1);

                if(uriSebelum == null){
                    final Toast toast = Toast.makeText(DetailTaskActivity.this, "Harap Pilih Foto Terlebih Dahulu", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    pd.setMessage("Mengirim Data...");
                    pd.show();
                    submitTask(kwhsebelum, kwhsesudah, beritaacarar, idplaporan);
                }
            }
        });
    }

    protected void submitTask(String kwhlama, String kwhbaru, String beritacaara, String idlaporan){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<SubmitResponse> submitResponseCall = api.submitTask(sessionManager.getUser().getRemember_token(),idlaporan,kwhlama,kwhbaru,beritacaara,DwaktuSelesai.getText().toString());
        submitResponseCall.enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                Log.i("SUCC UPKLAD", response.message());
                final Toast toast = Toast.makeText(DetailTaskActivity.this, "Harap Segera Lakukan Pembaruan Tugas", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                pd.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {
                Log.i("ERR IPSAD", t.getMessage());
                pd.dismiss();

            }
        });

    }

    public void kill(){
        finish();
    }

    protected void bukaFotoMeteranSebelum(){

        selectedImg = 0;

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Foto Meteran Sebelum"), 1);

    }

    protected void bukaFotoMeteranSesudah(){

        selectedImg = 1;

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }

    protected void bukaFotoBeritaAcara(){

        selectedImg = 2;

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                if(selectedImg == 0){
                    uriSebelum = data.getData();
                    imgSebelum.setImageURI(uriSebelum);

                    if (uriSebelum  != null) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriSebelum);
                            kwhsebelum = bitmapToBase64String(bitmap,60);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(selectedImg == 1){
                    uriSesudah = data.getData();
                    imgSesudah.setImageURI(uriSesudah);

                    if (uriSesudah  != null) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriSesudah);
                            kwhsesudah = bitmapToBase64String(bitmap,60);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else if(selectedImg == 2){
                    uriBeritaAcara = data.getData();
                    imgBeritaAcara.setImageURI(uriBeritaAcara);

                    if (uriBeritaAcara  != null) {
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriBeritaAcara);
                            beritaacarar = bitmapToBase64String(bitmap,60);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    protected void kirimReject(String token, String idpelaporan,String msg){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl.getBaseUrl()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<SubmitResponse> call = api.rejectTask(token, idpelaporan, msg);
        call.enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {

                Log.i("SUBMIT REJECT", response.body().getMessage());
                final Toast toast = Toast.makeText(DetailTaskActivity.this, "Harap Segera Lakukan Pembaruan Tugas", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                pd.dismiss();
                finish();

            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {
                Log.i("SUBMIT REJECT FAILUR", t.getMessage());

            }
        });

    }

    protected static String bitmapToBase64String(Bitmap bmp, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}


