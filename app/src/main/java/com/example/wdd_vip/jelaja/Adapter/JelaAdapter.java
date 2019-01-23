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

public class JelaAdapter extends RecyclerView.Adapter<JelaAdapter.MyViewHolder> {

    private Context context;
    private List<AuthModel> ScheduleModels;

    public JelaAdapter(Context context, ArrayList<AuthModel> ScheduleModels) {
        this.context = context;
        this.ScheduleModels = ScheduleModels;
    }

    @NonNull
    @Override
    public JelaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_jela,
                viewGroup, false);
        return new JelaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JelaAdapter.MyViewHolder myViewHolder, int i) {
        String time = (String) ScheduleModels.get(i).getGo_at();

        String[] separatedTimestamp = time.split(" ");
        String[] separatedTime = separatedTimestamp[0].trim().split("-");

        SharedPreferences spref = context.getSharedPreferences("UserData", MODE_PRIVATE);
        int userid = spref.getInt("jelaja_id", 0);
    }

    @Override
    public int getItemCount() {
        return ScheduleModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.s_p_hour)
        TextView s_p_hour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
