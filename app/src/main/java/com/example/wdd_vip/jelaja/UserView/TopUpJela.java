package com.example.wdd_vip.jelaja.UserView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopUpJela extends AppCompatActivity {

    @BindView(R.id.rg_amount_top)
    RadioGroup rg_amount_top;
    @BindView(R.id.btn_topup)
    Button btn_topup;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_jela);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Topup Jelapay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sp = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sp.getInt("jelaja_id", 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);

        btn_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radio_id = rg_amount_top.getCheckedRadioButtonId();
                RadioButton rb_amount = (RadioButton) findViewById(radio_id);
                String amount = (String) rb_amount.getText().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(TopUpJela.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda akan melakukan topup dengan jumlah Rp" + amount +" ?");
                builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        progressDialog.show();
                        Api apiServices = Retro.topUp();
                        apiServices.topUp(userid, amount,"In", "Pending").enqueue(new Callback<AuthModel>() {
                            @Override
                            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                                if (response.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Snackbar.make(v, "Permintaan Topup Berhasil", Snackbar.LENGTH_SHORT).show();
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(TopUpJela.this, TopupRequest.class));
                                        }
                                    },2000);
                                }
                            }
                            @Override
                            public void onFailure(Call<AuthModel> call, Throwable t) {
                                Toast.makeText(TopUpJela.this, "Periksa koneksi internet", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                onBackPressed();
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
