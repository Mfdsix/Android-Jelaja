package com.example.wdd_vip.jelaja.AdminView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ProviderProfileActivity;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProfileActivity extends AppCompatActivity {

    @BindView(R.id.da_fullname)
    TextView da_fullname;
    @BindView(R.id.da_phone)
    TextView da_phone;
    @BindView(R.id.da_name)
    TextView da_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);
        getDetail(userid);
        setContentView(R.layout.activity_admin_profile_provider);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab_edit = (FloatingActionButton) findViewById(R.id.fab_da_edit);

        fab_edit.setOnClickListener(v->{
            View view = LayoutInflater.from(AdminProfileActivity.this).inflate(R.layout.admin_edit_profile, null);
            EditText e_fullname = (EditText) view.findViewById(R.id.e_da_fullname);
            EditText e_phone = (EditText) view.findViewById(R.id.e_da_phone);
            Api apiServices = Retro.getDetailUser();
            apiServices.getDetailUser(userid).enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                    AuthModel userData = response.body();
                    if (response.isSuccessful()) {
                        e_fullname.setText(userData.getFullName());
                        e_phone.setText(userData.getPhone());
                    }
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {
                }
            });
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminProfileActivity.this);
            builder.setTitle("Edit Profile");
            builder.setView(view);
            builder.setPositiveButton("Edit",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogInterface, int id) {
                    final Api apiServices = Retro.updateUser();
                    apiServices.updateUser(userid, e_phone.getText().toString(), e_fullname.getText().toString(), "","","").enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            if (response.isSuccessful()) {
                                da_fullname.setText(e_fullname.getText().toString());
                                da_phone.setText(e_phone.getText().toString());
                                Toast.makeText(AdminProfileActivity.this, "Data edited successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(AdminProfileActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthModel> call, Throwable t) {
                            Toast.makeText(AdminProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.setView(null);
                }
            });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    public void getDetail(int userID)
    {
        final Api apiServices = Retro.getDetailUser();
        apiServices.getDetailUser(userID).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                AuthModel userData = response.body();
                if (response.isSuccessful()) {
                    da_name.setText(userData.getName());
                    da_fullname.setText(userData.getFullName());
                    da_phone.setText(userData.getPhone());
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
