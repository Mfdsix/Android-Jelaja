package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.AdminView.AdminDashboard;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.AfterRegister;
import com.example.wdd_vip.jelaja.ProviderView.ProviderActivity;
import com.example.wdd_vip.jelaja.ProviderView.ProviderDetail;
import com.example.wdd_vip.jelaja.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Diproses");
        progressDialog.setCancelable(false);
        Button b_user = (Button) findViewById(R.id.b_reg_user);
        Button b_pro = (Button) findViewById(R.id.b_reg_prov);
        final EditText t_name = (EditText) findViewById(R.id.i_username);
        final EditText t_pass = (EditText) findViewById(R.id.i_password);
        final EditText t_pass2 = (EditText) findViewById(R.id.i_password2);
        b_user.setOnClickListener(v -> {
            if(t_name.getText().length() < 6 || t_pass.getText().length() < 6)
            {
             Snackbar.make(v, "Username dan Password minimal 6 karakter", Snackbar.LENGTH_SHORT).show();
             t_name.requestFocus();
            }else {
                if (t_name.getText().toString().equals("") || t_pass.getText().toString().equals("")
                        || t_pass2.getText().toString().equals("")) {
                    Snackbar.make(v, "Semua Field Harus Diisi", Snackbar.LENGTH_SHORT).show();
                } else {
                    if (t_pass.getText().toString().equals(t_pass2.getText().toString())) {
                        progressDialog.show();
                        regAuth(t_name.getText().toString(), t_pass.getText().toString(), "U", v);
                    } else {
                        Snackbar.make(v, "Password Tidak Sama", Snackbar.LENGTH_SHORT).show();
                        t_pass2.requestFocus();
                    }
                }
            }
        });
        b_pro.setOnClickListener(v -> {
            if(t_name.getText().toString().equals("") || t_pass.getText().toString().equals("") || t_pass2.getText().toString().equals("")) {
                Snackbar.make(v, "Semua Field Harus Diisi", Snackbar.LENGTH_SHORT).show();
            }
            else {
                if(t_pass.getText().toString().equals(t_pass2.getText().toString())) {
                    progressDialog.show();
                    regAuth(t_name.getText().toString(), t_pass.getText().toString(), "P", v);
                }
                else {
                    Snackbar.make(v, "Password Tidak Sama", Snackbar.LENGTH_SHORT).show();
                    t_pass2.requestFocus();
                }
            }
        });

    }

    public void show_login(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }

    public void regAuth(String username, String password, String role, View v) {
        final Api apiServices = Retro.signupAuth();
        apiServices.signupAuth(username, password, role).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if(response.body().getMessage().equals("200")) {
                        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = spref.edit();
                        editor.putString("jelaja_username", response.body().getName());
                        editor.putString("jelaja_role", response.body().getRole());
                        editor.putInt("jelaja_id", response.body().getId());
                        editor.apply();
                        if(response.body().getRole().equals("P")) {
                            Intent i = new Intent(RegisterActivity.this, ProviderDetail.class);
                            startActivity(i);
                        }
                        else if(response.body().getRole().equals("A"))
                        {
                            Intent i = new Intent(RegisterActivity.this, AdminDashboard.class);
                            startActivity(i);
                        }
                        else
                        {
                            Intent i = new Intent(RegisterActivity.this, DrawerActivity.class);
                            startActivity(i);
                        }
                    }
                    else
                    {
                        Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    progressDialog.dismiss();
                    Snackbar.make(v, "Terjadi Kesalahan", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(v, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
