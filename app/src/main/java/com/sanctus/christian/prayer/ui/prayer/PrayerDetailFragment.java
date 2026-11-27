package com.sanctus.christian.prayer.ui.prayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.analytics.AnalyticsLogger;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import com.sanctus.christian.prayer.util.AnimUtils;
import java.util.concurrent.Executors;

public class PrayerDetailFragment extends Fragment {

    public static final String ARG_PRAYER_ID = "prayer_id";
    private android.speech.tts.TextToSpeech tts;
    private android.os.CountDownTimer silenceTimer;
    private boolean isSpeaking = false;
    private String currentSpeechText = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prayer_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int prayerId = getArguments() != null ? getArguments().getInt(ARG_PRAYER_ID, -1) : -1;

        TextView tvTitle = view.findViewById(R.id.tv_detail_title);
        TextView tvCategory = view.findViewById(R.id.tv_detail_category);
        TextView tvContent = view.findViewById(R.id.tv_detail_content);
        ImageButton btnBack = view.findViewById(R.id.btn_back);
        ImageButton btnFavorite = view.findViewById(R.id.btn_favorite_detail);
        ImageButton btnFontIncrease = view.findViewById(R.id.btn_font_increase);
        ImageButton btnFontDecrease = view.findViewById(R.id.btn_font_decrease);
        ImageButton btnShare = view.findViewById(R.id.btn_share_detail);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        int[] fontSize = {
            SanctusApplication.getInstance().getSettingsRepository().getTextSize()
        };
        tvContent.setTextSize(fontSize[0] + 6);

        btnFontIncrease.setOnClickListener(v -> {
            if (fontSize[0] < 26) {
                fontSize[0] += 2;
                tvContent.setTextSize(fontSize[0] + 6);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });
        btnFontDecrease.setOnClickListener(v -> {
            if (fontSize[0] > 12) {
                fontSize[0] -= 2;
                tvContent.setTextSize(fontSize[0] + 6);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });

        if (prayerId != -1) {
            PrayerRepository repo = new PrayerRepository(requireContext());
            repo.getPrayerById(prayerId).observe(getViewLifecycleOwner(), prayer -> {
                if (prayer == null) return;
                tvTitle.setText(prayer.name);
                tvCategory.setText(com.sanctus.christian.prayer.util.CategoryLabels.label(requireContext(), prayer.category));
                tvContent.setText(prayer.content);
                currentSpeechText = prayer.name + ". " + prayer.content;
                setupAudioAndTimer(view);
                SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
                AnalyticsLogger.logPrayerCompleted(requireContext(), prayer.category);
                btnFavorite.setSelected(prayer.isFavorite);
                btnFavorite.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        prayer.isFavorite = !prayer.isFavorite;
                        prayer.lastReadAt = System.currentTimeMillis();
                        com.sanctus.christian.prayer.data.local.SanctusDatabase
                                .getInstance(requireContext()).prayerDao().update(prayer);
                    });
                    btnFavorite.setSelected(!btnFavorite.isSelected());
                    AnimUtils.bounce(btnFavorite);
                });
                btnShare.setOnClickListener(v -> {
                    AnimUtils.pressFeedback(btnShare);
                    String text = getString(R.string.share_prayer_text,
                            prayer.name, prayer.content, BuildConfig.PLAY_STORE_URL);
                    com.sanctus.christian.prayer.share.ShareSheetDialogFragment
                            .create(prayer.name, prayer.content, text, getString(R.string.share_prayer_chooser_title))
                            .show(getChildFragmentManager(), "share_sheet");
                });
            });
        }
    }

    private void setupAudioAndTimer(View root) {
        TextView btnListen = root.findViewById(R.id.btn_audio_listen);
        TextView btnTimer = root.findViewById(R.id.btn_silence_timer);
        if (btnListen != null) {
            btnListen.setOnClickListener(v -> {
                AnimUtils.pressFeedback(btnListen);
                if (isSpeaking && tts != null) {
                    tts.stop();
                    isSpeaking = false;
                    btnListen.setText(R.string.prayer_audio_listen);
                    return;
                }
                if (tts == null) {
                    tts = new android.speech.tts.TextToSpeech(requireContext().getApplicationContext(), status -> {
                        if (status == android.speech.tts.TextToSpeech.SUCCESS && isAdded() && tts != null) {
                            tts.setLanguage(java.util.Locale.getDefault());
                            tts.setSpeechRate(0.85f);
                            tts.speak(currentSpeechText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "sanctus_prayer");
                            isSpeaking = true;
                            btnListen.setText(R.string.prayer_audio_stop);
                        }
                    });
                } else {
                    tts.speak(currentSpeechText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null, "sanctus_prayer");
                    isSpeaking = true;
                    btnListen.setText(R.string.prayer_audio_stop);
                }
            });
        }
        if (btnTimer != null) {
            btnTimer.setOnClickListener(v -> {
                AnimUtils.pressFeedback(btnTimer);
                if (silenceTimer != null) {
                    silenceTimer.cancel();
                    silenceTimer = null;
                    btnTimer.setText(R.string.prayer_silence_timer);
                    return;
                }
                silenceTimer = new android.os.CountDownTimer(60_000L, 1000L) {
                    @Override
                    public void onTick(long ms) {
                        if (isAdded()) {
                            btnTimer.setText(getString(R.string.prayer_silence_countdown_fmt, (int) (ms / 1000L)));
                        }
                    }

                    @Override
                    public void onFinish() {
                        silenceTimer = null;
                        if (isAdded()) {
                            btnTimer.setText(R.string.prayer_silence_done);
                            btnTimer.performHapticFeedback(android.view.HapticFeedbackConstants.LONG_PRESS);
                        }
                    }
                }.start();
            });
        }
    }

    @Override
    public void onDestroyView() {
        if (silenceTimer != null) {
            silenceTimer.cancel();
            silenceTimer = null;
        }
        if (tts != null) {
            try {
                tts.stop();
                tts.shutdown();
            } catch (Exception ignored) {
            }
            tts = null;
        }
        isSpeaking = false;
        super.onDestroyView();
    }
}
