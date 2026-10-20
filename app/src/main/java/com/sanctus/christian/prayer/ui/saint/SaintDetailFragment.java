package com.sanctus.christian.prayer.ui.saint;

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
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.SaintNames;
import com.sanctus.christian.prayer.util.Tradition;
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
        tvDesc.setTextSize(fontSize[0] + 6);

        btnFontIncrease.setOnClickListener(v -> {
            if (fontSize[0] < 26) {
                fontSize[0] += 2;
                tvDesc.setTextSize(fontSize[0] + 6);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });
        btnFontDecrease.setOnClickListener(v -> {
            if (fontSize[0] > 12) {
                fontSize[0] -= 2;
                tvDesc.setTextSize(fontSize[0] + 6);
                SanctusApplication.getInstance().getSettingsRepository().setTextSize(fontSize[0]);
            }
        });

        if (saintId != -1) {
            vm.getSaintById(saintId).observe(getViewLifecycleOwner(), saint -> {
                if (saint == null) return;
                ((TextView) view.findViewById(R.id.tv_saint_name_detail)).setText(saint.name);
                ((TextView) view.findViewById(R.id.tv_saint_initial_detail)).setText(SaintNames.initialOf(saint.name));
                TextView tvTraditions = view.findViewById(R.id.tv_saint_traditions);
                String traditions = traditionLabels(saint.tradition);
                tvTraditions.setText(traditions);
                tvTraditions.setVisibility(traditions.isEmpty() ? View.GONE : View.VISIBLE);
                ((TextView) view.findViewById(R.id.tv_feast_date_detail)).setText(getString(R.string.saint_feast_prefix, DateUtils.localizeFeastDate(saint.feastDate)));
                tvDesc.setText(saint.description);
                ((TextView) view.findViewById(R.id.tv_saint_patron_detail)).setText(getString(R.string.saint_patron_prefix, saint.patronOf));
                btnShare.setOnClickListener(v -> {
                    AnimUtils.pressFeedback(btnShare);
                    String text = getString(R.string.share_saint_text,
                            saint.name, getString(R.string.saint_feast_prefix, DateUtils.localizeFeastDate(saint.feastDate)),
                            saint.description, BuildConfig.PLAY_STORE_URL);
                    com.sanctus.christian.prayer.share.ShareSheetDialogFragment
                            .create(saint.name, saint.description, text, getString(R.string.share_saint_chooser_title))
                            .show(getChildFragmentManager(), "share_sheet");
                });
            });
        }
    }

    /** "Catholic · Orthodox" for a saint tagged with specific traditions; empty when shared by all. */
    private String traditionLabels(String tag) {
        if (tag == null || Tradition.ALL.equals(tag)) return "";
        StringBuilder out = new StringBuilder();
        for (String id : tag.split(",")) {
            if (out.length() > 0) out.append(" · ");
            out.append(getString(Tradition.labelRes(id.trim())));
        }
        return out.toString();
    }
}
