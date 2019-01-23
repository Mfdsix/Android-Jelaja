package com.example.wdd_vip.jelaja.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wdd_vip.jelaja.Model.JelapayModel;
import com.example.wdd_vip.jelaja.R;
import com.example.wdd_vip.jelaja.UserView.ConfirmJela;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopupHistoryAdapter extends RecyclerView.Adapter<TopupHistoryAdapter.MyViewHolder> {

    private Context context;
    private List<JelapayModel> jelapayModels;

    public TopupHistoryAdapter(Context context, ArrayList<JelapayModel> jelapayModels) {
        this.context = context;
        this.jelapayModels= jelapayModels;
    }

    @NonNull
    @Override
    public TopupHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_jelapay_history,
                viewGroup, false);
        return new TopupHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopupHistoryAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.t_date.setText(jelapayModels.get(i).getDate());
        myViewHolder.t_amount.setText("Rp "+jelapayModels.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return jelapayModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.t_date)
        TextView t_date;
        @BindView(R.id.t_amount)
        TextView t_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
