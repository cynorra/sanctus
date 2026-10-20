package com.sanctus.christian.prayer.ui.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.util.LanguageManager;
import com.sanctus.christian.prayer.util.Tradition;

public class SettingsFragment extends Fragment {
    private SettingsRepository settingsRepo;
    private MaterialSwitch switchNotif;
    private View containerHours;
    private View rowExactAlarm;

    // Denial isn't silently ignored: reverting the switch re-enters the checked-change listener
    // with isChecked=false, which persists "disabled" and cancels alarms/unsubscribes the topic —
    // so the UI and the actual notification state can never disagree.
    private final ActivityResultLauncher<String> notifPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    applyNotificationsEnabled(true);
                } else {
                    switchNotif.setChecked(false);
                }
            });

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

        // Tradition
        TextView tvTradition = view.findViewById(R.id.tv_current_tradition);
        tvTradition.setText(Tradition.labelRes(settingsRepo.getTradition()));
        view.findViewById(R.id.row_tradition).setOnClickListener(v -> {
            String[] ids = Tradition.CHOICES;
            CharSequence[] labels = new CharSequence[ids.length];
            int checked = 0;
            for (int i = 0; i < ids.length; i++) {
                labels[i] = getString(Tradition.labelRes(ids[i]));
                if (ids[i].equals(settingsRepo.getTradition())) checked = i;
            }
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.settings_tradition_title)
                    .setSingleChoiceItems(labels, checked, (dialog, which) -> {
                        boolean changed = !ids[which].equals(settingsRepo.getTradition());
                        settingsRepo.setTradition(ids[which]);
                        tvTradition.setText(Tradition.labelRes(ids[which]));
                        dialog.dismiss();
                        // Restart so the palette, calendar and every tab's cached filters apply.
                        if (changed) restartApp();
                    })
                    .show();
        });

        // Notifications
        switchNotif = view.findViewById(R.id.switch_notifications);
        switchNotif.setChecked(settingsRepo.isNotificationsEnabled());
        switchNotif.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked && needsNotificationPermissionPrompt()) {
                // Persistence/scheduling happens in the launcher callback once we know whether
                // the user actually granted it — not here, so a denial can't leave the switch
                // showing "on" while nothing is actually scheduled.
                notifPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                return;
            }
            applyNotificationsEnabled(isChecked);
        });

        // Per-hour reminder times
        containerHours = view.findViewById(R.id.container_hour_times);
        buildHourRows((ViewGroup) containerHours);
        containerHours.setVisibility(switchNotif.isChecked() ? View.VISIBLE : View.GONE);
        rowExactAlarm = view.findViewById(R.id.row_exact_alarm);
        rowExactAlarm.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                try {
                    startActivity(new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                            Uri.parse("package:" + requireContext().getPackageName())));
                } catch (Exception ignored) {
                    // Settings screen unavailable; reminders simply stay approximate.
                }
            }
        });

        // Language picker row
        TextView tvCurrentLang = view.findViewById(R.id.tv_current_language);
        updateLangLabel(tvCurrentLang);
        view.findViewById(R.id.row_language).setOnClickListener(v -> {
            LanguagePickerDialogFragment dialog = new LanguagePickerDialogFragment();
            dialog.setListener(code -> {
                boolean changed = !code.equals(settingsRepo.getLanguage());
                settingsRepo.setLanguage(code);
                updateLangLabel(tvCurrentLang);
                if (changed) restartApp();
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

    @Override
    public void onResume() {
        super.onResume();
        updateExactAlarmRow();
    }

    /** Shown only when reminders are on but the OS won't let them fire on the minute. */
    private void updateExactAlarmRow() {
        if (rowExactAlarm == null) return;
        boolean show = switchNotif.isChecked() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && !PrayerAlarmManager.canScheduleExact(requireContext());
        rowExactAlarm.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void buildHourRows(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        String[] names = getResources().getStringArray(R.array.hour_names_short);
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) {
            final int index = i;
            View row = inflater.inflate(R.layout.item_hour_setting, parent, false);
            ((TextView) row.findViewById(R.id.tv_hour_setting_name)).setText(names[i]);
            TextView tvTime = row.findViewById(R.id.tv_hour_setting_time);
            MaterialSwitch sw = row.findViewById(R.id.switch_hour_setting);
            tvTime.setText(formatTime(settingsRepo.getHourMinutes(i)));
            sw.setChecked(settingsRepo.isHourEnabled(i));
            tvTime.setAlpha(sw.isChecked() ? 1f : 0.4f);
            sw.setOnCheckedChangeListener((btn, on) -> {
                settingsRepo.setHourEnabled(index, on);
                tvTime.setAlpha(on ? 1f : 0.4f);
                PrayerAlarmManager.scheduleAll(requireContext());
            });
            tvTime.setOnClickListener(v -> {
                int minutes = settingsRepo.getHourMinutes(index);
                MaterialTimePicker picker = new MaterialTimePicker.Builder()
                        .setTimeFormat(DateFormat.is24HourFormat(requireContext())
                                ? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H)
                        .setHour(minutes / 60)
                        .setMinute(minutes % 60)
                        .setTitleText(names[index])
                        .build();
                picker.addOnPositiveButtonClickListener(d -> {
                    settingsRepo.setHourMinutes(index, picker.getHour() * 60 + picker.getMinute());
                    tvTime.setText(formatTime(settingsRepo.getHourMinutes(index)));
                    if (settingsRepo.isHourEnabled(index)) {
                        PrayerAlarmManager.scheduleAll(requireContext());
                    }
                });
                picker.show(getChildFragmentManager(), "hour_time_" + index);
            });
            parent.addView(row);
        }
    }

    private String formatTime(int minutesOfDay) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(java.util.Calendar.HOUR_OF_DAY, minutesOfDay / 60);
        cal.set(java.util.Calendar.MINUTE, minutesOfDay % 60);
        return DateFormat.getTimeFormat(requireContext()).format(cal.getTime());
    }

    private void updateLangLabel(TextView tv) {
        LanguageManager.Language lang = LanguageManager.getLanguage(settingsRepo.getLanguage());
        tv.setText(lang.nativeName + " (" + lang.englishName + ")");
    }

    private boolean needsNotificationPermissionPrompt() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED;
    }

    private void applyNotificationsEnabled(boolean enabled) {
        settingsRepo.setNotificationsEnabled(enabled);
        containerHours.setVisibility(enabled ? View.VISIBLE : View.GONE);
        updateExactAlarmRow();
        if (enabled) {
            PrayerAlarmManager.scheduleAll(requireContext());
            FirebaseMessaging.getInstance().subscribeToTopic(SanctusApplication.ENGAGEMENT_TOPIC);
        } else {
            PrayerAlarmManager.cancelAll(requireContext());
            FirebaseMessaging.getInstance().unsubscribeFromTopic(SanctusApplication.ENGAGEMENT_TOPIC);
        }
    }

    /**
     * recreate() keeps the ViewModels, so tabs would keep the old language/tradition filters.
     * A fresh task start drops them and also re-applies the app locale and theme overlay.
     */
    private void restartApp() {
        android.app.Activity activity = requireActivity();
        Intent intent = new Intent(activity, com.sanctus.christian.prayer.ui.main.MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.finish();
    }
}
