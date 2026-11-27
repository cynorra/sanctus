package com.sanctus.christian.prayer.ui.settings;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.util.LanguageManager;
import com.sanctus.christian.prayer.util.MotionKit;
import com.sanctus.christian.prayer.util.SanctuaryStore;
import com.sanctus.christian.prayer.util.Tradition;

public class SettingsFragment extends Fragment {
    private SettingsRepository settingsRepo;
    private MaterialSwitch switchNotif;
    private View containerHours;
    private View rowExactAlarm;

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

        // Community Feed & Invite friends
        view.findViewById(R.id.row_community_feed).setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_settings_to_feed));
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
                        if (changed) restartApp();
                    })
                    .show();
        });

        // Notifications
        switchNotif = view.findViewById(R.id.switch_notifications);
        switchNotif.setChecked(settingsRepo.isNotificationsEnabled());
        switchNotif.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked && needsNotificationPermissionPrompt()) {
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
                }
            }
        });

        // Sacred Scripture Translation 2x2 Grid
        setupScriptureTranslationTiles(view);

        // Language 2x2 Quick Grid + All 40 Languages Picker
        TextView tvCurrentLang = view.findViewById(R.id.tv_current_language);
        updateLangLabel(tvCurrentLang);
        setupQuickLanguageTiles(view, tvCurrentLang);
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

        // Data & Privacy: Clear Local Cache
        View rowClearCache = view.findViewById(R.id.row_clear_cache);
        MotionKit.pressable(rowClearCache);
        rowClearCache.setOnClickListener(v -> {
            clearLocalSanctuaryCache();
            Toast.makeText(requireContext(), R.string.settings_clear_cache_done, Toast.LENGTH_SHORT).show();
        });

        // Data & Privacy: Delete Account & Cloud Data (2-Step Confirmation Modal)
        View rowDeleteAccount = view.findViewById(R.id.row_delete_account);
        MotionKit.pressable(rowDeleteAccount);
        rowDeleteAccount.setOnClickListener(v -> showDeleteAccountDialog());

        // About
        view.findViewById(R.id.row_about).setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.WEBSITE_URL)));
            } catch (Exception ignored) {
            }
        });

        // Version
        TextView tvVersion = view.findViewById(R.id.tv_version);
        tvVersion.setText("Sanctus v" + BuildConfig.VERSION_NAME);
    }

    private void setupScriptureTranslationTiles(View root) {
        TextView tRsv = root.findViewById(R.id.tile_trans_rsv);
        TextView tVul = root.findViewById(R.id.tile_trans_vulgate);
        TextView tDrb = root.findViewById(R.id.tile_trans_drb);
        TextView tEsv = root.findViewById(R.id.tile_trans_esv);

        TextView[] tiles = {tRsv, tVul, tDrb, tEsv};
        String[] codes = {"RSV-2CE", "Vulgate", "Douay-Rheims", "ESV-CE"};

        Runnable refresh = () -> {
            String active = settingsRepo.getScriptureTranslation();
            int accentColor = MaterialColors.getColor(root, R.attr.sanctusAccent);
            int mutedColor = ContextCompat.getColor(requireContext(), R.color.color_text_secondary);
            for (int i = 0; i < tiles.length; i++) {
                boolean selected = codes[i].equals(active);
                tiles[i].setBackgroundResource(selected ? R.drawable.bg_tile_selected : R.drawable.bg_tile_unselected);
                tiles[i].setTextColor(selected ? accentColor : mutedColor);
                tiles[i].setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
            }
        };

        for (int i = 0; i < tiles.length; i++) {
            final String code = codes[i];
            MotionKit.pressable(tiles[i]);
            tiles[i].setOnClickListener(v -> {
                settingsRepo.setScriptureTranslation(code);
                refresh.run();
                Toast.makeText(requireContext(), code, Toast.LENGTH_SHORT).show();
            });
        }
        refresh.run();
    }

    private void setupQuickLanguageTiles(View root, TextView tvCurrentLang) {
        TextView tEn = root.findViewById(R.id.tile_lang_en);
        TextView tTr = root.findViewById(R.id.tile_lang_tr);
        TextView tEs = root.findViewById(R.id.tile_lang_es);
        TextView tFr = root.findViewById(R.id.tile_lang_fr);

        TextView[] tiles = {tEn, tTr, tEs, tFr};
        String[] codes = {"en", "tr", "es", "fr"};
        String[] labels = {"English", "Türkçe", "Español", "Français"};

        String active = settingsRepo.getLanguage();
        int accentColor = MaterialColors.getColor(root, R.attr.sanctusAccent);
        int mutedColor = ContextCompat.getColor(requireContext(), R.color.color_text_secondary);

        for (int i = 0; i < tiles.length; i++) {
            final String code = codes[i];
            boolean selected = code.equals(active);
            tiles[i].setText(selected ? labels[i] + "  ✓" : labels[i]);
            tiles[i].setBackgroundResource(selected ? R.drawable.bg_tile_selected : R.drawable.bg_tile_unselected);
            tiles[i].setTextColor(selected ? accentColor : mutedColor);
            tiles[i].setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
            MotionKit.pressable(tiles[i]);
            tiles[i].setOnClickListener(v -> {
                boolean changed = !code.equals(settingsRepo.getLanguage());
                settingsRepo.setLanguage(code);
                updateLangLabel(tvCurrentLang);
                if (changed) restartApp();
            });
        }
    }

    private void clearLocalSanctuaryCache() {
        SanctuaryStore.clearAll(requireContext());
        try {
            java.io.File cacheDir = requireContext().getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                java.io.File[] children = cacheDir.listFiles();
                if (children != null) {
                    for (java.io.File f : children) deleteRecursive(f);
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void deleteRecursive(java.io.File f) {
        if (f.isDirectory()) {
            java.io.File[] children = f.listFiles();
            if (children != null) {
                for (java.io.File c : children) deleteRecursive(c);
            }
        }
        //noinspection ResultOfMethodCallIgnored
        f.delete();
    }

    private void showDeleteAccountDialog() {
        Context ctx = requireContext();
        float dp = ctx.getResources().getDisplayMetrics().density;
        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout root = new LinearLayout(ctx);
        root.setOrientation(LinearLayout.VERTICAL);
        int pad = (int) (22 * dp);
        root.setPadding(pad, pad, pad, pad);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(0xFF131622);
        bg.setCornerRadius(24 * dp);
        bg.setStroke((int) (1.2f * dp), 0x558F1D1D);
        root.setBackground(bg);

        // STEP 1 CONTAINER
        LinearLayout step1 = new LinearLayout(ctx);
        step1.setOrientation(LinearLayout.VERTICAL);

        TextView eyebrow1 = new TextView(ctx);
        eyebrow1.setText(R.string.delete_step1_eyebrow);
        eyebrow1.setAllCaps(true);
        eyebrow1.setTextSize(10f);
        eyebrow1.setLetterSpacing(0.18f);
        eyebrow1.setTypeface(Typeface.DEFAULT_BOLD);
        eyebrow1.setTextColor(0xFFC94A4A);
        step1.addView(eyebrow1);

        TextView title1 = new TextView(ctx);
        title1.setText(R.string.delete_step1_title);
        title1.setTextSize(20f);
        title1.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title1.setTextColor(0xFFF5F2EB);
        LinearLayout.LayoutParams t1Lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        t1Lp.topMargin = (int) (4 * dp);
        step1.addView(title1, t1Lp);

        TextView desc1 = new TextView(ctx);
        desc1.setText(R.string.delete_step1_desc);
        desc1.setTextSize(12.5f);
        desc1.setTextColor(0xFF9E988D);
        desc1.setLineSpacing(0f, 1.35f);
        LinearLayout.LayoutParams d1Lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        d1Lp.topMargin = (int) (12 * dp);
        step1.addView(desc1, d1Lp);

        LinearLayout box = new LinearLayout(ctx);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding((int) (14 * dp), (int) (12 * dp), (int) (14 * dp), (int) (12 * dp));
        GradientDrawable boxBg = new GradientDrawable();
        boxBg.setColor(0xFF171B28);
        boxBg.setCornerRadius(16 * dp);
        boxBg.setStroke((int) dp, 0x14FFFFFF);
        box.setBackground(boxBg);
        LinearLayout.LayoutParams boxLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        boxLp.topMargin = (int) (14 * dp);
        int[] bulletRes = {R.string.delete_step1_item1, R.string.delete_step1_item2, R.string.delete_step1_item3};
        for (int i = 0; i < bulletRes.length; i++) {
            TextView item = new TextView(ctx);
            item.setText("✕  " + ctx.getString(bulletRes[i]));
            item.setTextSize(12f);
            item.setTextColor(0xFF9E988D);
            if (i > 0) item.setPadding(0, (int) (8 * dp), 0, 0);
            box.addView(item);
        }
        step1.addView(box, boxLp);

        TextView btnContinue = createModalPill(ctx, dp, ctx.getString(R.string.delete_continue), 0xFFC94A4A, 0x1A8F1D1D, 0x558F1D1D);
        LinearLayout.LayoutParams b1Lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (44 * dp));
        b1Lp.topMargin = (int) (16 * dp);
        step1.addView(btnContinue, b1Lp);

        TextView btnLocalOnly = createModalPill(ctx, dp, ctx.getString(R.string.delete_clear_local_only), 0xFFE6E1D6, 0xFF11141E, 0x18FFFFFF);
        LinearLayout.LayoutParams b2Lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (44 * dp));
        b2Lp.topMargin = (int) (8 * dp);
        btnLocalOnly.setOnClickListener(v -> {
            clearLocalSanctuaryCache();
            dialog.dismiss();
            Toast.makeText(ctx, R.string.settings_clear_cache_done, Toast.LENGTH_SHORT).show();
        });
        step1.addView(btnLocalOnly, b2Lp);

        TextView btnKeep = new TextView(ctx);
        btnKeep.setText(R.string.delete_keep_account);
        btnKeep.setGravity(Gravity.CENTER);
        btnKeep.setTextSize(12.5f);
        btnKeep.setTextColor(0xFF9E988D);
        btnKeep.setPadding(0, (int) (12 * dp), 0, (int) (4 * dp));
        btnKeep.setOnClickListener(v -> dialog.dismiss());
        step1.addView(btnKeep);

        // STEP 2 CONTAINER
        LinearLayout step2 = new LinearLayout(ctx);
        step2.setOrientation(LinearLayout.VERTICAL);
        step2.setVisibility(View.GONE);

        TextView eyebrow2 = new TextView(ctx);
        eyebrow2.setText(R.string.delete_step2_eyebrow);
        eyebrow2.setAllCaps(true);
        eyebrow2.setTextSize(10f);
        eyebrow2.setLetterSpacing(0.18f);
        eyebrow2.setTypeface(Typeface.DEFAULT_BOLD);
        eyebrow2.setTextColor(0xFFC94A4A);
        step2.addView(eyebrow2);

        TextView title2 = new TextView(ctx);
        title2.setText(R.string.delete_step2_title);
        title2.setTextSize(20f);
        title2.setTypeface(Typeface.SERIF, Typeface.BOLD);
        title2.setTextColor(0xFFF5F2EB);
        step2.addView(title2, t1Lp);

        TextView desc2 = new TextView(ctx);
        desc2.setText(R.string.delete_step2_desc);
        desc2.setTextSize(12.5f);
        desc2.setTextColor(0xFF9E988D);
        desc2.setLineSpacing(0f, 1.35f);
        step2.addView(desc2, d1Lp);

        EditText etConfirm = new EditText(ctx);
        etConfirm.setHint(R.string.delete_input_hint);
        etConfirm.setHintTextColor(0xFF5C564E);
        etConfirm.setTextColor(0xFFF5F2EB);
        etConfirm.setTextSize(13.5f);
        etConfirm.setSingleLine(true);
        etConfirm.setPadding((int) (14 * dp), (int) (12 * dp), (int) (14 * dp), (int) (12 * dp));
        GradientDrawable inputBg = new GradientDrawable();
        inputBg.setColor(0xFF07090E);
        inputBg.setCornerRadius(12 * dp);
        inputBg.setStroke((int) dp, 0x22FFFFFF);
        etConfirm.setBackground(inputBg);
        LinearLayout.LayoutParams etLp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        etLp.topMargin = (int) (14 * dp);
        step2.addView(etConfirm, etLp);

        TextView btnFinalDelete = createModalPill(ctx, dp, ctx.getString(R.string.delete_confirm_btn), 0x66C94A4A, 0x1A8F1D1D, 0x338F1D1D);
        btnFinalDelete.setEnabled(false);
        step2.addView(btnFinalDelete, b1Lp);

        etConfirm.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean ready = "DELETE".equals(s.toString().trim());
                btnFinalDelete.setEnabled(ready);
                GradientDrawable d = new GradientDrawable();
                d.setCornerRadius(22 * dp);
                if (ready) {
                    d.setColor(0xFF8F1D1D);
                    d.setStroke((int) dp, 0xFFC94A4A);
                    btnFinalDelete.setTextColor(0xFFF5F2EB);
                } else {
                    d.setColor(0x1A8F1D1D);
                    d.setStroke((int) dp, 0x338F1D1D);
                    btnFinalDelete.setTextColor(0x66C94A4A);
                }
                btnFinalDelete.setBackground(d);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnFinalDelete.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                user.delete().addOnCompleteListener(task -> FirebaseAuth.getInstance().signOut());
            } else {
                FirebaseAuth.getInstance().signOut();
            }
            clearLocalSanctuaryCache();
            settingsRepo.clearAllPreferences();
            PrayerAlarmManager.cancelAll(ctx);
            dialog.dismiss();
            Toast.makeText(ctx, R.string.delete_done_toast, Toast.LENGTH_LONG).show();
            restartApp();
        });

        TextView btnBack = createModalPill(ctx, dp, ctx.getString(R.string.delete_back_btn), 0xFF9E988D, 0xFF11141E, 0x18FFFFFF);
        btnBack.setOnClickListener(v -> {
            step2.setVisibility(View.GONE);
            step1.setVisibility(View.VISIBLE);
        });
        step2.addView(btnBack, b2Lp);

        btnContinue.setOnClickListener(v -> {
            step1.setVisibility(View.GONE);
            step2.setVisibility(View.VISIBLE);
        });

        root.addView(step1);
        root.addView(step2);

        dialog.setContentView(root);
        Window win = dialog.getWindow();
        if (win != null) {
            win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = (int) (ctx.getResources().getDisplayMetrics().widthPixels - 36 * dp);
            win.setAttributes(lp);
        }
        dialog.show();
    }

    private TextView createModalPill(Context ctx, float dp, String text, int textColor, int fillColor, int strokeColor) {
        TextView tv = new TextView(ctx);
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(13f);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(textColor);
        GradientDrawable d = new GradientDrawable();
        d.setColor(fillColor);
        d.setStroke((int) dp, strokeColor);
        d.setCornerRadius(22 * dp);
        tv.setBackground(d);
        return tv;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateExactAlarmRow();
    }

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
            TextView tvName = row.findViewById(R.id.tv_hour_setting_name);
            TextView tvTime = row.findViewById(R.id.tv_hour_setting_time);
            MaterialSwitch sw = row.findViewById(R.id.switch_hour_setting);
            tvName.setText(names[i] + " (" + formatTime(settingsRepo.getHourMinutes(i)) + ")");
            tvTime.setText(getString(R.string.dashboard_divine_office));
            sw.setChecked(settingsRepo.isHourEnabled(i));
            tvName.setAlpha(sw.isChecked() ? 1f : 0.55f);
            sw.setOnCheckedChangeListener((btn, on) -> {
                settingsRepo.setHourEnabled(index, on);
                tvName.setAlpha(on ? 1f : 0.55f);
                PrayerAlarmManager.scheduleAll(requireContext());
            });
            View.OnClickListener timeClick = v -> {
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
                    tvName.setText(names[index] + " (" + formatTime(settingsRepo.getHourMinutes(index)) + ")");
                    if (settingsRepo.isHourEnabled(index)) {
                        PrayerAlarmManager.scheduleAll(requireContext());
                    }
                });
                picker.show(getChildFragmentManager(), "hour_time_" + index);
            };
            tvName.setOnClickListener(timeClick);
            tvTime.setOnClickListener(timeClick);
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
        tv.setText(lang.nativeName);
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

    private void restartApp() {
        android.app.Activity activity = requireActivity();
        Intent intent = new Intent(activity, com.sanctus.christian.prayer.ui.main.MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        activity.finish();
    }
}
