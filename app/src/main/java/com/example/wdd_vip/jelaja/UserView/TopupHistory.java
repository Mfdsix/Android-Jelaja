package com.example.wdd_vip.jelaja.UserView;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.wdd_vip.jelaja.Adapter.TopupHistoryAdapter;
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

public class TopupHistory extends AppCompatActivity {

    @BindView(R.id.rv_history)
    RecyclerView rv_history;
    ArrayList<JelapayModel> jelapayModels;
    TopupHistoryAdapter topupHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_history);
        ButterKnife.bind(this);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = sharedPreferences.getInt("jelaja_id", 0);

        getSupportActionBar().setTitle("History Topup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        get_my_history(userid);
    }

    public void get_my_history(int userid)
    {
        ApiJelapay apiJelapay = Retro.createJelapayApi();
        apiJelapay.getMyHistory(userid).enqueue(new Callback<ArrayList<JelapayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<JelapayModel>> call, Response<ArrayList<JelapayModel>> response) {
                if(response.isSuccessful())
                {
                    jelapayModels = new ArrayList<>();
                    jelapayModels = response.body();
                    topupHistoryAdapter = new TopupHistoryAdapter(TopupHistory.this, jelapayModels);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TopupHistory.this);
                    rv_history.setLayoutManager(layoutManager);
                    rv_history.setAdapter(topupHistoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JelapayModel>> call, Throwable t) {

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
