package com.sanctus.christian.prayer.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.util.CalendarResolver;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.LiturgicalSeasonHelper;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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
                LiturgicalSeasonHelper.getSeasonName(requireContext(), season));
        ((TextView) view.findViewById(R.id.tv_today_date)).setText(DateUtils.getTodayFormatted());

        RecyclerView rvEvents = view.findViewById(R.id.rv_calendar_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));
        CalendarEventAdapter adapter = new CalendarEventAdapter();
        rvEvents.setAdapter(adapter);

        TextView tvTodayLabel = view.findViewById(R.id.tv_today_feast_label);
        TextView tvEmpty = view.findViewById(R.id.tv_empty_calendar);
        Calendar today = Calendar.getInstance();

        CalendarViewModel viewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        viewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            List<com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity> safeEvents =
                    events != null ? events : Collections.emptyList();

            List<com.sanctus.christian.prayer.domain.model.ResolvedCalendarEvent> todays =
                    CalendarResolver.resolveForToday(safeEvents, today);
            if (!todays.isEmpty()) {
                tvTodayLabel.setText(todays.get(0).event.title);
                if (todays.get(0).event.isSolemnity) {
                    tvTodayLabel.setTextColor(requireContext().getColor(R.color.color_accent));
                }
            } else {
                tvTodayLabel.setText(R.string.feria_weekday);
            }

            List<com.sanctus.christian.prayer.domain.model.ResolvedCalendarEvent> upcoming =
                    CalendarResolver.resolveUpcomingMajorFeasts(safeEvents, today, 15);
            adapter.submitList(upcoming);
            tvEmpty.setVisibility(upcoming.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }
}
