package com.example.wdd_vip.jelaja.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Config.Config;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ProviderProfileActivity;
import com.example.wdd_vip.jelaja.ProviderView.ScheduleProvier;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.DetailHotel;
import com.example.wdd_vip.jelaja.UserView.DetailVehicle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;

    public HotelAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public HotelAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_hotel,
                viewGroup, false);
        return new HotelAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.MyViewHolder myViewHolder, int i) {

        String full_url = Config.BASE_URL+"upload/provider/"+ScheduleModels.get(i).getPhoto();
        myViewHolder.s_p_name.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.s_p_address.setText(ScheduleModels.get(i).getAddress());
        myViewHolder.s_p_price.setText("Rp " + ScheduleModels.get(i).getPrice());
        Glide.with(context).load(full_url).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(myViewHolder.photo);

        SharedPreferences spref = context.getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);

        myViewHolder.v_r_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailHotel.class);
                intent.putExtra("id_schedule", ScheduleModels.get(i).getIdSchedule());
                intent.putExtra("date", ScheduleModels.get(i).getGo_at());
                intent.putExtra("passenger", ScheduleModels.get(i).getPassenger());
                intent.putExtra("night", ScheduleModels.get(i).getNight());
                intent.putExtra("room", ScheduleModels.get(i).getRoom());
                intent.putExtra("phone", ScheduleModels.get(i).getPhone());
                intent.putExtra("price", ScheduleModels.get(i).getPrice());
                myViewHolder.itemView.getContext().startActivity(intent);
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
        @BindView(R.id.s_p_address)
        TextView s_p_address;
        @BindView(R.id.s_p_price)
        TextView s_p_price;
        @BindView(R.id.v_r_book)
        CardView v_r_book;
        @BindView(R.id.photo)
        ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
