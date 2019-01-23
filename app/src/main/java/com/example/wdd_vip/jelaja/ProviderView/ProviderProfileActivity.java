package com.example.wdd_vip.jelaja.ProviderView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdd_vip.jelaja.AdminView.DetailTransaction;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Config.Config;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.JelaPay;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderProfileActivity extends AppCompatActivity {

    @BindView(R.id.d_name)
    TextView d_name;
    @BindView(R.id.d_fullname)
    TextView d_fullname;
    @BindView(R.id.d_phone)
    TextView d_phone;
    @BindView(R.id.d_c_name)
    TextView d_c_name;
    @BindView(R.id.d_address)
    TextView d_address;
    @BindView(R.id.photo)
    ImageView photo;
    ProgressDialog progressDialog;
    int swidth, sheight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        swidth = size.x;
        sheight = size.y;

        progressDialog = new ProgressDialog(ProviderProfileActivity.this);
        progressDialog.setMessage("Sedang Memproses");
        progressDialog.setCancelable(false);
        progressDialog.show();

        getDetail(userid);

        FloatingActionButton fab_edit = (FloatingActionButton) findViewById(R.id.fab_d_edit);
        FloatingActionButton fab_photo = (FloatingActionButton) findViewById(R.id.fab_d_photo);

        fab_edit.setOnClickListener(v->{
            View view = LayoutInflater.from(ProviderProfileActivity.this).inflate(R.layout.provider_edit_profile, null);
            EditText e_c_name = (EditText) view.findViewById(R.id.e_d_name);
            EditText e_fullname = (EditText) view.findViewById(R.id.e_d_fullname);
            EditText e_phone = (EditText) view.findViewById(R.id.e_d_phone);
            EditText e_address = (EditText) view.findViewById(R.id.e_d_address);
            Api apiServices = Retro.getDetailUser();
            apiServices.getDetailUser(userid).enqueue(new Callback<AuthModel>() {
                @Override
                public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                    AuthModel userData = response.body();
                    if (response.isSuccessful()) {
                        e_c_name.setText(response.body().getCompany_name());
                        e_fullname.setText(response.body().getFullName());
                        e_phone.setText(response.body().getPhone());
                        e_address.setText(response.body().getAddress());
                    }
                }

                @Override
                public void onFailure(Call<AuthModel> call, Throwable t) {

                }
            });
            AlertDialog alertDialog = new AlertDialog.Builder(ProviderProfileActivity.this).create();
            alertDialog.setTitle("Edit Profile");
            alertDialog.setView(view);
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Yes",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogInterface, int id) {
                    Api apiServices = Retro.updateUser();
                    apiServices.updateUser(userid, e_phone.getText().toString(), e_fullname.getText().toString(), e_address.getText().toString(),e_c_name.getText().toString(),"").enqueue(new Callback<AuthModel>() {
                        @Override
                        public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                            AuthModel userData = response.body();
                            if (response.isSuccessful()) {
                                Toast.makeText(ProviderProfileActivity.this, "Data edited successfully", Toast.LENGTH_SHORT).show();
                                d_c_name.setText(e_c_name.getText().toString());
                                d_phone.setText(e_phone.getText().toString());
                                d_fullname.setText(e_fullname.getText().toString());
                                d_address.setText(e_address.getText().toString());
                            }
                            else
                            {
                                Toast.makeText(ProviderProfileActivity.this, response.raw().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<AuthModel> call, Throwable t) {
                            Toast.makeText(ProviderProfileActivity.this, "wwwhh", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE ,"Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        });

        fab_photo.setOnClickListener(v->{
            startActivity(new Intent(ProviderProfileActivity.this, EditPhoto.class));
        });
    }

    public void getDetail(int userID)
    {
        Api apiServices = Retro.getDetailUser();
        apiServices.getDetailUser(userID).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                AuthModel userData = response.body();
                if (response.isSuccessful()) {
                    String full_url = Config.BASE_URL+"upload/provider/"+userData.getPhoto();
                    d_name.setText(userData.getName());
                    d_fullname.setText(userData.getFullName());
                    d_phone.setText(userData.getPhone());
                    d_c_name.setText(userData.getCompany_name()+" ("+userData.getSector()+")");
                    d_address.setText(userData.getAddress());
                    Glide.with(ProviderProfileActivity.this).load(full_url).override(swidth, 400).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(photo);
                    progressDialog.dismiss();
                }
                else
                {
                    Snackbar.make(d_c_name, "Kesalahan Mengambil Data", Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Snackbar.make(d_c_name, "Periksa Koneksi Internet", Snackbar.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                startActivity(new Intent(ProviderProfileActivity.this,ProviderActivity.class));
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
