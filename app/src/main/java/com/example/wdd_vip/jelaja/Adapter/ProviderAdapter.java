package com.example.wdd_vip.jelaja.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wdd_vip.jelaja.AdminView.DetailRequest;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ScheduleProvier;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;

    public ProviderAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public ProviderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_provider,
                viewGroup, false);
        return new ProviderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAdapter.MyViewHolder myViewHolder, int i) {
        switch (ScheduleModels.get(i).getSector()) {
            case "Bus":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_directions_bus_black_24dp);
                break;
            case "Pesawat":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_airplanemode_active_black_24dp);
                break;
            case "Kapal":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_directions_boat_black_24dp);
                break;
            case "Kereta":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_train_black_24dp);
                break;
            case "Hotel":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_hotel_black_24dp);
                break;
            case "Wisata":
                myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_resort);
                break;
                default:
                    myViewHolder.i_sector.setBackgroundResource(R.drawable.ic_unknown);
                    break;
        }

        myViewHolder.t_name.setText(ScheduleModels.get(i).getName());
        myViewHolder.t_company.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.t_email.setText(ScheduleModels.get(i).getEmail());

        myViewHolder.b_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailRequest.class);
                intent.putExtra("id_user", ScheduleModels.get(i).getId());
                myViewHolder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ScheduleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.t_name)
        TextView t_name;
        @BindView(R.id.t_company)
        TextView t_company;
        @BindView(R.id.t_email)
        TextView t_email;
        @BindView(R.id.b_detail)
        CardView b_detail;
        @BindView(R.id.i_sector)
        ImageView i_sector;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
