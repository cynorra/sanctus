package com.sanctus.christian.prayer.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.color.MaterialColors;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import com.sanctus.christian.prayer.util.PrayerTimeCalculator;
import java.util.List;

public class PrayerTimeAdapter extends RecyclerView.Adapter<PrayerTimeAdapter.ViewHolder> {
    private final List<PrayerTime> items;
    private final int activeIndex;

    public PrayerTimeAdapter(List<PrayerTime> items) {
        this.items = items;
        this.activeIndex = PrayerTimeCalculator.getNextHourIndex();
    }

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
        boolean isActive = (position == activeIndex);
        holder.itemView.setBackgroundResource(isActive ? R.drawable.bg_tile_selected : R.drawable.bg_tile_unselected);
        int accent = MaterialColors.getColor(holder.itemView, R.attr.sanctusAccent);
        int muted = ContextCompat.getColor(holder.itemView.getContext(), R.color.color_text_secondary);
        holder.tvName.setTextColor(isActive ? accent : ContextCompat.getColor(holder.itemView.getContext(), R.color.color_on_surface));
        holder.tvTime.setTextColor(isActive ? ContextCompat.getColor(holder.itemView.getContext(), R.color.color_gold_light) : muted);
        holder.itemView.setAlpha(isActive ? 1.0f : 0.78f);
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
