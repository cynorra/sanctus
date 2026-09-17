package com.sanctus.christian.prayer.ui.saint;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.dao.SaintDao;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import java.util.List;

public class SaintViewModel extends AndroidViewModel {
    private final SaintDao dao;
    private final String language;
    private final MutableLiveData<String> query = new MutableLiveData<>("");
    private final LiveData<List<SaintEntity>> results;

    public SaintViewModel(Application app) {
        super(app);
        dao = SanctusDatabase.getInstance(app).saintDao();
        language = SanctusApplication.getInstance().getSettingsRepository().getLanguage();
        results = Transformations.switchMap(query, q -> {
            boolean empty = q == null || q.isEmpty();
            LiveData<List<SaintEntity>> primary = empty ? dao.getAllSaints(language) : dao.searchSaints(q, language);
            if ("en".equals(language)) return primary;
            LiveData<List<SaintEntity>> fallback = empty ? dao.getAllSaints("en") : dao.searchSaints(q, "en");
            return withListFallback(primary, fallback);
        });
    }

    /** Single source of truth for the saint list screen: reacts to search query changes, no duplicate observers. */
    public LiveData<List<SaintEntity>> getResults() { return results; }

    public void setQuery(String q) { query.setValue(q); }

    /** Falls back to English when the selected language has no saint for this feast date yet. */
    public LiveData<SaintEntity> getSaintByFeastDate(String date) {
        LiveData<SaintEntity> primary = dao.getSaintByFeastDate(date, language);
        if ("en".equals(language)) return primary;
        return Transformations.switchMap(primary, saint ->
                saint == null ? dao.getSaintByFeastDate(date, "en") : staticLiveData(saint));
    }

    /**
     * Only ~60 of the 365 feast days have a matching saint, so most days getSaintByFeastDate()
     * falls through to the same static "no saint today" message. This picks a rotating spotlight
     * (deterministic by day-of-year, not random) from the full list instead, so there's always
     * something different to show.
     */
    public LiveData<SaintEntity> getSaintSpotlight(String date, int dayOfYear) {
        return Transformations.switchMap(getSaintByFeastDate(date), exact ->
                exact != null ? staticLiveData(exact) : rotatingFallback(dayOfYear));
    }

    private LiveData<SaintEntity> rotatingFallback(int dayOfYear) {
        LiveData<List<SaintEntity>> primary = dao.getAllSaints(language);
        if ("en".equals(language)) return Transformations.map(primary, list -> pickForDay(list, dayOfYear));
        return Transformations.switchMap(primary, list -> {
            if (list != null && !list.isEmpty()) return staticLiveData(pickForDay(list, dayOfYear));
            return Transformations.map(dao.getAllSaints("en"), l -> pickForDay(l, dayOfYear));
        });
    }

    private static SaintEntity pickForDay(List<SaintEntity> list, int dayOfYear) {
        if (list == null || list.isEmpty()) return null;
        return list.get(dayOfYear % list.size());
    }

    public LiveData<SaintEntity> getSaintById(int id) { return dao.getSaintById(id); }

    private static LiveData<List<SaintEntity>> withListFallback(LiveData<List<SaintEntity>> primary, LiveData<List<SaintEntity>> fallback) {
        return Transformations.switchMap(primary, list -> (list == null || list.isEmpty()) ? fallback : staticLiveData(list));
    }

    private static <T> LiveData<T> staticLiveData(T value) {
        MutableLiveData<T> live = new MutableLiveData<>();
        live.setValue(value);
        return live;
    }
}
