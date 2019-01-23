package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Adapter.TopupListAdapter;
import com.example.wdd_vip.jelaja.Adapter.VehicleAdapter;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupRequest extends AppCompatActivity {


    @BindView(R.id.rv_topup_request)
    RecyclerView rv_topup_request;
    ProgressDialog progressDialog;
    ArrayList<JelapayModel> jelapayModel;
    TopupListAdapter topupListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_request);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.show();

        getSupportActionBar().setTitle("Permintaan Topup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id", 0);

        get_topup_request(userid);
    }

    public void get_topup_request(int userid)
    {
        ApiJelapay apiJelapay = Retro.createJelapayApi();
        apiJelapay.getMyRequest(userid).enqueue(new Callback<ArrayList<JelapayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<JelapayModel>> call, Response<ArrayList<JelapayModel>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    jelapayModel = new ArrayList<>();
                    jelapayModel = response.body();
                    System.out.println(response.body());
                    topupListAdapter = new TopupListAdapter(TopupRequest.this, jelapayModel);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TopupRequest.this);
                    rv_topup_request.setLayoutManager(layoutManager);
                    rv_topup_request.setAdapter(topupListAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JelapayModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TopupRequest.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
