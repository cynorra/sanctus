package com.sanctus.christian.prayer.ui.rosary;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.analytics.AnalyticsLogger;
import com.sanctus.christian.prayer.domain.model.RosaryMystery;
import com.sanctus.christian.prayer.util.DateUtils;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RosaryViewModel extends AndroidViewModel {
    private static final int DECADE_COUNT = 5;

    private final MutableLiveData<RosaryMystery> currentMystery = new MutableLiveData<>();
    private final MutableLiveData<String> currentMysteryLine = new MutableLiveData<>();
    private final MutableLiveData<String> currentPrayer = new MutableLiveData<>();
    private final MutableLiveData<String> progress = new MutableLiveData<>();
    private final MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
    private int beadIndex = 0;
    private int decadeIndex = 0;

    public RosaryViewModel(Application app) {
        super(app);
        loadTodayMystery();
    }

    /**
     * Application-context resources follow the device language, not the in-app one, so the
     * rosary text came out in a different language than the rest of the screen.
     */
    private Resources localizedResources() {
        String lang = SanctusApplication.getInstance().getSettingsRepository().getLanguage();
        Configuration config = new Configuration(getApplication().getResources().getConfiguration());
        config.setLocale(new Locale(lang));
        return getApplication().createConfigurationContext(config).getResources();
    }

    // Traditional weekday schedule: Mon/Sat Joyful, Tue/Fri Sorrowful, Wed/Sun Glorious, Thu Luminous
    private RosaryMystery.Type typeForDay(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.MONDAY:
            case Calendar.SATURDAY:
                return RosaryMystery.Type.JOYFUL;
            case Calendar.TUESDAY:
            case Calendar.FRIDAY:
                return RosaryMystery.Type.SORROWFUL;
            case Calendar.THURSDAY:
                return RosaryMystery.Type.LUMINOUS;
            default: // Wednesday, Sunday
                return RosaryMystery.Type.GLORIOUS;
        }
    }

    @ArrayRes
    private int mysteriesArrayFor(RosaryMystery.Type type) {
        switch (type) {
            case SORROWFUL: return R.array.mysteries_sorrowful;
            case GLORIOUS: return R.array.mysteries_glorious;
            case LUMINOUS: return R.array.mysteries_luminous;
            default: return R.array.mysteries_joyful;
        }
    }

    @StringRes
    private int titleFor(RosaryMystery.Type type) {
        switch (type) {
            case SORROWFUL: return R.string.sorrowful_mysteries;
            case GLORIOUS: return R.string.glorious_mysteries;
            case LUMINOUS: return R.string.luminous_mysteries;
            default: return R.string.joyful_mysteries;
        }
    }

    private void loadTodayMystery() {
        Resources res = localizedResources();
        int day = DateUtils.getDayOfWeekInt();
        RosaryMystery.Type type = typeForDay(day);
        currentMystery.setValue(new RosaryMystery(
                res.getString(titleFor(type)),
                type,
                Arrays.asList(res.getStringArray(mysteriesArrayFor(type))),
                DateUtils.getDayOfWeek()
        ));
        updatePrayer();
    }

    private void updatePrayer() {
        Resources res = localizedResources();

        RosaryMystery mystery = currentMystery.getValue();
        if (mystery != null) {
            List<String> lines = mystery.getMysteries();
            int idx = Math.min(decadeIndex, lines.size() - 1);
            currentMysteryLine.setValue((idx + 1) + ". " + lines.get(idx));
        }

        if (beadIndex == 0) currentPrayer.setValue(res.getString(R.string.prayer_our_father));
        else if (beadIndex <= 10) currentPrayer.setValue(res.getString(R.string.rosary_hail_mary_progress, beadIndex));
        else currentPrayer.setValue(res.getString(R.string.prayer_glory_be));
        progress.setValue(res.getString(R.string.rosary_progress, decadeIndex + 1, beadIndex));
    }

    public void nextBead() {
        if (Boolean.TRUE.equals(finished.getValue())) return;

        beadIndex++;
        if (beadIndex > 11) {
            beadIndex = 0;
            if (decadeIndex >= DECADE_COUNT - 1) {
                showClosingPrayer();
                return;
            }
            decadeIndex++;
        }
        updatePrayer();
    }

    private void showClosingPrayer() {
        Resources res = localizedResources();
        currentMysteryLine.setValue("");
        currentPrayer.setValue(res.getString(R.string.prayer_hail_holy_queen));
        progress.setValue(res.getString(R.string.rosary_complete));
        finished.setValue(true);
        SanctusApplication.getInstance().getSettingsRepository().recordPrayerActivityToday();
        AnalyticsLogger.logRosaryCompleted(getApplication());
    }

    public LiveData<RosaryMystery> getCurrentMystery() { return currentMystery; }
    public LiveData<String> getCurrentMysteryLine() { return currentMysteryLine; }
    public LiveData<String> getCurrentPrayer() { return currentPrayer; }
    public LiveData<String> getProgress() { return progress; }
    public LiveData<Boolean> getFinished() { return finished; }
}
