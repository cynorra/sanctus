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
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import java.util.concurrent.Executors;

public class PrayerDetailFragment extends Fragment {

    public static final String ARG_PRAYER_ID = "prayer_id";

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

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        int[] fontSize = {
            SanctusApplication.getInstance().getSettingsRepository().getTextSize()
        };
        tvContent.setTextSize(fontSize[0]);

        btnFontIncrease.setOnClickListener(v -> {
            if (fontSize[0] < 26) {
                fontSize[0] += 2;
                tvContent.setTextSize(fontSize[0]);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });
        btnFontDecrease.setOnClickListener(v -> {
            if (fontSize[0] > 12) {
                fontSize[0] -= 2;
                tvContent.setTextSize(fontSize[0]);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });

        if (prayerId != -1) {
            PrayerRepository repo = new PrayerRepository(requireContext());
            repo.getPrayerById(prayerId).observe(getViewLifecycleOwner(), prayer -> {
                if (prayer == null) return;
                tvTitle.setText(prayer.name);
                tvCategory.setText(prayer.category != null ? prayer.category.toUpperCase() : "");
                tvContent.setText(prayer.content);
                btnFavorite.setSelected(prayer.isFavorite);
                btnFavorite.setOnClickListener(v -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        prayer.isFavorite = !prayer.isFavorite;
                        prayer.lastReadAt = System.currentTimeMillis();
                        com.sanctus.christian.prayer.data.local.SanctusDatabase
                                .getInstance(requireContext()).prayerDao().update(prayer);
                    });
                    btnFavorite.setSelected(!btnFavorite.isSelected());
                });
            });
        }
    }
}
