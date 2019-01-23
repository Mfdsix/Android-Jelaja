package com.example.wdd_vip.jelaja.ProviderView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.LoginActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditResort extends AppCompatActivity {

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePicker;
    @BindView(R.id.i_r_description)
    EditText i_r_description;
    @BindView(R.id.i_r_dmon)
    EditText i_r_dmon;
    @BindView(R.id.i_r_dtue)
    EditText i_r_dtue;
    @BindView(R.id.i_r_dwed)
    EditText i_r_dwed;
    @BindView(R.id.i_r_dthu)
    EditText i_r_dthu;
    @BindView(R.id.i_r_dfri)
    EditText i_r_dfri;
    @BindView(R.id.i_r_dsat)
    EditText i_r_dsat;
    @BindView(R.id.i_r_dsun)
    EditText i_r_dsun;
    @BindView(R.id.i_r_price)
    EditText i_r_price;
    @BindView(R.id.i_r_open)
    EditText i_r_open;
    @BindView(R.id.i_r_close)
    EditText i_r_close;
    @BindView(R.id.btn_save)
    EditText btn_save;
    String open_day;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resort);
        ButterKnife.bind(this);

        open_day = "";
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        getSupportActionBar().setTitle("Edit Wisata");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id", 0);

        i_r_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(EditResort.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        i_r_open.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        i_r_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(EditResort.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        i_r_close.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(i_r_description.getText().toString().equals("") || i_r_price.getText().toString().equals("")
                        || i_r_open.getText().toString().equals("") || i_r_close.getText().toString().equals("")
                        || ( !i_r_dmon.isSelected() && !i_r_dtue.isSelected() && !i_r_dwed.isSelected()
                        && !i_r_dthu.isSelected() && !i_r_dfri.isSelected() && !i_r_dsat.isSelected() && !i_r_dsun.isSelected() ))
                {
                    Snackbar.make(v, "Semua Field Harus Diisi", Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    progressDialog.show();

                    open_day += (i_r_dmon.isSelected()) ? "Senin," : "";
                    open_day += (i_r_dtue.isSelected()) ? "Selasa," : "";
                    open_day += (i_r_dwed.isSelected()) ? "Rabu," : "";
                    open_day += (i_r_dthu.isSelected()) ? "Kamis," : "";
                    open_day += (i_r_dfri.isSelected()) ? "Jumat," : "";
                    open_day += (i_r_dsat.isSelected()) ? "Sabtu," : "";
                    open_day += (i_r_dsun.isSelected()) ? "Minggu," : "";

                    Api apiService = Retro.editResort();
                    apiService.editResort(userid, i_r_description.getText().toString(), i_r_open.getText().toString(),
                            i_r_close.getText().toString(), open_day, i_r_price.getText().toString()).enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            progressDialog.dismiss();
                            if(response.isSuccessful())
                            {
                                if(response.body().getMessage().equals("200"))
                                {
                                    Snackbar.make(v, "Data Sukses Diubah", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Snackbar.make(v, response.message(), Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthModel> call, Throwable t) {
                            progressDialog.dismiss();
                            Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void getResortDetail(int userid)
    {
        Api apiService = Retro.getResort();
        apiService.getResort(userid).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    i_r_description.setText(response.body().getDescription());
                    i_r_open.setText(response.body().getTime());
                    i_r_close.setText(response.body().getCloseTime());
                    i_r_price.setText(response.body().getPrice());
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {

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
