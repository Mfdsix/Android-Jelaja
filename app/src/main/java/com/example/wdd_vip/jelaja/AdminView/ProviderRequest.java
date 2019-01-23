package com.example.wdd_vip.jelaja.AdminView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Adapter.ProviderAdapter;
import com.example.wdd_vip.jelaja.Adapter.VehicleAdapter;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.VehicleResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderRequest extends AppCompatActivity {

    @BindView(R.id.rv_provider)
    RecyclerView rv_provider;
    ArrayList<AuthModel> authModelArrayList;
    ProviderAdapter providerAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_request);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Permintaan Provider");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();
        getProviderRequest();

    }

    protected void getProviderRequest()
    {
        final Api apiServices = Retro.getProviderRequest();
        apiServices.getProviderRequest().enqueue(new Callback<ArrayList<AuthModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AuthModel>> call, Response<ArrayList<AuthModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    authModelArrayList = new ArrayList<>();
                    authModelArrayList = response.body();
                    providerAdapter = new ProviderAdapter(ProviderRequest.this, authModelArrayList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ProviderRequest.this);
                    rv_provider.setLayoutManager(layoutManager);
                    rv_provider.setAdapter(providerAdapter);
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<AuthModel>> call, Throwable t) {
            progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                startActivity(new Intent(ProviderRequest.this, AdminDashboard.class));
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
