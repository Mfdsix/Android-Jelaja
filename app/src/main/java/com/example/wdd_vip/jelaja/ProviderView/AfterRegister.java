package com.example.wdd_vip.jelaja.ProviderView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.UserProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterRegister extends AppCompatActivity {

    @BindView(R.id.c_service)
    RadioGroup c_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_register);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Jenis Layanan");

        Button b_save = (Button) findViewById(R.id.b_save_choice);
        b_save.setOnClickListener(v-> {
                int selectedID = c_service.getCheckedRadioButtonId();
                RadioButton serv = (RadioButton) findViewById(selectedID);
                String serv_name = serv.getText().toString();

                SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                int userid = spref.getInt("jelaja_id", 0);

                Api apiServices = Retro.setService();
                apiServices.setService(userid, serv_name).enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = spref.edit();
                            editor.putString("jelaja_sector", serv_name);
                            editor.apply();
                            startActivity(new Intent(AfterRegister.this, ProviderActivity.class));
                        }
                        else
                        {
                            Toast.makeText(AfterRegister.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        Toast.makeText(AfterRegister.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
