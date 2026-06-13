package com.sanctus.christian.prayer.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.ui.main.MainActivity;

public class OnboardingFragment extends Fragment {

    private static final String[][] PAGES = {
        {"Welcome to Sanctus", "Your sacred companion for the Liturgy of the Hours, Rosary, Saints, and Liturgical Calendar."},
        {"Canonical Hours", "Receive gentle reminders for all six canonical hours — Lauds, Terce, Sext, None, Vespers, and Compline."},
        {"Holy Rosary", "Guided Rosary with automatic mystery selection based on the day of the week."},
        {"Saints & Calendar", "Discover the Saint of the Day and explore the full liturgical calendar — feasts, solemnities, and seasons."},
    };

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 pager = view.findViewById(R.id.onboarding_pager);
        TabLayout dots = view.findViewById(R.id.onboarding_dots);
        Button btnNext = view.findViewById(R.id.btn_onboarding_next);
        Button btnSkip = view.findViewById(R.id.btn_onboarding_skip);
        TextView tvTitle = view.findViewById(R.id.tv_onboarding_title);
        TextView tvDesc = view.findViewById(R.id.tv_onboarding_desc);

        OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(PAGES);
        pager.setAdapter(adapter);
        new TabLayoutMediator(dots, pager, (tab, pos) -> {}).attach();

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                tvTitle.setText(PAGES[position][0]);
                tvDesc.setText(PAGES[position][1]);
                boolean isLast = position == PAGES.length - 1;
                btnNext.setText(isLast ? "Get Started" : "Next");
            }
        });
        tvTitle.setText(PAGES[0][0]);
        tvDesc.setText(PAGES[0][1]);

        btnNext.setOnClickListener(v -> {
            int cur = pager.getCurrentItem();
            if (cur < PAGES.length - 1) {
                pager.setCurrentItem(cur + 1);
            } else {
                finishOnboarding();
            }
        });
        btnSkip.setOnClickListener(v -> finishOnboarding());
    }

    private void finishOnboarding() {
        SanctusApplication.getInstance().getSettingsRepository()
                .setNotificationsEnabled(true);
        PrayerAlarmManager.scheduleAll(requireContext());
        startActivity(new Intent(requireContext(), MainActivity.class));
        requireActivity().finish();
    }
}
