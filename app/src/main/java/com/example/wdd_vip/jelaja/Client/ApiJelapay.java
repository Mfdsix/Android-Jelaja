package com.example.wdd_vip.jelaja.Client;

import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiJelapay {

    @Headers({
            "Content-Type: application/json"
    })

    @FormUrlEncoded
    @POST("auth/login.php")
    Call<AuthModel> logAuth(
            @Field("username") String name,
            @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("jelapay/myrequest.php")
    Call<ArrayList<JelapayModel>> getMyRequest(
            @Field("user") int user
    );

    @FormUrlEncoded
    @POST("jelapay/myhistory.php")
    Call<ArrayList<JelapayModel>> getMyHistory(
            @Field("user") int user
    );

    @Multipart
    @POST("jelapay/rich.php")
    Call<AuthModel> uploadRich(
            @Part MultipartBody.Part image,
            @Part("id_ptransaction") RequestBody id_ptransaction
            );

    @GET("jelapay/allrequest.php")
    Call<ArrayList<JelapayModel>> getAllRequest();

    @FormUrlEncoded
    @POST("jelapay/activate.php")
    Call<JelapayModel> activate(
            @Field("id_ptransaction") int id_ptransaction,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("jelapay/balance.php")
    Call<JelapayModel> getMyBalance(
            @Field("user") int user
    );

    @FormUrlEncoded
    @POST("jelapay/me.php")
    Call<ArrayList<JelapayModel>> getMyTransaction(
            @Field("user") int user
    );

    @Multipart
    @POST("auth/foto.php")
    Call<AuthModel> uploadPhoto(
            @Part MultipartBody.Part image,
            @Part("user") RequestBody user
    );
}

