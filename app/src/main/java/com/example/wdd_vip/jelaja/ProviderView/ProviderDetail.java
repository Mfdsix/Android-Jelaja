package com.example.wdd_vip.jelaja.ProviderView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderDetail extends AppCompatActivity {

    EditText i_p_name;
    EditText i_p_address;
    EditText i_p_email;
    EditText i_p_phone;
    RadioGroup i_p_sector;
    Button b_detail_provider;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_detail);

        getSupportActionBar().setTitle("Detail Provider");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        i_p_name = (EditText) findViewById(R.id.i_p_name);
        i_p_address = (EditText) findViewById(R.id.i_p_address);
        i_p_email = (EditText) findViewById(R.id.i_p_email);
        i_p_phone = (EditText) findViewById(R.id.i_p_phone);
        i_p_sector = (RadioGroup) findViewById(R.id.i_p_sector);
        b_detail_provider = (Button) findViewById(R.id.b_detail_provider);

        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        int id_user = spref.getInt("jelaja_id", 0);

        b_detail_provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i_p_name.getText().toString().equals("") || i_p_address.getText().toString().equals("")
                        || i_p_email.getText().toString().equals("") || i_p_phone.getText().toString().equals("")
                        || i_p_sector.getCheckedRadioButtonId() == -1)
                {
                    Snackbar.make(v, "Semua Field Harus Diisi", Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    RadioButton rb_selected = (RadioButton) findViewById(i_p_sector.getCheckedRadioButtonId());
                    String sector_name = rb_selected.getText().toString();
                    progressDialog.setMessage("Sedang Diproses");
                    progressDialog.show();
                    Api apiService = Retro.updateUser();
                    apiService.updateUser(id_user, i_p_phone.getText().toString(), i_p_email.getText().toString(),
                            i_p_address.getText().toString(), i_p_name.getText().toString(), sector_name)
                            .enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            progressDialog.dismiss();
                            if(response.body().getMessage().equals("200"))
                            {
                                if(sector_name.equals("Wisata"))
                                {
                                    startActivity(new Intent(ProviderDetail.this, ResortDetail.class));
                                }
                                else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(ProviderDetail.this).create();
                                    alertDialog.setTitle("Pemberitahuan");
                                    alertDialog.setMessage("Proses Registrasi Sukses.\nSekarang tinggal menunggu email" +
                                            " konfirmasi dari kami untuk proses selanjutnya");
                                    alertDialog.setIcon(R.drawable.splash);
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.clear();
                                            editor.apply();
                                            alertDialog.dismiss();
                                            startActivity(new Intent(ProviderDetail.this, LoginActivity.class));
                                        }
                                    });
                                    alertDialog.show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthModel> call, Throwable t) {
                            Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
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
