package com.example.wdd_vip.jelaja.ProviderView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.wdd_vip.jelaja.Adapter.ScheduleAdapter;
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

public class ScheduleProvier extends AppCompatActivity {

    @BindView(R.id.provider_rv)
    RecyclerView provider_rv;
    ArrayList<AuthModel> authModelArrayList;
    ScheduleAdapter scheduleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_provier);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Jadwal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);

        getSchedule(userid);


        FloatingActionButton fab_save = (FloatingActionButton) findViewById(R.id.fab_addschedule);

        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                String sector = spref.getString("jelaja_sector", "");

                if(sector.equals("Hotel"))
                {
                    startActivity(new Intent(ScheduleProvier.this, AddHotelSchedule.class));
                }
                else if(sector.equals(""))
                {
                    startActivity(new Intent(ScheduleProvier.this, AfterRegister.class));
                }
                else
                {
                    startActivity(new Intent(ScheduleProvier.this, AddSchedule.class));
                }
            }
        });

    }
    private void getSchedule(int userid) {
        final Api apiServices = Retro.getMySchedule();
        apiServices.getMySchedule(userid).enqueue(new Callback<ArrayList<AuthModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AuthModel>> call, Response<ArrayList<AuthModel>> response) {
                if (response.isSuccessful()) {
                    authModelArrayList = new ArrayList<>();
                    authModelArrayList = response.body();
                    scheduleAdapter = new ScheduleAdapter(ScheduleProvier.this, authModelArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ScheduleProvier.this);
                    provider_rv.setLayoutManager(layoutManager);
                    provider_rv.setAdapter(scheduleAdapter);
                }
                else
                {
                    Snackbar.make(provider_rv, "Terjadi Kesalahan", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AuthModel>> call, Throwable t) {
                Snackbar.make(provider_rv,"Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                startActivity(new Intent(ScheduleProvier.this, ProviderActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
