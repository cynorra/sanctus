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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.ui.saint.SaintViewModel;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.LiturgicalSeasonHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel;
    private View rowStreak;
    private TextView tvStreak;

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

        rowStreak = view.findViewById(R.id.row_streak);
        tvStreak = view.findViewById(R.id.tv_streak);
        refreshStreak();

        // Season
        TextView tvSeason = view.findViewById(R.id.tv_liturgical_season);
        LiturgicalSeasonHelper.Season season = LiturgicalSeasonHelper.getCurrentSeason();
        tvSeason.setText(LiturgicalSeasonHelper.getSeasonName(requireContext(), season));

        // Next canonical hour
        TextView tvNextHour = view.findViewById(R.id.tv_next_hour);
        tvNextHour.setText(com.sanctus.christian.prayer.util.PrayerTimeCalculator.getNextHour(requireContext()));

        // Tap hero card to go pray
        view.findViewById(R.id.card_next_hour).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.prayerFragment));

        // Prayer times recycler
        RecyclerView rvPrayerTimes = view.findViewById(R.id.rv_prayer_times);
        rvPrayerTimes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        PrayerTimeAdapter adapter = new PrayerTimeAdapter(
                com.sanctus.christian.prayer.util.PrayerTimeCalculator.getCanonicalHours(requireContext())
        );
        rvPrayerTimes.setAdapter(adapter);

        // Prayer of the day
        TextView tvPrayerOfDayName = view.findViewById(R.id.tv_prayer_of_day_name);
        TextView tvPrayerOfDayExcerpt = view.findViewById(R.id.tv_prayer_of_day_excerpt);
        View cardPrayerOfDay = view.findViewById(R.id.card_prayer_of_day);
        viewModel.getPrayerOfTheDay().observe(getViewLifecycleOwner(), prayer -> {
            if (prayer == null) return;
            tvPrayerOfDayName.setText(prayer.name);
            tvPrayerOfDayExcerpt.setText(prayer.content);
            cardPrayerOfDay.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putInt(com.sanctus.christian.prayer.ui.prayer.PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_prayer_detail, args);
            });
        });

        // Saint of the day
        TextView tvSaintName = view.findViewById(R.id.tv_saint_name);
        TextView tvSaintFeast = view.findViewById(R.id.tv_saint_feast);
        TextView tvSaintDesc = view.findViewById(R.id.tv_saint_desc);
        String today = new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date());
        int dayOfYear = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_YEAR);

        SaintViewModel saintViewModel = new ViewModelProvider(this).get(SaintViewModel.class);
        saintViewModel.getSaintSpotlight(today, dayOfYear).observe(getViewLifecycleOwner(), saint -> {
            if (saint != null) {
                tvSaintName.setText(saint.name);
                tvSaintFeast.setText(getString(R.string.saint_feast_day_format, saint.feastDate));
                tvSaintDesc.setText(saint.description);
            } else {
                tvSaintName.setText(R.string.no_saint_today_title);
                tvSaintFeast.setText("");
                tvSaintDesc.setText(R.string.no_saint_today_desc);
            }
        });

        view.findViewById(R.id.card_saint_of_day).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.saintFragment));
    }

    @Override
    public void onResume() {
        super.onResume();
        // Fragment stays alive across bottom-nav tab switches, so re-check the streak
        // whenever this tab regains focus rather than only once at view creation.
        if (viewModel != null) refreshStreak();
    }

    private void refreshStreak() {
        if (viewModel == null || rowStreak == null) return;
        int streak = viewModel.getStreak();
        if (streak > 0) {
            tvStreak.setText(getString(R.string.streak_days_format, streak));
            rowStreak.setVisibility(View.VISIBLE);
        } else {
            rowStreak.setVisibility(View.GONE);
        }
    }
}
