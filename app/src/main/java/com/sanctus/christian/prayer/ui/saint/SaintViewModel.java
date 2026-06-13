package com.sanctus.christian.prayer.ui.saint;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import java.util.List;

public class SaintViewModel extends AndroidViewModel {
    private final SanctusDatabase db;

    public SaintViewModel(Application app) {
        super(app);
        db = SanctusDatabase.getInstance(app);
    }

    public LiveData<List<SaintEntity>> getAllSaints() { return db.saintDao().getAllSaints(); }
    public LiveData<SaintEntity> getSaintByFeastDate(String date) { return db.saintDao().getSaintByFeastDate(date); }
    public LiveData<SaintEntity> getSaintById(int id) { return db.saintDao().getSaintById(id); }
    public LiveData<List<SaintEntity>> search(String query) { return db.saintDao().searchSaints(query); }
}
