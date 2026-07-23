package com.sanctus.christian.prayer.ui.onboarding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.ui.main.MainActivity;

public class OnboardingFragment extends Fragment {

    private final ActivityResultLauncher<String> notifPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> proceedToMain());

    private String[][] pages;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pages = new String[][]{
            {getString(R.string.onboarding_title_1), getString(R.string.onboarding_desc_1)},
            {getString(R.string.onboarding_title_2), getString(R.string.onboarding_desc_2)},
            {getString(R.string.onboarding_title_3), getString(R.string.onboarding_desc_3)},
            {getString(R.string.onboarding_title_4), getString(R.string.onboarding_desc_4)},
        };

        ViewPager2 pager = view.findViewById(R.id.onboarding_pager);
        TabLayout dots = view.findViewById(R.id.onboarding_dots);
        Button btnNext = view.findViewById(R.id.btn_onboarding_next);
        Button btnSkip = view.findViewById(R.id.btn_onboarding_skip);
        TextView tvTitle = view.findViewById(R.id.tv_onboarding_title);
        TextView tvDesc = view.findViewById(R.id.tv_onboarding_desc);

        OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(pages);
        pager.setAdapter(adapter);
        new TabLayoutMediator(dots, pager, (tab, pos) -> {}).attach();

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override public void onPageSelected(int position) {
                tvTitle.setText(pages[position][0]);
                tvDesc.setText(pages[position][1]);
                boolean isLast = position == pages.length - 1;
                btnNext.setText(isLast ? getString(R.string.onboarding_get_started) : getString(R.string.onboarding_next));
            }
        });
        tvTitle.setText(pages[0][0]);
        tvDesc.setText(pages[0][1]);

        btnNext.setOnClickListener(v -> {
            int cur = pager.getCurrentItem();
            if (cur < pages.length - 1) {
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        } else {
            proceedToMain();
        }
    }

    private void proceedToMain() {
        PrayerAlarmManager.scheduleAll(requireContext());
        startActivity(new Intent(requireContext(), MainActivity.class));
        requireActivity().finish();
    }
}
