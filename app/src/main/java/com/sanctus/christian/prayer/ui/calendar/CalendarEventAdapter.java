package com.sanctus.christian.prayer.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.ResolvedCalendarEvent;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class CalendarEventAdapter extends ListAdapter<ResolvedCalendarEvent, CalendarEventAdapter.VH> {

    public CalendarEventAdapter() {
        super(new DiffUtil.ItemCallback<ResolvedCalendarEvent>() {
            @Override public boolean areItemsTheSame(@NonNull ResolvedCalendarEvent a, @NonNull ResolvedCalendarEvent b) { return a.event.id == b.event.id; }
            @Override public boolean areContentsTheSame(@NonNull ResolvedCalendarEvent a, @NonNull ResolvedCalendarEvent b) {
                return Objects.equals(a.event.title, b.event.title) && a.occursOn.equals(b.occursOn);
            }
        });
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_event, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ResolvedCalendarEvent item = getItem(position);
        Context context = holder.itemView.getContext();
        holder.tvTitle.setText(item.event.title);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.getDefault());
        holder.tvDate.setText(sdf.format(item.occursOn.getTime()));
        holder.tvType.setText(item.event.isSolemnity
                ? context.getString(R.string.calendar_type_solemnity)
                : item.event.isFeastDay
                    ? context.getString(R.string.calendar_type_feast)
                    : context.getString(R.string.calendar_type_memorial));
        holder.tvHoly.setVisibility(item.event.isHolyDayOfObligation ? View.VISIBLE : View.GONE);
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
