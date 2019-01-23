package com.example.wdd_vip.jelaja.Client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.wdd_vip.jelaja.Config.Config.BASE_URL;

public class Retro {

    private static Retrofit retro = null;
    private static final String base_url = BASE_URL;

    private static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retro == null) {
            retro = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
        }

        return retro;
    }

    public static ApiJelapay createJelapayApi()
    {
        return getClient().create(ApiJelapay.class);
    }
    public static Api createApi()
    {
        return getClient().create(Api.class);
    }


//    user only
    public static Api signupAuth() {
        return getClient().create(Api.class);
    }
    public static Api loginAuth() {
        return getClient().create(Api.class);
    }
    public static Api getDetailUser() {
        return getClient().create(Api.class);
    }
    public static Api updateUser() { return getClient().create(Api.class); }
    public static Api updateMainUser() {
        return getClient().create(Api.class);
    }
    public static Api setService() {
        return getClient().create(Api.class);
    }
    public static Api activatedUser() {
        return getClient().create(Api.class);
    }

    //    schedule only

    public static Api addSchedule() {
        return getClient().create(Api.class);
    }
    public static Api getMySchedule() {
        return getClient().create(Api.class);
    }
    public static Api getVehicleSchedule() {
        return getClient().create(Api.class);
    }
    public static Api getResortSchedule() {
        return getClient().create(Api.class);
    }
    public static Api getHotelSchedule() {
        return getClient().create(Api.class);
    }
    public static Api bookVehicle() {
        return getClient().create(Api.class);
    }
    public static Api bookHotel() {
        return getClient().create(Api.class);
    }
    public static Api getDetailSchedule() {
        return getClient().create(Api.class);
    }

//    jelapay only
    public static Api topUp() {
    return getClient().create(Api.class);
}
    public static Api uploadRich() {
        return getClient().create(Api.class);
    }
    public static Api addResort() {
        return getClient().create(Api.class);
    }
    public static Api getResort() {
        return getClient().create(Api.class);
    }
    public static Api editResort() {
        return getClient().create(Api.class);
    }

//    provider only
public static Api getProviderRequest() {
    return getClient().create(Api.class);
}
}
