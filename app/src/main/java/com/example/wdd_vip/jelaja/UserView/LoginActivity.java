package com.example.wdd_vip.jelaja.UserView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Button b_login = (Button) findViewById(R.id.b_login);
        EditText t_name = (EditText) findViewById(R.id.i_username);
        EditText t_pass = (EditText) findViewById(R.id.i_password);

        b_login.setOnClickListener(view -> {
                    if (t_name.getText().length() < 6 || t_pass.getText().length() < 6) {
                        Snackbar.make(view, "Username dan Password minimal 6 karakter", Snackbar.LENGTH_SHORT).show();
                        t_name.requestFocus();
                    } else {
                        if (t_name.getText().equals("") || t_pass.getText().equals("")) {
                            Snackbar.make(view, "Semua field harus diisi", Snackbar.LENGTH_SHORT).show();
                        } else {
                            progressDialog.setMessage("Sedang Memproses");
                            progressDialog.show();
                            loginAuth(t_name.getText().toString(), t_pass.getText().toString(), view);
                        }
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
    }

    public void show_register(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
    public void loginAuth(String username, String password, View v) {
        final Api apiServices = Retro.loginAuth();
        apiServices.loginAuth(username, password).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if(response.body().getMessage().equals("200")) {
                        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = spref.edit();
                        editor.putString("jelaja_username", response.body().getName());
                        editor.putString("jelaja_role", response.body().getRole());
                        editor.putInt("jelaja_id", response.body().getId());
                        editor.putString("jelaja_sector", response.body().getSector());
                        editor.putString("jelaja_email", response.body().getEmail());
                        editor.apply();
                        Intent i = new Intent(LoginActivity.this, DrawerActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Snackbar.make(v, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else
                {
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
