package com.example.wdd_vip.jelaja.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ResortAdapter extends RecyclerView.Adapter<ResortAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;

    public ResortAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public ResortAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_resort,
                viewGroup, false);
        return new ResortAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResortAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.s_p_name.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.s_p_time.setText(ScheduleModels.get(i).getSimpleTime());
        myViewHolder.s_p_price.setText("Rp " + ScheduleModels.get(i).getPrice());

        SharedPreferences spref = context.getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);

        myViewHolder.r_r_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Book "+ScheduleModels.get(i).getCompany_name()+" ?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"Yes",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int id) {
                        Api apiServices = Retro.bookHotel();
                        apiServices.bookHotel(userid, ScheduleModels.get(i).getIdSchedule(), ScheduleModels.get(i).getPassenger(), ScheduleModels.get(i).getRoom(), ScheduleModels.get(i).getNight(),0).enqueue(new Callback<AuthModel>() {
                            @Override
                            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                                AuthModel userData = response.body();
                                if (response.isSuccessful()) {
                                    Toast.makeText(context, "Hotel Booked successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<AuthModel> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return ScheduleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.s_p_name)
        TextView s_p_name;
        @BindView(R.id.s_p_time)
        TextView s_p_time;
        @BindView(R.id.s_p_price)
        TextView s_p_price;
        @BindView(R.id.r_r_book)
        Button r_r_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
