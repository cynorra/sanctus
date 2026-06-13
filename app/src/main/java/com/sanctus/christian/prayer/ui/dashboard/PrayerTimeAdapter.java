package com.sanctus.christian.prayer.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import java.util.List;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.ViewHolder> {
    private final List<PrayerTime> items;

    public PrayerTimeAdapter(List<PrayerTime> items) { this.items = items; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prayer_time, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrayerTime pt = items.get(position);
        holder.tvName.setText(pt.getNameLocalized());
        holder.tvTime.setText(pt.getTime());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_prayer_name);
            tvTime = v.findViewById(R.id.tv_prayer_time);
        }
    }
}
