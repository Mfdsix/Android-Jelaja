package com.example.wdd_vip.jelaja.AdminView;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Adapter.TopupListAdapter;
import com.example.wdd_vip.jelaja.Adapter.TransactionAdapter;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.TopupRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRequest extends AppCompatActivity {

    ProgressDialog progressDialog;
    ArrayList<JelapayModel> jelapayModels;
    TransactionAdapter transactionAdapter;
    @BindView(R.id.rv_transaction)
    RecyclerView rv_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_request);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Permintaan Transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        getTransaction();
    }

    public void getTransaction()
    {
        ApiJelapay apiJelapay = Retro.createJelapayApi();
        apiJelapay.getAllRequest().enqueue(new Callback<ArrayList<JelapayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<JelapayModel>> call, Response<ArrayList<JelapayModel>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    jelapayModels = new ArrayList<>();
                    jelapayModels = response.body();
                    transactionAdapter = new TransactionAdapter(TransactionRequest.this, jelapayModels);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TransactionRequest.this);
                    rv_transaction.setLayoutManager(layoutManager);
                    rv_transaction.setAdapter(transactionAdapter);
                }
                else
                {
                    Toast.makeText(TransactionRequest.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JelapayModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TransactionRequest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
