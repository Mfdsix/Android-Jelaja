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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdd_vip.jelaja.Config.Config;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.DetailHotel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.MyViewHolder> {

    private Context context;
    private List<JelapayModel> ScheduleModels;

    public SuccessAdapter(Context context, ArrayList<JelapayModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public SuccessAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_success,
                viewGroup, false);
        return new SuccessAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuccessAdapter.MyViewHolder myViewHolder, int i) {

        if(ScheduleModels.get(i).getSector().equals("Bus")) {
            myViewHolder.gambar.setBackgroundResource(R.drawable.ic_directions_bus_black_24dp);
        }
        else if(ScheduleModels.get(i).getSector().equals("Plane")) {
            myViewHolder.gambar.setBackgroundResource(R.drawable.ic_airplanemode_active_black_24dp);
        }
        else if(ScheduleModels.get(i).getSector().equals("Boat")) {
            myViewHolder.gambar.setBackgroundResource(R.drawable.ic_directions_boat_black_24dp);
        }
        else if(ScheduleModels.get(i).getSector().equals("Train")) {
            myViewHolder.gambar.setBackgroundResource(R.drawable.ic_train_black_24dp);
        }
        else if(ScheduleModels.get(i).getSector().equals("Hotel")) {
            myViewHolder.gambar.setBackgroundResource(R.drawable.ic_hotel_black_24dp);
        }
        myViewHolder.nama.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.harga.setText("Rp " + ScheduleModels.get(i).getHarga());
        myViewHolder.berangkat.setText(ScheduleModels.get(i).getBerangkat());
        myViewHolder.detail.setText(ScheduleModels.get(i).getDetail());
        myViewHolder.kota.setText(ScheduleModels.get(i).getKota());

    }

    @Override
    public int getItemCount() {
        return ScheduleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.gambar)
        ImageView gambar;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.harga)
        TextView harga;
        @BindView(R.id.kota)
        TextView kota;
        @BindView(R.id.berangkat)
        TextView berangkat;
        @BindView(R.id.detail)
        TextView detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
