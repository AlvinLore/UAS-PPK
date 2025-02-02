package com.example.pendaftaranukm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pendaftaranukm.R;
import com.example.pendaftaranukm.model.Ukm;
import java.util.List;

public class UkmAdapter extends RecyclerView.Adapter<UkmAdapter.UkmViewHolder> {
    private List<Ukm> ukmList;
    private OnUkmClickListener onUkmClickListener;

    public UkmAdapter(List<Ukm> ukmList, OnUkmClickListener onUkmClickListener) {
        this.ukmList = ukmList;
        this.onUkmClickListener = onUkmClickListener;
    }

    @NonNull
    @Override
    public UkmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ukm, parent, false);
        return new UkmViewHolder(view, onUkmClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UkmViewHolder holder, int position) {
        Ukm ukm = ukmList.get(position);
        holder.tvUkmName.setText(ukm.getName());
        holder.tvUkmDescription.setText(ukm.getDescription());
    }

    @Override
    public int getItemCount() {
        return ukmList.size();
    }

    public static class UkmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUkmName, tvUkmDescription;
        OnUkmClickListener onUkmClickListener;

        public UkmViewHolder(@NonNull View itemView, OnUkmClickListener onUkmClickListener) {
            super(itemView);
            tvUkmName = itemView.findViewById(R.id.tvUkmName);
            tvUkmDescription = itemView.findViewById(R.id.tvUkmDescription);
            this.onUkmClickListener = onUkmClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onUkmClickListener.onUkmClick(getAdapterPosition());
        }
    }
    public interface OnUkmClickListener {
        void onUkmClick(int position);
    }
}
