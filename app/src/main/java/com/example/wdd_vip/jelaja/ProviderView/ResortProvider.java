package com.example.wdd_vip.jelaja.ProviderView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResortProvider extends AppCompatActivity {

    @BindView(R.id.t_r_description)
    TextView t_r_description;
    @BindView(R.id.t_r_day)
    TextView t_r_day;
    @BindView(R.id.t_r_time)
    TextView t_r_time;
    @BindView(R.id.t_r_price)
    TextView t_r_price;
    @BindView(R.id.fab_r_edit)
    FloatingActionButton fab_r_edit;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resort_provider);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id", 0);

        getResotDetail(userid);

        fab_r_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResortProvider.this, EditResort.class));
            }
        });
    }

    public void getResotDetail(int userid)
    {
        Api apiService = Retro.getResort();
        apiService.getResort(userid).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    t_r_description.setText(response.body().getDescription());
                    t_r_day.setText(response.body().getSimpleDay());
                    t_r_time.setText(response.body().getSimpleTime());
                    t_r_price.setText(response.body().getPrice());
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
