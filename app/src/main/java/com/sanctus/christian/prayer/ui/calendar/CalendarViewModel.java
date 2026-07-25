package com.sanctus.christian.prayer.ui.calendar;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.dao.CalendarEventDao;
import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import java.util.List;

public class CalendarViewModel extends AndroidViewModel {
    private final CalendarEventDao dao;
    private final String language;

    public CalendarViewModel(Application app) {
        super(app);
        dao = SanctusDatabase.getInstance(app).calendarEventDao();
        language = SanctusApplication.getInstance().getSettingsRepository().getLanguage();
    }

    /** Falls back to English when the selected language has no translated calendar content yet. */
    public LiveData<List<CalendarEventEntity>> getAllEvents() {
        if ("en".equals(language)) return dao.getAllEvents("en");
        return Transformations.switchMap(dao.getAllEvents(language), list ->
                (list == null || list.isEmpty()) ? dao.getAllEvents("en") : staticLiveData(list));
    }

    private static LiveData<List<CalendarEventEntity>> staticLiveData(List<CalendarEventEntity> list) {
        MutableLiveData<List<CalendarEventEntity>> live = new MutableLiveData<>();
        live.setValue(list);
        return live;
    }
}
