package com.sanctus.christian.prayer.ui.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import java.util.Objects;

public class CalendarEventAdapter extends ListAdapter<CalendarEventEntity, CalendarEventAdapter.VH> {

    public CalendarEventAdapter() {
        super(new DiffUtil.ItemCallback<CalendarEventEntity>() {
            @Override public boolean areItemsTheSame(@NonNull CalendarEventEntity a, @NonNull CalendarEventEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull CalendarEventEntity a, @NonNull CalendarEventEntity b) { return Objects.equals(a.title, b.title); }
        });
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_event, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CalendarEventEntity ev = getItem(position);
        holder.tvTitle.setText(ev.title);
        holder.tvDate.setText(formatDate(ev.date));
        holder.tvType.setText(ev.isSolemnity ? "Solemnity" : ev.isFeastDay ? "Feast" : "Memorial");
        if (ev.isHolyDayOfObligation) {
            holder.tvHoly.setVisibility(View.VISIBLE);
        } else {
            holder.tvHoly.setVisibility(View.GONE);
        }
    }

    private String formatDate(String mmdd) {
        if (mmdd == null || mmdd.length() < 5) return mmdd;
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        try {
            int m = Integer.parseInt(mmdd.substring(0, 2)) - 1;
            int d = Integer.parseInt(mmdd.substring(3, 5));
            return months[m] + " " + d;
        } catch (Exception e) { return mmdd; }
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate, tvType, tvHoly;
        VH(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tv_event_title);
            tvDate = v.findViewById(R.id.tv_event_date);
            tvType = v.findViewById(R.id.tv_event_type);
            tvHoly = v.findViewById(R.id.tv_holy_day);
        }
    }
}
