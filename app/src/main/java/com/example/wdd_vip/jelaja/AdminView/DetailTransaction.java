package com.example.wdd_vip.jelaja.AdminView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Config.Config;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DetailTransaction extends AppCompatActivity {

    ProgressDialog progressDialog;
    @BindView(R.id.t_name)
    TextView t_name;
    @BindView(R.id.t_date)
    TextView t_date;
    @BindView(R.id.t_amount)
    TextView t_amount;
    @BindView(R.id.i_rich)
    ImageView i_rich;
    @BindView(R.id.b_confirm)
    Button b_confirm;
    @BindView(R.id.b_refuse)
    Button b_refuse;
    int swidth, sheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaction);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        int id_ptransaction = getIntent().getIntExtra("id_ptransaction",0);
        String name = getIntent().getStringExtra("username");
        String amount = getIntent().getStringExtra("amount");
        String date = getIntent().getStringExtra("date");
        String rich_url = getIntent().getStringExtra("rich_url");

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        swidth = size.x;
        sheight = size.y;

        show_detail(name, amount, date, rich_url);

        b_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTransaction.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin mengonfirmasi pembayaran ini ?");
                builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        ApiJelapay apiJelapay = Retro.createJelapayApi();
                        apiJelapay.activate(id_ptransaction, "Success").enqueue(new Callback<JelapayModel>() {
                            @Override
                            public void onResponse(Call<JelapayModel> call, Response<JelapayModel> response) {
                                if(response.isSuccessful())
                                {
                                    Snackbar.make(v, "Pembayaran Disetujui", Snackbar.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(DetailTransaction.this, TransactionRequest.class);
                                            startActivity(i);
                                        }
                                    }, 2000);
                                }
                            }

                            @Override
                            public void onFailure(Call<JelapayModel> call, Throwable t) {

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

        b_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailTransaction.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menolak pembayaran ini ?");
                builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        ApiJelapay apiJelapay = Retro.createJelapayApi();
                        apiJelapay.activate(id_ptransaction, "Refused").enqueue(new Callback<JelapayModel>() {
                            @Override
                            public void onResponse(Call<JelapayModel> call, Response<JelapayModel> response) {
                                if(response.isSuccessful())
                                {
                                    Snackbar.make(v, "Pembayaran Ditolak", Snackbar.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent i = new Intent(DetailTransaction.this, TransactionRequest.class);
                                            startActivity(i);
                                        }
                                    }, 2000);
                                }
                            }

                            @Override
                            public void onFailure(Call<JelapayModel> call, Throwable t) {

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

    public void show_detail(String name, String amount, String date, String rich_url)
    {
        String full_url = Config.BASE_URL+"upload/rich/"+rich_url;
        t_name.setText(name);
        t_amount.setText("Rp "+amount);
        t_date.setText(date);
        Glide.with(DetailTransaction.this).load(full_url).override(swidth,400).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(i_rich);
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
