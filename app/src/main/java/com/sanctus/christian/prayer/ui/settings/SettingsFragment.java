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
                        settingsRepo.setTradition(ids[which]);
                        tvTradition.setText(Tradition.labelRes(ids[which]));
                        dialog.dismiss();
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

    private boolean needsNotificationPermissionPrompt() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED;
    }

    private void applyNotificationsEnabled(boolean enabled) {
        settingsRepo.setNotificationsEnabled(enabled);
        if (enabled) {
            PrayerAlarmManager.scheduleAll(requireContext());
            FirebaseMessaging.getInstance().subscribeToTopic(SanctusApplication.ENGAGEMENT_TOPIC);
        } else {
            PrayerAlarmManager.cancelAll(requireContext());
            FirebaseMessaging.getInstance().unsubscribeFromTopic(SanctusApplication.ENGAGEMENT_TOPIC);
        }
    }
}
