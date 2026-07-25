package com.sanctus.christian.prayer.ui.rosary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;

public class RosaryFragment extends Fragment {
    private RosaryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rosary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RosaryViewModel.class);

        TextView tvMystery = view.findViewById(R.id.tv_mystery_title);
        TextView tvMysteryLine = view.findViewById(R.id.tv_mystery_line);
        TextView tvPrayer = view.findViewById(R.id.tv_current_prayer);
        TextView tvProgress = view.findViewById(R.id.tv_bead_progress);
        Button btnNext = view.findViewById(R.id.btn_next_bead);

        int textSize = SanctusApplication.getInstance().getSettingsRepository().getTextSize();
        tvPrayer.setTextSize(textSize + 5); // prayer text stays visually larger than the base body size

        viewModel.getCurrentMystery().observe(getViewLifecycleOwner(), mystery -> {
            if (mystery != null) tvMystery.setText(mystery.getTitle());
        });

        viewModel.getCurrentMysteryLine().observe(getViewLifecycleOwner(), line -> {
            if (line != null) tvMysteryLine.setText(line);
        });

        viewModel.getCurrentPrayer().observe(getViewLifecycleOwner(), prayer -> {
            if (prayer != null) tvPrayer.setText(prayer);
        });

        viewModel.getProgress().observe(getViewLifecycleOwner(), progress -> {
            tvProgress.setText(progress);
        });

        viewModel.getFinished().observe(getViewLifecycleOwner(), finished -> {
            if (Boolean.TRUE.equals(finished)) {
                btnNext.setVisibility(View.GONE);
            }
        });

        btnNext.setOnClickListener(v -> viewModel.nextBead());
    }
}
