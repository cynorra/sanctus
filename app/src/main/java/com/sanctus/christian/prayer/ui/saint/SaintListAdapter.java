package com.sanctus.christian.prayer.ui.saint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.util.AnimUtils;
import java.util.Objects;

public class SaintListAdapter extends ListAdapter<SaintEntity, SaintListAdapter.ViewHolder> {

    public interface OnSaintClick { void onClick(SaintEntity saint); }
    private final OnSaintClick listener;

    public SaintListAdapter(OnSaintClick listener) {
        super(new DiffUtil.ItemCallback<SaintEntity>() {
            @Override public boolean areItemsTheSame(@NonNull SaintEntity a, @NonNull SaintEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull SaintEntity a, @NonNull SaintEntity b) { return Objects.equals(a.name, b.name); }
        });
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saint, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaintEntity s = getItem(position);
        holder.tvName.setText(s.name);
        holder.tvFeast.setText(s.feastDate);
        holder.tvPatron.setText(s.patronOf);
        holder.itemView.setOnClickListener(v -> {
            AnimUtils.pressFeedback(v);
            listener.onClick(s);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFeast, tvPatron;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_saint_name);
            tvFeast = v.findViewById(R.id.tv_feast_date);
            tvPatron = v.findViewById(R.id.tv_patron_of);
        }
    }
}
