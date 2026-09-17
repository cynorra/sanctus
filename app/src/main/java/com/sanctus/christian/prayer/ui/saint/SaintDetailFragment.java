package com.sanctus.christian.prayer.ui.saint;

import android.content.Intent;
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
import com.sanctus.christian.prayer.util.AnimUtils;

public class SaintDetailFragment extends Fragment {
    public static final String ARG_SAINT_ID = "saint_id";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saint_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int saintId = getArguments() != null ? getArguments().getInt(ARG_SAINT_ID, -1) : -1;
        SaintViewModel vm = new ViewModelProvider(this).get(SaintViewModel.class);

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        TextView tvDesc = view.findViewById(R.id.tv_saint_desc_detail);
        ImageButton btnFontIncrease = view.findViewById(R.id.btn_font_increase);
        ImageButton btnFontDecrease = view.findViewById(R.id.btn_font_decrease);
        ImageButton btnShare = view.findViewById(R.id.btn_share_saint);

        int[] fontSize = {SanctusApplication.getInstance().getSettingsRepository().getTextSize()};
        tvDesc.setTextSize(fontSize[0]);

        btnFontIncrease.setOnClickListener(v -> {
            if (fontSize[0] < 26) {
                fontSize[0] += 2;
                tvDesc.setTextSize(fontSize[0]);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });
        btnFontDecrease.setOnClickListener(v -> {
            if (fontSize[0] > 12) {
                fontSize[0] -= 2;
                tvDesc.setTextSize(fontSize[0]);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });

        if (saintId != -1) {
            vm.getSaintById(saintId).observe(getViewLifecycleOwner(), saint -> {
                if (saint == null) return;
                ((TextView) view.findViewById(R.id.tv_saint_name_detail)).setText(saint.name);
                ((TextView) view.findViewById(R.id.tv_feast_date_detail)).setText(getString(R.string.saint_feast_prefix, saint.feastDate));
                tvDesc.setText(saint.description);
                ((TextView) view.findViewById(R.id.tv_saint_patron_detail)).setText(getString(R.string.saint_patron_prefix, saint.patronOf));
                btnShare.setOnClickListener(v -> {
                    AnimUtils.pressFeedback(btnShare);
                    String text = getString(R.string.share_saint_text,
                            saint.name, getString(R.string.saint_feast_prefix, saint.feastDate),
                            saint.description, BuildConfig.PLAY_STORE_URL);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_saint_chooser_title)));
                });
            });
        }
    }
}
