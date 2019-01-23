package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Adapter.HotelAdapter;
import com.example.wdd_vip.jelaja.Adapter.ResortAdapter;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResort extends AppCompatActivity {

    @BindView(R.id.rv_resort)
    RecyclerView rv_resort;
    ArrayList<AuthModel> authModelArrayList;
    ResortAdapter resortAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_resort);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String city = (String) getIntent().getStringExtra("city");
        String date = (String) getIntent().getStringExtra("date");
        String person = (String) getIntent().getStringExtra("person");

        getSupportActionBar().setTitle("Pencarian Wisata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("di " + city+ " untuk " + person + " orang");

        getResort(city, date, person, rv_resort);
    }

    protected void getResort(String city, String date, String person, View v)
    {
        final Api apiServices = Retro.getResortSchedule();
        apiServices.getResortSchedule(city, date, person).enqueue(new Callback<ArrayList<AuthModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AuthModel>> call, Response<ArrayList<AuthModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().get(0).getMessage().equals("0"))
                    {
                        Snackbar.make(v, "Tidak Ditemukan Data", Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        authModelArrayList = new ArrayList<>();
                        authModelArrayList = response.body();
                        resortAdapter = new ResortAdapter(SearchResort.this, authModelArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchResort.this);
                        rv_resort.setLayoutManager(layoutManager);
                        rv_resort.setAdapter(resortAdapter);
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
                Snackbar.make(v, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                super.onBackPressed();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
