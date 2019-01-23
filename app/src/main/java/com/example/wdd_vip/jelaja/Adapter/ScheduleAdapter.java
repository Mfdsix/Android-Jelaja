package com.example.wdd_vip.jelaja.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.ProviderView.ScheduleProvier;
import com.example.wdd_vip.jelaja.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;


    public ScheduleAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_provider_list,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String time = (String) ScheduleModels.get(i).getGo_at();
        String[] separatedTimestamp = time.split(" ");
        String[] separatedTime = separatedTimestamp[1].trim().split(":");

        myViewHolder.s_p_hour.setText(separatedTime[0]);
        myViewHolder.s_p_min.setText(separatedTime[1]);
        myViewHolder.s_p_name.setText(ScheduleModels.get(i).getCompany_name());
        myViewHolder.s_p_time.setText("About " + ScheduleModels.get(i).getEst_time() + " minutes");
        myViewHolder.s_p_price.setText("Rp " + ScheduleModels.get(i).getPrice());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
