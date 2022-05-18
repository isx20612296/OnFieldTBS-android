package com.example.onfieldtbs_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onfieldtbs_android.IncidenceDetailActivity;
import com.example.onfieldtbs_android.R;
import com.example.onfieldtbs_android.api.WebService;
import com.example.onfieldtbs_android.databinding.IncidenceRowBinding;
import com.example.onfieldtbs_android.models.Incidence;
import com.example.onfieldtbs_android.ui.IncidenceFragment;
import com.example.onfieldtbs_android.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

public class IncidenceAdapter extends RecyclerView.Adapter<IncidenceAdapter.ViewHolder> {

    private ArrayList<Incidence> incidences;
    private Context context;

    public IncidenceAdapter(ArrayList<Incidence> incidences, Context context) {
        this.incidences = incidences;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(IncidenceRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.rowState.setText(incidences.get(position).getState());

        // Priority text color
        String priority = incidences.get(position).getPriority();
        holder.binding.rowPriority.setText(priority);
        switch (priority){
            case "Baja":
                holder.binding.rowPriority.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case "Media":
                holder.binding.rowPriority.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case "Alta":
                holder.binding.rowPriority.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }

        // Date formatter
        holder.binding.rowDate.setText(Utils.formatDate(incidences.get(position).getCreatedAt()));

        // Title format
        holder.binding.rowTitle.setText(incidences.get(position).getId().toString().split("-")[0]);

        // On Click Listener to IncidenceDetail
        holder.binding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(context, IncidenceDetailActivity.class);
            Bundle bundle = new Bundle();
            WebService service = new WebService(context);
            service.getIncidenceById(incidences.get(position).getId().toString(), incidence -> {
                bundle.putSerializable("incidence", incidence);
                intent.putExtras(bundle);
                context.startActivity(intent);
            });

        });

    }

    @Override
    public int getItemCount() {
        return incidences.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private IncidenceRowBinding binding;
        public ViewHolder(@NonNull IncidenceRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
