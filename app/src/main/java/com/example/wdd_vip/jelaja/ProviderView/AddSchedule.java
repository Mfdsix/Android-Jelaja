package com.example.wdd_vip.jelaja.ProviderView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSchedule extends AppCompatActivity {

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        EditText e_s_date = (EditText) findViewById(R.id.e_s_date1);
        EditText e_s_hour = (EditText) findViewById(R.id.e_s_date2);
        EditText e_s_time= (EditText) findViewById(R.id.e_s_time);
        EditText e_s_price = (EditText) findViewById(R.id.e_s_price);
        EditText e_s_source = (EditText) findViewById(R.id.e_s_source);
        EditText e_s_destination = (EditText) findViewById(R.id.e_s_destination);

        getSupportActionBar().setTitle("Tambah Jadwal");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar = Calendar.getInstance();
        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String dateFormat = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat,Locale.US);
                e_s_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        e_s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddSchedule.this, datePicker, calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) ).show();
            }
        });

        e_s_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(AddSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        e_s_hour.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        Button btn_save = (Button) findViewById(R.id.b_save_schedule);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                int userid = spref.getInt("jelaja_id", 0);
                Api apiServices = Retro.addSchedule();
                apiServices.addSchedule(userid, e_s_source.getText().toString(), e_s_destination.getText().toString(),e_s_date.getText().toString()+" "+e_s_hour.getText().toString(), e_s_time.getText().toString(), e_s_price.getText().toString()).enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        if (response.isSuccessful()) {
                            if(response.body().getMessage().equals("200"))
                            {
                                Snackbar.make(v, "Berhasil Ditambahkan", Snackbar.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(AddSchedule.this, ScheduleProvier.class));
                                    }
                                },2000);
                            }
                        }
                        else
                        {
                            Snackbar.make(v, "Terjadi Kesalahan", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        Snackbar.make(v, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                startActivity(new Intent(AddSchedule.this, ScheduleProvier.class));
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
