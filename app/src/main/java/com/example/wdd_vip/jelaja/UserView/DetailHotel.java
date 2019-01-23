package com.example.wdd_vip.jelaja.UserView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Config.Config;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ProviderProfileActivity;
import com.example.wdd_vip.jelaja.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHotel extends AppCompatActivity {

    @BindView(R.id.t_company)
    TextView t_company;
    @BindView(R.id.t_price)
    TextView t_price;
    @BindView(R.id.t_address)
    TextView t_address;
    @BindView(R.id.t_email)
    TextView t_email;
    @BindView(R.id.t_phone)
    TextView t_phone;
    @BindView(R.id.b_book)
    Button b_book;
    @BindView(R.id.photo)
    ImageView photo;
    String service;
    int swidth, sheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hotel);
        ButterKnife.bind(this);

        int schedule_id = getIntent().getIntExtra("id_schedule", 0);
        int passenger = getIntent().getIntExtra("passenger",0);
        System.out.println(passenger);
        String phone = getIntent().getStringExtra("phone");
        String date = getIntent().getStringExtra("date");
        String price = getIntent().getStringExtra("price");
        int night = getIntent().getIntExtra("night",0);
        int room = getIntent().getIntExtra("room", 0);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        swidth = size.x;
        sheight = size.y;

        getDetailSchedule(schedule_id);

        getSupportActionBar().setTitle("Detail Hotel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        b_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailHotel.this, CheckoutHotel.class);
                intent.putExtra("id_schedule", schedule_id);
                intent.putExtra("date", date);
                intent.putExtra("passenger", passenger);
                intent.putExtra("phone", phone);
                intent.putExtra("price", price);
                intent.putExtra("night", night);
                intent.putExtra("room", room);
                intent.putExtra("company", t_company.getText().toString());
                intent.putExtra("address", t_address.getText().toString());
                intent.putExtra("sector", service);
                startActivity(intent);
            }
        });
    }

    protected void getDetailSchedule(int schedule_id) {
        Api apiService = Retro.getDetailSchedule();
        apiService.getDetailSchedule(schedule_id).enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if(response.isSuccessful())
                {
                    String full_url = Config.BASE_URL+"upload/provider/"+response.body().getPhoto();
                    t_company.setText(response.body().getCompany_name());
                    t_price.setText("Rp "+response.body().getPrice());
                    t_address.setText(response.body().getAddress());
                    t_email.setText(response.body().getEmail());
                    t_phone.setText(response.body().getPhone());
                    service = response.body().getSector();
                    Glide.with(DetailHotel.this).load(full_url).override(swidth, 400).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(photo);
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
