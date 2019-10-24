package com.example.coba.myreport.Api;

import com.example.coba.myreport.Response.LoginResponse;
import com.example.coba.myreport.Response.SubmitResponse;
import com.example.coba.myreport.Response.TaskResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("token_hp") String token_hp
    );

    @FormUrlEncoded
    @POST("order")
    Call<TaskResponse> getTask(
            @Field("token") String token
    );


//    @Multipart
//    @POST("doorder")
//    Call<SubmitResponse> uploadPhotoMultipart(
//            @Part("token") RequestBody token,
//            @Part("id_laporan") RequestBody id_laporan,
//            @Part MultipartBody.Part photo1,
//            @Part MultipartBody.Part photo2,
//            @Part MultipartBody.Part photo3);

    @FormUrlEncoded
    @POST("doorder")
    Call<SubmitResponse> submitTask(
            @Field("token") String token,
            @Field("id_laporan") String id_laporan,
            @Field("kwhlama") String kwhlama,
            @Field("kwhbaru") String kwhbaru,
            @Field("beritaacara") String beritaacara,
            @Field("waktu_selesai") String waktu_selesai

    );

    @FormUrlEncoded
    @POST("cancelorder")
    Call<SubmitResponse> rejectTask(
            @Field("token") String token,
            @Field("id_laporan") String id_laporan,
            @Field("alasan") String alasan
    );


    @FormUrlEncoded
    @POST("cektokenhp")
    Call<SubmitResponse> cektoTenHp(
            @Field("username") String username
    );

//    @GET("kirimreject.json")
//    Call<SubmitResponse> kirimReject();

//    @Multipart
//    @POST("upload.json")
//    Call<SubmitResponse> uploadPhotoMultipart(
//            @Part("action") RequestBody action,
//            @Part MultipartBody.Part photo1,
//            @Part MultipartBody.Part photo2,
//            @Part MultipartBody.Part photo3);



}
