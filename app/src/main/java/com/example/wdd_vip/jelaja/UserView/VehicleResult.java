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

import com.example.wdd_vip.jelaja.Adapter.ScheduleAdapter;
import com.example.wdd_vip.jelaja.Adapter.VehicleAdapter;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ScheduleProvier;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleResult extends AppCompatActivity {

    @BindView(R.id.rv_vehicle)
    RecyclerView rv_vehicle;
    ArrayList<AuthModel> authModelArrayList;
    VehicleAdapter vehicleAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_result);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String source = (String) getIntent().getStringExtra("source");
        String destination = (String) getIntent().getStringExtra("destination");
        String date = (String) getIntent().getStringExtra("date");
        String passenger = (String) getIntent().getStringExtra("passenger");

        getSupportActionBar().setTitle("Pencarian Kendaraan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(source + " - " + destination + ", "+ passenger +" orang");

        getVehicle(source, destination, date, passenger, rv_vehicle);
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

    protected void getVehicle(String source, String destination, String date, String passenger, View v)
    {
        final Api apiServices = Retro.getVehicleSchedule();
        apiServices.getVehicleSchedule(source, destination, date, passenger).enqueue(new Callback<ArrayList<AuthModel>>() {
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
                        vehicleAdapter = new VehicleAdapter(VehicleResult.this, authModelArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(VehicleResult.this);
                        rv_vehicle.setLayoutManager(layoutManager);
                        rv_vehicle.setAdapter(vehicleAdapter);
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
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
