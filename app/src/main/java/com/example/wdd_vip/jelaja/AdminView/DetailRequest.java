package com.example.wdd_vip.jelaja.AdminView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ProviderProfileActivity;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.DrawerActivity;
import com.example.wdd_vip.jelaja.UserView.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRequest extends AppCompatActivity {

    @BindView(R.id.t_username)
    TextView t_username;
    @BindView(R.id.t_company)
    TextView t_company;
    @BindView(R.id.t_address)
    TextView t_address;
    @BindView(R.id.t_email)
    TextView t_email;
    @BindView(R.id.t_phone)
    TextView t_phone;
    @BindView(R.id.b_aktif)
    Button b_aktif;
    @BindView(R.id.b_blok)
    Button b_blok;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Pemintaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        int userid = getIntent().getIntExtra("id_user", 0);
        getUser(userid);

        b_aktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequest.this);
                builder.setTitle("Aktifkan Akun");
                builder.setMessage("Apakah anda yakin ingin mengaktifkan akun ini ?");
                builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Api apiService = Retro.activatedUser();
                        apiService.activatedUser(userid, "A").enqueue(new Callback<AuthModel>() {
                            @Override
                            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                                if(response.isSuccessful())
                                {
                                    Snackbar.make(v, "Akun Diaktifkan", Snackbar.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(DetailRequest.this, ProviderRequest.class);
                                            startActivity(i);
                                        }
                                    }, 2000);
                                }
                            }

                            @Override
                            public void onFailure(Call<AuthModel> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        b_blok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailRequest.this);
                builder.setTitle("Blok Akun");
                builder.setMessage("Apakah anda yakin ingin memblok akun ini ?");
                builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Api apiService = Retro.activatedUser();
                        apiService.activatedUser(userid, "B").enqueue(new Callback<AuthModel>() {
                            @Override
                            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                                if(response.isSuccessful())
                                {
                                    Snackbar.make(v, "Akun Diblok", Snackbar.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(DetailRequest.this, ProviderRequest.class);
                                            startActivity(i);
                                        }
                                    },2000);
                                }
                            }

                            @Override
                            public void onFailure(Call<AuthModel> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    public void getUser(int userid)
    {
        Api apiServices = Retro.getDetailUser();
        apiServices.getDetailUser(userid).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    t_username.setText(response.body().getName());
                    t_company.setText(response.body().getCompany_name());
                    t_address.setText(response.body().getAddress());
                    t_email.setText(response.body().getEmail());
                    t_phone.setText(response.body().getPhone());
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                progressDialog.dismiss();
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
