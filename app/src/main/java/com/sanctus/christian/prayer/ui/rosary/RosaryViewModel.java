package com.sanctus.christian.prayer.ui.rosary;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.sanctus.christian.prayer.domain.model.RosaryMystery;
import com.sanctus.christian.prayer.util.DateUtils;
import java.util.Arrays;

public class RosaryViewModel extends AndroidViewModel {
    private final MutableLiveData<RosaryMystery> currentMystery = new MutableLiveData<>();
    private final MutableLiveData<String> currentPrayer = new MutableLiveData<>();
    private final MutableLiveData<String> progress = new MutableLiveData<>();
    private int beadIndex = 0;
    private int decadeIndex = 0;

    private static final String[] JOYFUL = {
            "The Annunciation", "The Visitation", "The Nativity",
            "The Presentation", "Finding in the Temple"
    };
    private static final String[] SORROWFUL = {
            "Agony in the Garden", "Scourging at the Pillar", "Crowning with Thorns",
            "Carrying the Cross", "The Crucifixion"
    };

    public RosaryViewModel(Application app) {
        super(app);
        loadTodayMystery();
    }

    private void loadTodayMystery() {
        int day = DateUtils.getDayOfWeekInt();
        String[] mysteries = (day == 1 || day == 4) ? SORROWFUL : JOYFUL;
        currentMystery.setValue(new RosaryMystery(
                (day == 1 || day == 4) ? "Sorrowful Mysteries" : "Joyful Mysteries",
                (day == 1 || day == 4) ? RosaryMystery.Type.SORROWFUL : RosaryMystery.Type.JOYFUL,
                Arrays.asList(mysteries), DateUtils.getDayOfWeek()
        ));
        updatePrayer();
    }

    private void updatePrayer() {
        String[] prayers = {"Our Father", "Hail Mary", "Glory Be"};
        if (beadIndex == 0) currentPrayer.setValue("Our Father");
        else if (beadIndex <= 10) currentPrayer.setValue("Hail Mary · " + beadIndex + " of 10");
        else currentPrayer.setValue("Glory Be");
        progress.setValue("Decade " + (decadeIndex + 1) + " · Bead " + beadIndex);
    }

    public void nextBead() {
        beadIndex++;
        if (beadIndex > 11) {
            beadIndex = 0;
            decadeIndex = Math.min(decadeIndex + 1, 4);
        }
        updatePrayer();
    }

    public LiveData<RosaryMystery> getCurrentMystery() { return currentMystery; }
    public LiveData<String> getCurrentPrayer() { return currentPrayer; }
    public LiveData<String> getProgress() { return progress; }
}
