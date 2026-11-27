package com.sanctus.christian.prayer.ui.dashboard;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;
import com.google.android.material.color.MaterialColors;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import com.sanctus.christian.prayer.share.ShareActions;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.ui.saint.SaintViewModel;
import com.sanctus.christian.prayer.util.AnimUtils;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.MotionKit;
import com.sanctus.christian.prayer.util.PrayerTimeCalculator;
import com.sanctus.christian.prayer.util.SaintNames;
import com.sanctus.christian.prayer.util.Tradition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        // Greeting & Date
        TextView tvGreeting = view.findViewById(R.id.tv_app_title);
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hourOfDay < 12) {
            tvGreeting.setText(R.string.greeting_morning);
        } else if (hourOfDay < 18) {
            tvGreeting.setText(R.string.greeting_afternoon);
        } else {
            tvGreeting.setText(R.string.greeting_evening);
        }

        TextView tvDate = view.findViewById(R.id.tv_date);
        tvDate.setText(DateUtils.getTodayFormatted());

        MotionKit.pressable(view.findViewById(R.id.card_prayer_of_day));
        MotionKit.pressable(view.findViewById(R.id.card_saint_of_day));
        MotionKit.pressable(view.findViewById(R.id.tile_rosary));
        MotionKit.pressable(view.findViewById(R.id.tile_prayers));

        String tradition = SanctusApplication.getInstance().getSettingsRepository().getTradition();
        View tileRosary = view.findViewById(R.id.tile_rosary);
        int counterDestination = R.id.rosaryFragment;
        if (Tradition.ORTHODOX.equals(tradition)) {
            counterDestination = R.id.jesusPrayerFragment;
            ((ImageView) view.findViewById(R.id.tile_rosary_icon)).setImageResource(R.drawable.ic_prayer_rope);
            ((TextView) view.findViewById(R.id.tile_rosary_label)).setText(R.string.nav_jesus_prayer);
        } else if (Tradition.PROTESTANT.equals(tradition)) {
            tileRosary.setVisibility(View.GONE);
        }
        final int destination = counterDestination;
        tileRosary.setOnClickListener(v -> Navigation.findNavController(v).navigate(destination));
        view.findViewById(R.id.tile_prayers).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.prayerFragment));

        setupSanctuary2027Tools(view);

        // Next canonical hour
        TextView tvNextHour = view.findViewById(R.id.tv_next_hour);
        tvNextHour.setText(PrayerTimeCalculator.getNextHour(requireContext()));

        View cardNextHour = view.findViewById(R.id.card_next_hour);
        View rowHeader = view.findViewById(R.id.row_next_hour_header);
        View expandContainer = view.findViewById(R.id.container_hours_expand);
        ImageView ivChevron = view.findViewById(R.id.iv_expand_chevron);
        LinearLayout listHoursMini = view.findViewById(R.id.list_hours_mini);

        List<PrayerTime> hours = PrayerTimeCalculator.getCanonicalHours(requireContext());
        int nextIndex = PrayerTimeCalculator.getNextHourIndex();
        for (int i = 0; i < hours.size(); i++) {
            PrayerTime hour = hours.get(i);
            View row = LayoutInflater.from(requireContext()).inflate(R.layout.item_hour_mini_row, listHoursMini, false);
            TextView tvHourName = row.findViewById(R.id.tv_hour_name);
            TextView tvHourTime = row.findViewById(R.id.tv_hour_time);
            tvHourName.setText(hour.getNameLocalized());
            tvHourTime.setText(hour.getTime());
            if (i == nextIndex) {
                int accent = MaterialColors.getColor(view, R.attr.sanctusAccent);
                tvHourName.setTextColor(accent);
                tvHourTime.setTextColor(accent);
                tvHourName.setTypeface(tvHourName.getTypeface(), Typeface.BOLD);
            }
            listHoursMini.addView(row);
        }

        rowHeader.setOnClickListener(v -> {
            AnimUtils.pressFeedback(rowHeader);
            TransitionManager.beginDelayedTransition((ViewGroup) cardNextHour, new AutoTransition());
            boolean expanding = expandContainer.getVisibility() != View.VISIBLE;
            expandContainer.setVisibility(expanding ? View.VISIBLE : View.GONE);
            MotionKit.rotateTo(ivChevron, expanding ? 180f : 0f);
        });

        view.findViewById(R.id.row_begin_prayer).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.prayerFragment));

        // Prayer times horizontal strip
        RecyclerView rvPrayerTimes = view.findViewById(R.id.rv_prayer_times);
        rvPrayerTimes.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        PrayerTimeAdapter adapter = new PrayerTimeAdapter(hours);
        rvPrayerTimes.setAdapter(adapter);

        // Prayer of the day (Warm Parchment Card)
        TextView tvPrayerOfDayName = view.findViewById(R.id.tv_prayer_of_day_name);
        TextView tvPrayerOfDayExcerpt = view.findViewById(R.id.tv_prayer_of_day_excerpt);
        View cardPrayerOfDay = view.findViewById(R.id.card_prayer_of_day);
        ImageButton btnSharePrayer = view.findViewById(R.id.btn_share_prayer_of_day);
        viewModel.getPrayerOfTheDay().observe(getViewLifecycleOwner(), prayer -> {
            if (prayer == null) return;
            tvPrayerOfDayName.setText(prayer.name);
            tvPrayerOfDayExcerpt.setText(prayer.content);
            btnSharePrayer.setOnClickListener(v -> {
                AnimUtils.pressFeedback(btnSharePrayer);
                ShareActions.sharePrayer(requireContext(), getChildFragmentManager(), prayer);
            });
            cardPrayerOfDay.setOnClickListener(v -> {
                Bundle args = new Bundle();
                args.putInt(com.sanctus.christian.prayer.ui.prayer.PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_prayer_detail, args);
            });
        });

        // Saint of the day
        TextView tvSaintKicker = view.findViewById(R.id.tv_saint_kicker);
        TextView tvSaintInitial = view.findViewById(R.id.tv_saint_initial);
        TextView tvSaintName = view.findViewById(R.id.tv_saint_name);
        TextView tvSaintFeast = view.findViewById(R.id.tv_saint_feast);
        TextView tvSaintDesc = view.findViewById(R.id.tv_saint_desc);
        ImageButton btnShareSaint = view.findViewById(R.id.btn_share_saint_of_day);
        View cardSaint = view.findViewById(R.id.card_saint_of_day);
        cardSaint.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.saintFragment));
        String today = new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date());
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

        SaintViewModel saintViewModel = new ViewModelProvider(this).get(SaintViewModel.class);
        saintViewModel.getSaintSpotlight(today, dayOfYear).observe(getViewLifecycleOwner(), saint -> {
            if (saint != null) {
                tvSaintKicker.setText(DateUtils.isFeastToday(saint.feastDate) ? R.string.saint_of_day : R.string.saint_featured);
                tvSaintName.setText(saint.name);
                tvSaintInitial.setText(SaintNames.initialOf(saint.name));
                tvSaintFeast.setText(getString(R.string.saint_feast_day_format, DateUtils.localizeFeastDate(saint.feastDate)));
                tvSaintDesc.setText(saint.description);
                cardSaint.setOnClickListener(v -> {
                    Bundle args = new Bundle();
                    args.putInt(com.sanctus.christian.prayer.ui.saint.SaintDetailFragment.ARG_SAINT_ID, saint.id);
                    Navigation.findNavController(v).navigate(R.id.saintDetailFragment, args);
                });
                btnShareSaint.setOnClickListener(v -> {
                    AnimUtils.pressFeedback(btnShareSaint);
                    ShareActions.shareSaint(requireContext(), getChildFragmentManager(), saint);
                });
            } else {
                tvSaintName.setText(R.string.no_saint_today_title);
                tvSaintInitial.setText("☩");
                tvSaintFeast.setText("");
                tvSaintDesc.setText(R.string.no_saint_today_desc);
                btnShareSaint.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).refreshHeaderStatus();
        }
    }

    private void setupSanctuary2027Tools(View view) {
        View btnPeace = view.findViewById(R.id.btn_sanctuary_peace);
        View btnJournal = view.findViewById(R.id.btn_sanctuary_journal);
        View btnPlans = view.findViewById(R.id.btn_sanctuary_plans);

        if (btnPeace != null) {
            MotionKit.pressable(btnPeace);
            btnPeace.setOnClickListener(v -> PeaceSanctuaryDialog.show(
                    requireContext(),
                    getString(R.string.mood_peace_title),
                    getString(R.string.mood_peace_verse),
                    getString(R.string.mood_peace_prayer)));
        }
        if (btnJournal != null) {
            MotionKit.pressable(btnJournal);
            btnJournal.setOnClickListener(v -> PrayerJournalDialog.show(requireContext()));
        }
        if (btnPlans != null) {
            MotionKit.pressable(btnPlans);
            btnPlans.setOnClickListener(v -> GuidedPlansDialog.show(requireContext()));
        }

        bindMood(view, R.id.mood_peace, R.string.mood_peace_title, R.string.mood_peace_verse, R.string.mood_peace_prayer);
        bindMood(view, R.id.mood_anxiety, R.string.mood_anxiety_title, R.string.mood_anxiety_verse, R.string.mood_anxiety_prayer);
        bindMood(view, R.id.mood_gratitude, R.string.mood_gratitude_title, R.string.mood_gratitude_verse, R.string.mood_gratitude_prayer);
        bindMood(view, R.id.mood_strength, R.string.mood_strength_title, R.string.mood_strength_verse, R.string.mood_strength_prayer);
        bindMood(view, R.id.mood_sleep, R.string.mood_sleep_title, R.string.mood_sleep_verse, R.string.mood_sleep_prayer);
    }

    private void bindMood(View root, int id, int titleRes, int verseRes, int prayerRes) {
        View chip = root.findViewById(id);
        if (chip == null) return;
        MotionKit.pressable(chip);
        chip.setOnClickListener(v -> PeaceSanctuaryDialog.show(
                requireContext(), getString(titleRes), getString(verseRes), getString(prayerRes)));
    }
}
