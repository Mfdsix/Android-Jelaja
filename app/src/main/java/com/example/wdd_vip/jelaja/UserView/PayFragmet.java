package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdd_vip.jelaja.Adapter.HotelAdapter;
import com.example.wdd_vip.jelaja.Adapter.SuccessAdapter;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayFragmet extends Fragment {

    ProgressDialog progressDialog;
    RecyclerView rv_transaction;
    ArrayList<JelapayModel> authModelArrayList;
    SuccessAdapter successAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pay, container,false);
        rv_transaction = v.findViewById(R.id.rv_transaction);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();
        rv_transaction = v.findViewById(R.id.rv_transaction);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        int user = sharedPreferences.getInt("jelaja_id",0);
        final ApiJelapay apiServices = Retro.createJelapayApi();
        apiServices.getMyTransaction(user).enqueue(new Callback<ArrayList<JelapayModel>>() {
            @Override
            public void onResponse(Call<ArrayList<JelapayModel>> call, Response<ArrayList<JelapayModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().get(0).getMessage().equals("0"))
                    {
                        Snackbar.make(rv_transaction, "Tidak Ditemukan Data", Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        System.out.println(response.body().toString());
                        authModelArrayList = new ArrayList<>();
                        authModelArrayList = response.body();
                        successAdapter = new SuccessAdapter(getContext(), authModelArrayList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        rv_transaction.setLayoutManager(layoutManager);
                        rv_transaction.setAdapter(successAdapter);
                    }
                }
                else
                {
                    Snackbar.make(rv_transaction, "Gagal Memuat Data", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JelapayModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(rv_transaction, "Periksa Koneksi Internet",Snackbar.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
