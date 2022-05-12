package com.example.onfieldtbs_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onfieldtbs_android.databinding.IncidenceRowBinding;
import com.example.onfieldtbs_android.models.Incidence;

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
        holder.binding.rowPriority.setText(incidences.get(position).getPriority());
        holder.binding.rowDate.setText(incidences.get(position).getCreatedAt().toString());

        // Title format
        int counter = 0;
        String finalTitle = "";
        boolean end = false;
        for (char c : incidences.get(position).getTitle().toCharArray()){
            if (!end) {
                counter += 1;
                if (counter > 20) {
                    finalTitle += "...";
                    end = true;
                } else {
                    finalTitle += c;
                }
            }
        }

        holder.binding.rowTitle.setText(finalTitle);
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
