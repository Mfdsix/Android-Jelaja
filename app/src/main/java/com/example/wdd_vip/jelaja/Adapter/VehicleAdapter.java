package com.example.wdd_vip.jelaja.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
import com.example.wdd_vip.jelaja.ProviderView.ProviderProfileActivity;
import com.example.wdd_vip.jelaja.ProviderView.ScheduleProvier;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.DetailVehicle;
import com.example.wdd_vip.jelaja.UserView.VehicleResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;

    public VehicleAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public VehicleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_vehicle,
                viewGroup, false);
        return new VehicleAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleAdapter.MyViewHolder myViewHolder, int i) {
        String time = (String) ScheduleModels.get(i).getGo_at();

        if(ScheduleModels.get(i).getSector().equals("Bus")) {
            myViewHolder.s_p_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_directions_bus_black_24dp,0,0,0);
        }
        else if(ScheduleModels.get(i).getSector().equals("Plane")) {
            myViewHolder.s_p_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_airplanemode_active_black_24dp,0,0,0);
        }
        else if(ScheduleModels.get(i).getSector().equals("Boat")) {
            myViewHolder.s_p_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_directions_boat_black_24dp,0,0,0);
        }
        else if(ScheduleModels.get(i).getSector().equals("Train")) {
            myViewHolder.s_p_name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_train_black_24dp,0,0,0);
        }
        String[] separatedTimestamp = time.split(" ");
        String[] separatedTime = separatedTimestamp[1].trim().split(":");

        myViewHolder.s_p_hour.setText(separatedTime[0]);
        myViewHolder.s_p_min.setText(separatedTime[1]);

        myViewHolder.s_p_name.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.s_p_time.setText("About " + ScheduleModels.get(i).getEst_time() + " minutes");
        myViewHolder.s_p_price.setText("Rp " + ScheduleModels.get(i).getPrice());

        SharedPreferences spref = context.getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);

        myViewHolder.v_r_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailVehicle.class);
                intent.putExtra("id_schedule", ScheduleModels.get(i).getIdSchedule());
                intent.putExtra("date", ScheduleModels.get(i).getGo_at());
                intent.putExtra("passenger", ScheduleModels.get(i).getPassenger());
                intent.putExtra("route", ScheduleModels.get(i).getSource()+" - "+ScheduleModels.get(i).getDestination());
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

        @BindView(R.id.s_p_hour)
        TextView s_p_hour;
        @BindView(R.id.s_p_min)
        TextView s_p_min;
        @BindView(R.id.s_p_name)
        TextView s_p_name;
        @BindView(R.id.s_p_time)
        TextView s_p_time;
        @BindView(R.id.s_p_price)
        TextView s_p_price;
        @BindView(R.id.v_r_book)
        CardView v_r_book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
