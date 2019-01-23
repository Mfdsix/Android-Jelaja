package com.example.wdd_vip.jelaja.UserView;

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
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutVehicle extends AppCompatActivity {

    @BindView(R.id.t_company)
    TextView t_company;
    @BindView(R.id.t_sector)
    TextView t_sector;
    @BindView(R.id.t_address)
    TextView t_address;
    @BindView(R.id.t_route)
    TextView t_route;
    @BindView(R.id.t_date)
    TextView t_date;
    @BindView(R.id.t_passenger)
    TextView t_passenger;
    @BindView(R.id.t_price)
    TextView t_price;
    @BindView(R.id.t_saldo)
    TextView t_saldo;
    @BindView(R.id.t_sisa)
    TextView t_sisa;
    @BindView(R.id.b_book)
    Button b_book;
    ProgressDialog progressDialog;
    int price;
    int saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_vehicle);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id",0);

        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setMyTextView(userid);

        b_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(price>saldo)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutVehicle.this);
                    builder.setTitle("Maaf");
                    builder.setMessage("Saldo Jelapay anda saat ini tidak mencukupi untuk memenuhi  tagihan transaksi ini.");
                    builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(CheckoutVehicle.this,JelaPay.class));
                        }
                    });
                    builder.show();
                }
                else
                {
                    Api api = Retro.createApi();
                    api.bookVehicle(userid,getIntent().getIntExtra("id_schedule", 0)
                            , getIntent().getIntExtra("passenger"
                                    ,0), price)
                    .enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            if(response.isSuccessful() && response.body().getMessage().equals("200"))
                            {
                                Snackbar.make(v, "Booking Sukses", Snackbar.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(CheckoutVehicle.this, JelaPay.class));
                                    }
                                },2000);
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
            }
        });
    }

    public void setMyTextView(int userid)
    {
        t_company.setText(getIntent().getStringExtra("company"));
        t_sector.setText(getIntent().getStringExtra("sector"));
        t_address.setText(getIntent().getStringExtra("address"));
        t_route.setText(getIntent().getStringExtra("route"));
        t_date.setText(getIntent().getStringExtra("date"));
        t_passenger.setText(String.valueOf(getIntent().getIntExtra("passenger",0)));
        System.out.println(getIntent().getIntExtra("passenger",0));
        t_price.setText("Rp"+getIntent().getStringExtra("price"));

        ApiJelapay apiJelapay = Retro.createJelapayApi();
        apiJelapay.getMyBalance(userid).enqueue(new Callback<JelapayModel>() {
            @Override
            public void onResponse(Call<JelapayModel> call, Response<JelapayModel> response) {
                if(response.isSuccessful())
                {
                    t_saldo.setText("Rp "+ response.body().getAmount());
                    price = Integer.valueOf(getIntent().getStringExtra("price").replace(".",""));
                    saldo = Integer.valueOf(response.body().getAmount().replace(".",""));
                    Locale localeID = new Locale("in", "ID");
                    NumberFormat format = NumberFormat.getCurrencyInstance(localeID);
                    t_sisa.setText(format.format((double) saldo-price));
                }
            }

            @Override
            public void onFailure(Call<JelapayModel> call, Throwable t) {

            }
        });
        progressDialog.dismiss();
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
