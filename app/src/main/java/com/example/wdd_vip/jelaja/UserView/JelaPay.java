package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JelaPay extends AppCompatActivity {

    @BindView(R.id.t_balance)
    TextView t_balance;
    ProgressDialog progressDialog;
    Boolean pressedTwice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jela_pay);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Jelapay");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id",0);

        FloatingActionButton fab_topup = (FloatingActionButton) findViewById(R.id.fab_topup);
        fab_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JelaPay.this , TopUpJela.class));
            }
        });

        set_my_balance(userid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                startActivity(new Intent(JelaPay.this, DrawerActivity.class));
                return true;
            default :
            return super.onOptionsItemSelected(item);
        }
    }

    public void show_my_request(View v)
    {
        startActivity(new Intent(JelaPay.this, TopupRequest.class));
    }
    public void show_my_history(View v)
    {
        startActivity(new Intent(JelaPay.this, TopupHistory.class));
    }
    public void set_my_balance(int userid)
    {
        View view = getLayoutInflater().from(JelaPay.this).inflate(R.layout.activity_jela_pay, null);
        ApiJelapay apiJelapay = Retro.createJelapayApi();
        apiJelapay.getMyBalance(userid).enqueue(new Callback<JelapayModel>() {
            @Override
            public void onResponse(Call<JelapayModel> call, Response<JelapayModel> response) {
                if(response.isSuccessful())
                {
                    t_balance.setText("Rp "+response.body().getAmount());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JelapayModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(view, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
