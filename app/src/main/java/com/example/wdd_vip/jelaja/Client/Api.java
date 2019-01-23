package com.example.wdd_vip.jelaja.Client;

import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {

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
    @POST("auth/login.php")
    Call<AuthModel> loginAuth(
            @Field("username") String name,
            @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("auth/activate.php")
    Call<AuthModel> activatedUser(
            @Field("id_user") int id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("schedule/add.php")
    Call<AuthModel> addSchedule(
            @Field("user") int userid,
            @Field("source") String source,
            @Field("destination") String destination,
            @Field("go_at") String date,
            @Field("est_time") String time,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("schedule/resort.php")
    Call<AuthModel> addResort(
            @Field("user") int userid,
            @Field("description") String description,
            @Field("open_at") String open,
            @Field("close_at") String close,
            @Field("open_day") String day,
            @Field("price") String price
    );


    @FormUrlEncoded
    @POST("auth/register.php")
    Call<AuthModel> signupAuth(
            @Field("username") String name,
            @Field("password") String pass,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("auth/detail.php")
    Call<AuthModel> getDetailUser(
            @Field("id_user") int userid
    );

    @FormUrlEncoded
    @POST("auth/edit.php")
    Call<AuthModel> updateUser(
            @Field("user") int userid,
            @Field("phone") String phone,
            @Field("email") String email,
            @Field("address") String address,
            @Field("factory_name") String name,
            @Field("sector") String sector
    );

    @FormUrlEncoded
    @POST("auth/edit.php")
    Call<AuthModel> updateMainUser(
            @Field("user") int userid,
            @Field("phone") String phone,
            @Field("full_name") String fullname,
            @Field("address") String address,
            @Field("bank_name") String bank,
            @Field("bank_number") String number
    );

    @FormUrlEncoded
    @POST("auth/edit.php")
    Call<AuthModel> setService(
            @Field("user") int userid,
            @Field("sector") String service
    );

    @FormUrlEncoded
    @POST("schedule/mydata.php")
    Call<ArrayList<AuthModel>> getMySchedule(
            @Field("user") int userid
    );

    @FormUrlEncoded
    @POST("schedule/vehicle.php")
    Call<ArrayList<AuthModel>> getVehicleSchedule(
            @Field("source") String source,
            @Field("destination") String destination,
            @Field("date") String date,
            @Field("passenger") String passenger
    );

    @FormUrlEncoded
    @POST("schedule/detail.php")
    Call<AuthModel> getDetailSchedule(
            @Field("id_schedule") int schedule_id
    );

    @FormUrlEncoded
    @POST("schedule/hotel.php")
    Call<ArrayList<AuthModel>> getHotelSchedule(
            @Field("city") String city,
            @Field("date") String date,
            @Field("passenger") String passenger,
            @Field("room") String room,
            @Field("night") String night
    );

    @FormUrlEncoded
    @POST("schedule/search_resort.php")
    Call<ArrayList<AuthModel>> getResortSchedule(
            @Field("city") String city,
            @Field("date") String date,
            @Field("passenger") String passenger
    );

    @FormUrlEncoded
    @POST("schedule/book.php")
    Call<AuthModel> bookVehicle(
            @Field("user") int user,
            @Field("schedule") int vehicle,
            @Field("passenger") int passenger,
            @Field("price") int price
    );

    @FormUrlEncoded
    @POST("schedule/book.php")
    Call<AuthModel> bookHotel(
            @Field("user") int user,
            @Field("schedule") int vehicle,
            @Field("passenger") int passenger,
            @Field("room") int room,
            @Field("night") int night,
            @Field("price") int price
    );

    @FormUrlEncoded
    @POST("schedule/getresort.php")
    Call<AuthModel> getResort(
            @Field("id_user") int user
    );

    @FormUrlEncoded
    @POST("schedule/editresort.php")
    Call<AuthModel> editResort(
            @Field("user") int userid,
            @Field("description") String description,
            @Field("open_at") String open,
            @Field("close_at") String close,
            @Field("open_day") String day,
            @Field("price") String price
    );

    @FormUrlEncoded
    @POST("jelapay/topup.php")
    Call<AuthModel> topUp(
            @Field("user") int user,
            @Field("amount") String amount,
            @Field("kind") String kind,
            @Field("status") String status
    );


    @GET("provider/request.php")
    Call<ArrayList<AuthModel>> getProviderRequest(
    );

}

