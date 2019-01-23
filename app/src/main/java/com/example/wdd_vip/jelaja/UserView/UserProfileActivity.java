package com.example.wdd_vip.jelaja.UserView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.e_du_fullname)
    TextView tu_name;
    @BindView(R.id.e_du_address)
    TextView tu_address;
    @BindView(R.id.e_du_phone)
    TextView tu_phone;
    @BindView(R.id.e_du_number)
    TextView tu_number;
//    @BindView(R.id.e_du_bank)
//    RadioGroup tu_bank;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_profile);
        ButterKnife.bind(this);

        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);
        getDetail(userid, findViewById(R.id.b_save_user));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getSupportActionBar().setTitle("Data Diri");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button b_save = (Button) findViewById(R.id.b_save_user);
        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
//                int selectedID = tu_bank.getCheckedRadioButtonId();
//                RadioButton bank = (RadioButton) findViewById(selectedID);
//                String bank_name = bank.getText().toString();

                Api apiServices = Retro.updateMainUser();
                apiServices.updateMainUser(userid, tu_phone.getText().toString(), tu_name.getText().toString(),tu_address.getText().toString(),"","" ).enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            Snackbar.make(v, "Data Diri DiUpdate", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(v, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getDetail(int userID, View v)
    {
        final Api apiServices = Retro.getDetailUser();
        apiServices.getDetailUser(userID).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                AuthModel userData = response.body();
                if (response.isSuccessful()) {
                    tu_name.setText(userData.getFullName());
                    tu_address.setText(userData.getAddress());
                    tu_phone.setText(userData.getPhone());
//                    if(!userData.getBank().equals("")) {
//                        switch (userData.getBank()) {
//                            case "BRI": {
//                                RadioButton bank = (RadioButton) findViewById(R.id.BRI);
//                                bank.setChecked(true);
//                                break;
//                            }
//                            case "BNI": {
//                                RadioButton bank = (RadioButton) findViewById(R.id.BNI);
//                                bank.setChecked(true);
//                                break;
//                            }
//                            case "BCA": {
//                                RadioButton bank = (RadioButton) findViewById(R.id.BCA);
//                                bank.setChecked(true);
//                                break;
//                            }
//                            default:
//                                break;
//                        }
//                    }
                    tu_number.setText(userData.getNumber());
                    progressDialog.dismiss();
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
