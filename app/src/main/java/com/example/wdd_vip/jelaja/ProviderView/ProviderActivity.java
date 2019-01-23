package com.example.wdd_vip.jelaja.ProviderView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.LoginActivity;

public class ProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        getSupportActionBar().setTitle("Dashboard Provider");
    }

    public void show_my_profile(View view)
    {
        startActivity(new Intent(ProviderActivity.this, ProviderProfileActivity.class));
    }

    public void show_my_schedule(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String sector = sharedPreferences.getString("jelaja_sector", "");

        if(sector.equals("Wisata")) {
            startActivity(new Intent(ProviderActivity.this, ResortProvider.class));
        }
        else{
            startActivity(new Intent(ProviderActivity.this, ScheduleProvier.class));
        }
    }

    public void let_me_logout(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProviderActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Yakin Ingin Keluar ?");
        builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int id) {
                SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = spref.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(ProviderActivity.this, LoginActivity.class);
                startActivity(i);
                Toast.makeText(ProviderActivity.this, "You're logged out", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
