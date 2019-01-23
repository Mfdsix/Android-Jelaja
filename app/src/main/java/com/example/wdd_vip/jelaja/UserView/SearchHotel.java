package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Adapter.HotelAdapter;
import com.example.wdd_vip.jelaja.Adapter.VehicleAdapter;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchHotel extends AppCompatActivity {

    @BindView(R.id.rv_hotel)
    RecyclerView rv_hotel;
    ArrayList<AuthModel> authModelArrayList;
    HotelAdapter hotelAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hotel);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();


        String city = (String) getIntent().getStringExtra("city");
        String date = (String) getIntent().getStringExtra("date");
        String person = (String) getIntent().getStringExtra("person");
        String room = (String) getIntent().getStringExtra("room");
        String night = (String) getIntent().getStringExtra("night");

        getSupportActionBar().setTitle("Pencarian Hotel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(city+ ", " + person + " orang, " + night + " malam, "+room+" kamar");

        getHotel(city, date, person, room, night, rv_hotel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                super.onBackPressed();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

    }

    protected void getHotel(String city, String date, String person, String room, String night, View v)
    {
        final Api apiServices = Retro.getHotelSchedule();
        apiServices.getHotelSchedule(city, date, person, room, night).enqueue(new Callback<ArrayList<AuthModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AuthModel>> call, Response<ArrayList<AuthModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().get(0).getMessage().equals("0"))
                    {
                        Snackbar.make(v, "Tidak Ditemukan Data", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                    authModelArrayList = new ArrayList<>();
                    authModelArrayList = response.body();
                    hotelAdapter = new HotelAdapter(SearchHotel.this, authModelArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchHotel.this);
                    rv_hotel.setLayoutManager(layoutManager);
                    rv_hotel.setAdapter(hotelAdapter);
                }
                }
                else
                {
                    Snackbar.make(v, "Gagal Memuat Data", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AuthModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(v, "Periksa Koneksi Internet",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
