package com.sanctus.christian.prayer.ui.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.util.LanguageManager;

public class SettingsFragment extends Fragment {
    private SettingsRepository settingsRepo;
    private static final int REQ_NOTIF = 101;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        settingsRepo = SanctusApplication.getInstance().getSettingsRepository();

        // Invite friends
        view.findViewById(R.id.row_invite).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_settings_to_invite));

        // Dark mode
        MaterialSwitch switchDark = view.findViewById(R.id.switch_dark_mode);
        switchDark.setChecked(settingsRepo.isDarkMode());
        switchDark.setOnCheckedChangeListener((btn, isChecked) -> {
            settingsRepo.setDarkMode(isChecked);
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });

        // Notifications
        MaterialSwitch switchNotif = view.findViewById(R.id.switch_notifications);
        switchNotif.setChecked(settingsRepo.isNotificationsEnabled());
        switchNotif.setOnCheckedChangeListener((btn, isChecked) -> {
            settingsRepo.setNotificationsEnabled(isChecked);
            if (isChecked) {
                requestNotificationPermission();
                PrayerAlarmManager.scheduleAll(requireContext());
            } else {
                PrayerAlarmManager.cancelAll(requireContext());
            }
        });

        // Language picker row
        TextView tvCurrentLang = view.findViewById(R.id.tv_current_language);
        updateLangLabel(tvCurrentLang);
        view.findViewById(R.id.row_language).setOnClickListener(v -> {
            LanguagePickerDialogFragment dialog = new LanguagePickerDialogFragment();
            dialog.setListener(code -> {
                settingsRepo.setLanguage(code);
                updateLangLabel(tvCurrentLang);
            });
            dialog.show(getChildFragmentManager(), "lang_picker");
        });

        // About
        view.findViewById(R.id.row_about).setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WEBSITE_URL)));
            } catch (Exception ignored) {
                // No browser available to handle the intent — nothing more we can do here.
            }
        });

        // Version
        TextView tvVersion = view.findViewById(R.id.tv_version);
        tvVersion.setText("Sanctus v" + BuildConfig.VERSION_NAME);
    }

    private void updateLangLabel(TextView tv) {
        LanguageManager.Language lang = LanguageManager.getLanguage(settingsRepo.getLanguage());
        tv.setText(lang.nativeName + " (" + lang.englishName + ")");
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_NOTIF);
        }
    }
}
