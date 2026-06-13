package com.sanctus.christian.prayer.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.LiturgicalSeasonHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LiturgicalSeasonHelper.Season season = LiturgicalSeasonHelper.getCurrentSeason();
        ((TextView) view.findViewById(R.id.tv_current_season)).setText(
                LiturgicalSeasonHelper.getSeasonName(season));

        String today = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(new Date());
        ((TextView) view.findViewById(R.id.tv_today_date)).setText(DateUtils.getTodayFormatted());

        RecyclerView rvEvents = view.findViewById(R.id.rv_calendar_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        CalendarEventAdapter adapter = new CalendarEventAdapter();
        rvEvents.setAdapter(adapter);

        // Today's events
        SanctusDatabase.getInstance(requireContext())
                .calendarEventDao().getEventsForDate(today)
                .observe(getViewLifecycleOwner(), events -> {
                    TextView tvTodayLabel = view.findViewById(R.id.tv_today_feast_label);
                    if (events != null && !events.isEmpty()) {
                        tvTodayLabel.setText(events.get(0).title);
                        if (events.get(0).isSolemnity) {
                            tvTodayLabel.setTextColor(
                                requireContext().getColor(R.color.color_accent));
                        }
                    } else {
                        tvTodayLabel.setText("Feria (Weekday)");
                    }
                });

        // Upcoming solemnities
        SanctusDatabase.getInstance(requireContext())
                .calendarEventDao().getMajorFeast()
                .observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
