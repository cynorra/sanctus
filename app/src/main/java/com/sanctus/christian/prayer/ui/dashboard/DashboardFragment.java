package com.sanctus.christian.prayer.ui.dashboard;

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
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.LiturgicalSeasonHelper;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Date
        TextView tvDate = view.findViewById(R.id.tv_date);
        tvDate.setText(DateUtils.getTodayFormatted());

        // Season
        TextView tvSeason = view.findViewById(R.id.tv_liturgical_season);
        LiturgicalSeasonHelper.Season season = LiturgicalSeasonHelper.getCurrentSeason();
        tvSeason.setText(LiturgicalSeasonHelper.getSeasonName(season));

        // Next canonical hour
        TextView tvNextHour = view.findViewById(R.id.tv_next_hour);
        tvNextHour.setText(com.sanctus.christian.prayer.util.PrayerTimeCalculator.getNextHour());

        // Prayer times recycler
        RecyclerView rvPrayerTimes = view.findViewById(R.id.rv_prayer_times);
        rvPrayerTimes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        PrayerTimeAdapter adapter = new PrayerTimeAdapter(
                com.sanctus.christian.prayer.util.PrayerTimeCalculator.getCanonicalHours()
        );
        rvPrayerTimes.setAdapter(adapter);
    }
}
