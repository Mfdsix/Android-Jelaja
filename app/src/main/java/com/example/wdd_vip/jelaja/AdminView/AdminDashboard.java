package com.example.wdd_vip.jelaja.AdminView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.UserView.LoginActivity;
import com.example.wdd_vip.jelaja.R;

public class AdminDashboard extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getSupportActionBar().setTitle("Dashboard Admin");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memproses");

    }

    public void show_my_profile(View view)
    {
        startActivity(new Intent(AdminDashboard.this, AdminProfileActivity.class));
    }

    public void show_the_transactions(View v)
    {
        startActivity(new Intent(AdminDashboard.this, TransactionRequest.class));
    }

    public void show_the_providers(View v)
    {
        startActivity(new Intent(AdminDashboard.this, ProviderRequest.class));
    }

    public void let_me_logout(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure want to logout ?");
                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = spref.edit();
                        editor.clear();
                        editor.apply();
                        Intent i = new Intent(AdminDashboard.this, LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(AdminDashboard.this, "You're logged out", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
