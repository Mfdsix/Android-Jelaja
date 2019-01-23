package com.example.wdd_vip.jelaja.UserView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.AdminView.AdminDashboard;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ProviderActivity;
import com.example.wdd_vip.jelaja.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.reflect.Array.getInt;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,TabVehicle.OnFragmentInteractionListener, TabHotel.OnFragmentInteractionListener, TabResort.OnFragmentInteractionListener{

    private TextView mTextMessage;
    TextView t_name_drawer, t_email_drawer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFrag = null;

            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectedFrag = new HomeFragmet();
                    break;
                case R.id.nav_travel:
                    selectedFrag = new TravelFragmet();
                    break;
                case R.id.nav_pay:
                    selectedFrag = new PayFragmet();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.f_container, selectedFrag).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = spref.getString("jelaja_username", "");
        String email = spref.getString("jelaja_email", "");

        View view = LayoutInflater.from(DrawerActivity.this).inflate(R.layout.nav_header_drawer, null);
        t_name_drawer = view.findViewById(R.id.t_name_drawer);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.f_container, new HomeFragmet()).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(DrawerActivity.this);
            builder.setTitle("Logout");
            builder.setMessage("Yakin ingin keluar ?");
            builder.setPositiveButton("Ya",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogInterface, int id) {
                    SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = spref.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(DrawerActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();
        }
        else if (id == R.id.nav_profile) {
            startActivity(new Intent(DrawerActivity.this, UserProfileActivity.class));
        }
        else if (id == R.id.nav_jelajapay) {
            startActivity(new Intent(DrawerActivity.this, JelaPay.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        SharedPreferences spref = getSharedPreferences("UserData", MODE_PRIVATE);
        String username = spref.getString("jelaja_username", "");
        String role = spref.getString("jelaja_role", "");


        if(username.toString().equals(""))
        {
            Intent i = new Intent(DrawerActivity.this, LoginActivity.class);
            startActivity(i);
        }
        else if(role.toString().equals("P"))
        {
            Intent i = new Intent(DrawerActivity.this, ProviderActivity.class);
            startActivity(i);
        }
        else if(role.toString().equals("A"))
        {
            Intent i = new Intent(DrawerActivity.this, AdminDashboard.class);
            startActivity(i);
        }

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
